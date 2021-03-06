package com.demo.mmo.mmo_server.server.old;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.demo.mmo.mmo_server.server.ServerConfig;

/**
 * 消息类
 * @author xjd
 *
 */
public class Message
{
  private static final Charset charset = Charset.forName("UTF-8");
  private short type;
  private IoBuffer data;
  private int dataLength;
  private static final short NONE_TYPE = -1;
  public static final Message NONE = new Message((short)-1);
  private static final byte TRUE = 1;
  private static final byte FALSE = 0;
  
  private static byte Int = 1;
  private static byte Str = 2;
  private static byte Short = 3;
  private static byte Long = 4;
  private static byte Boolean = 5;
  private static byte Byte = 6;
  private static byte Bytes= 7;
  private static byte Double = 8;
  

  public Message()
  {
    this.data = IoBuffer.wrap(new byte[0]);
    this.data.order(ByteOrder.LITTLE_ENDIAN);
    this.data.setAutoExpand(true);
    this.dataLength = 0;
  }

  public Message(short type) {
    this();
    this.type = type;
  }

  public boolean error() {
    return this.type == -1;
  }

  public void putInt(int intParam)
  {
    this.data.put(Int);
    this.data.putInt(intParam);
    this.dataLength += 5;
  }

  public int getInt()
  {
    this.data.get();
    return this.data.getInt();
  }

  public void putBytes(byte[] bytes) {
    this.data.put(Bytes);
    System.out.println(bytes.length);
    this.data.putInt(bytes.length);
    this.data.put(bytes);
    this.dataLength = (this.dataLength + bytes.length + 5);
  }

  public byte[] getBytes() {
    this.data.get();
    int strLen = this.data.getInt();
    byte[] strBytes = new byte[strLen];
    this.data.get(strBytes);
    return strBytes;
  }

  public void putLong(long longParam)
  {
    this.data.put(Long);
    this.data.putLong(longParam);
    this.dataLength += 9;
  }

  public long getLong()
  {
    this.data.get();
    return this.data.getLong();
  }

  public void putDouble(double longParam)
  {
    this.data.put(Double);
    this.data.putDouble(longParam);
    this.dataLength += 9;
  }

  public double getDouble()
  {
    this.data.get();
    return this.data.getDouble();
  }

  public void putBoolean(boolean b)
  {
    this.data.put(Boolean);
    if (b)
      this.data.put((byte)1);
    else
      this.data.put((byte)0);
    this.dataLength += 2;
  }

  public void putBooleanToInt(boolean b) {
    if (b)
      putInt(1);
    else
      putInt(0);
  }

  public boolean getIntToBoolean() {
    int b = getInt();
    if (b == 1)
      return true;
    return false;
  }

  public boolean getBoolean() {
    this.data.get();
    byte b = this.data.get();
    if (b == 1)
      return true;
    return false;
  }

  public void put(byte b) {
    this.data.put(Byte);
    this.data.put(b);
    this.dataLength += 2;
  }

  public byte get() {
    this.data.get();
    return this.data.get();
  }

  public void putShort(short shortParam)
  {
    this.data.put(Short);
    this.data.putShort(shortParam);
    this.dataLength += 3;
  }

  public short getShort()
  {
    this.data.get();
    return this.data.getShort();
  }

  public void putString(String stringParam)
  {
    this.data.put(Str);
    byte[] msgBytes = stringParam.getBytes(charset);
    this.data.putInt(msgBytes.length);
    this.data.put(msgBytes);
    this.dataLength = (this.dataLength + msgBytes.length + 5);
  }

  public String getString(IoSession is)
  {
    this.data.get();
    int strLen = this.data.getInt();
    if (strLen > 2048) {
      Integer roleInteger = (Integer)is.getAttribute("roleId");
      System.err.println("client need string length：" + strLen + 
        " roleid:" + roleInteger);
      return null;
    }
    byte[] strBytes = new byte[strLen];
    this.data.get(strBytes);
    String msgStr = new String(strBytes, charset).trim();
    return msgStr;
  }

  public short getType()
  {
    return this.type;
  }

  public void setType(short type)
  {
    this.type = type;
  }

  public IoBuffer getData()
  {
    IoBuffer cloneData = this.data.duplicate();
    cloneData.order(ByteOrder.LITTLE_ENDIAN);
    cloneData.rewind();
    return cloneData;
  }

  public void setData(IoBuffer params)
  {
    this.data = params;
  }

  public int getDataLength() {
    return this.dataLength;
  }

  public void setDataLength() {
    this.dataLength = this.data.limit();
  }

  public IoBuffer flip() {
    return this.data.flip();
  }

  public IoBuffer rewind() {
    return this.data.rewind();
  }
}
