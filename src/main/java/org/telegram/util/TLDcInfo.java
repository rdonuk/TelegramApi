package org.telegram.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.tl.StreamingUtils;
import org.telegram.tl.TLContext;
import org.telegram.tl.TLObject;

public class TLDcInfo extends TLObject {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public static final int CLASS_ID = 98092748;
  private int flags;
  private int dcId;
  private String address;
  private int port;
  private int version;

  public TLDcInfo(int flags, int dcId, String address, int port, int version) {
    this.flags = flags;
    this.dcId = dcId;
    this.address = address;
    this.port = port;
    this.version = version;
  }

  public TLDcInfo() {}

  public int getFlags() {
    return this.flags;
  }

  public int getDcId() {
    return this.dcId;
  }

  public String getAddress() {
    return this.address;
  }

  public int getPort() {
    return this.port;
  }

  public int getVersion() {
    return this.version;
  }

  public int getClassId() {
    return 98092748;
  }

  public String toString() {
    return "dcInfo#5d8c6cc";
  }

  public void serializeBody(OutputStream stream) throws IOException {
    StreamingUtils.writeInt((int) this.flags, (OutputStream) stream);
    StreamingUtils.writeInt((int) this.dcId, (OutputStream) stream);
    StreamingUtils.writeTLString((String) this.address, (OutputStream) stream);
    StreamingUtils.writeInt((int) this.port, (OutputStream) stream);
    StreamingUtils.writeInt((int) this.version, (OutputStream) stream);
  }

  public void deserializeBody(InputStream stream, TLContext context) throws IOException {
    this.flags = StreamingUtils.readInt((InputStream) stream);
    this.dcId = StreamingUtils.readInt((InputStream) stream);
    this.address = StreamingUtils.readTLString((InputStream) stream);
    this.port = StreamingUtils.readInt((InputStream) stream);
    this.version = StreamingUtils.readInt((InputStream) stream);
  }
}