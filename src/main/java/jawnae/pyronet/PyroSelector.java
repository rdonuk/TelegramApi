/*
 * Created on Sep 24, 2008
 */

package jawnae.pyronet;

import com.sun.org.apache.xpath.internal.SourceTreeManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@SuppressWarnings("ObjectEquality")
public class PyroSelector {
    
    private static final Logger log = Logger.getLogger(PyroSelector.class);
    
    private static boolean DO_NOT_CHECK_NETWORK_THREAD = false;
    static final int BUFFER_SIZE = 64 * 1024;
    private Thread networkThread;
    private final Selector nioSelector;
    final ByteBuffer networkBuffer;

    public PyroSelector() {
        this.networkBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

        try {
            this.nioSelector = Selector.open();
        } catch (IOException exc) {
            throw new PyroException("Failed to open a selector?!", exc);
        }

        this.networkThread = Thread.currentThread();
    }

    //

    final boolean isNetworkThread() {
        return DO_NOT_CHECK_NETWORK_THREAD || (networkThread == Thread.currentThread());

    }

    public final Thread networkThread() {
        return this.networkThread;
    }

    public final void checkThread() {
        if (DO_NOT_CHECK_NETWORK_THREAD) {
            return;
        }

        if (!this.isNetworkThread()) {
            throw new PyroException("call from outside the network-thread, you must schedule tasks");
        }
    }

    public PyroClient connect(InetSocketAddress host) throws IOException {
        return this.connect(host, null);
    }

    public PyroClient connect(InetSocketAddress host, InetSocketAddress bind) throws IOException {
        return new PyroClient(this, bind, host);
    }

    public void select() {
        this.select(0);
    }

    public void select(long eventTimeout) {
        this.checkThread();

        //

        this.executePendingTasks();
        this.performNioSelect(eventTimeout);

        final long now = System.currentTimeMillis();
        this.handleSelectedKeys(now);
        this.handleSocketTimeouts(now);
    }

    private void executePendingTasks() {
        while (true) {
            Runnable task = this.tasks.poll();
            if (task == null)
                break;

            try {
                task.run();
            } catch (Throwable cause) {
                log.error(cause);
            }
        }
    }

    private final void performNioSelect(long timeout) {
        int selected;
        try {
            selected = nioSelector.select(timeout);
        } catch (Exception exc) {
            log.error(exc);
            try {
                Thread.sleep(2000);
                selected = nioSelector.select(timeout);
            } catch (IOException | InterruptedException e) {
                log.error(e);
            }
        }
    }

    private final void handleSelectedKeys(long now) {
        Iterator<SelectionKey> keys = nioSelector.selectedKeys().iterator();

        while (keys.hasNext()) {
            SelectionKey key = keys.next();
            keys.remove();

            if (key.channel() instanceof SocketChannel) {
                PyroClient client = (PyroClient) key.attachment();
                client.onInterestOp(now);
            }
        }
    }

    private final void handleSocketTimeouts(long now) {
        for (SelectionKey key : nioSelector.keys()) {
            if (key.channel() instanceof SocketChannel) {
                PyroClient client = (PyroClient) key.attachment();

                if (client.didTimeout(now)) {
                    try {
                        throw new SocketTimeoutException("PyroNet detected NIO timeout");
                    } catch (SocketTimeoutException exc) {
                        client.onConnectionError(exc);
                    }
                }
            }
        }
    }

    public void spawnNetworkThread(final String name) {
        
        this.networkThread =
                new Thread(name) {
                    @Override
                    public void run() {
                    
                        // start select-loop
                        try {
                            while (!this.isInterrupted()) {
                                PyroSelector.this.select(1000L);
                            }
//                        } catch (ClosedSelectorException ee) {
//                            log.warn("Selector closed " + ee);
                        } catch (Exception exc) {
                            throw new IllegalStateException(exc);
                        }
                    }
                };
    
        networkThread.start();
    }

    //

    private BlockingQueue<Runnable> tasks = new LinkedBlockingDeque<>();

    public void scheduleTask(Runnable task) {
        if (task == null) {
            throw new NullPointerException();
        }

        try {
            this.tasks.put(task);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        wakeup();
    }

    public void wakeup() {
        this.nioSelector.wakeup();
    }

    public void close() throws IOException {
        this.networkThread.interrupt();
        this.nioSelector.close();
        this.networkBuffer.clear();
    }

    //

    final SelectionKey register(SelectableChannel channel, int ops) throws IOException {
        return channel.register(this.nioSelector, ops);
    }

    final boolean adjustInterestOp(SelectionKey key, int op, boolean state) {
        this.checkThread();

        try {
            int ops = key.interestOps();
            boolean changed = state != ((ops & op) == op);
            if (changed)
                key.interestOps(state ? (ops | op) : (ops & ~op));
            return changed;
        } catch (CancelledKeyException exc) {
            // ignore
            return false;
        }
    }
}