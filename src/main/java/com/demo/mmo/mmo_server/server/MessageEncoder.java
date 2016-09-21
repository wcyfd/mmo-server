package com.demo.mmo.mmo_server.server;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.demo.mmo.mmo_server.protocals.Protocal.Request;
import com.demo.mmo.mmo_server.protocals.Protocal.Request.Builder;

public class MessageEncoder extends ProtocolEncoderAdapter {
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		// Message msg = (Message) message;
		// int msgLength = 2 + msg.getDataLength();
		// IoBuffer buffer = IoBuffer.allocate(msgLength +
		// 4).setAutoExpand(true);
		// buffer.order(ByteOrder.LITTLE_ENDIAN);
		// buffer.putInt(msgLength);
		// buffer.putShort(msg.getType());
		// buffer.put(msg.getData());
		// buffer.flip();
		// out.write(buffer);

		Builder builder = (Builder) message;
		byte[] bytes = builder.build().toByteArray();
		IoBuffer buffer = IoBuffer.allocate(bytes.length + 4).setAutoExpand(true);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		out.write(buffer);
	}
}