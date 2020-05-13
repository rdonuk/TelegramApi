package org.telegram.mtproto.log;

/**
 * Created with IntelliJ IDEA. User: Ruben Bermudez Date: 03.11.13 Time: 3:54
 */
public class Logger {
    
    public static final boolean LOG_THREADS = true;
    public static final boolean LOG_IGNORED = true;
    public static final boolean LOG_PING = true;
    
    private volatile static LogInterface logInterface=new LogInterface() {
        
        @Override
        public void w(String tag, String message) {
        }
        
        @Override
        public void e(String tag, Throwable t) {
        }
        
        @Override
        public void e(String tag, String message) {
        }
        
        @Override
        public void d(String tag, String message) {
        }
    };
    
    public static void registerInterface(LogInterface logInterface) {
        Logger.logInterface = logInterface;
    }
    
    public static void w(String tag, String message) {
        logInterface.w(tag, message);
    }
    
    public static void d(String tag, String message) {
        logInterface.d(tag, message);
    }
    
    public static void e(String tag, Throwable t) {
        logInterface.e(tag, t);
    }
    
    public static void e(String tag, String message) {
        logInterface.e(tag, message);
    }
}
