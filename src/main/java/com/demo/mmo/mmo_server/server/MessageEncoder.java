package com.demo.mmo.mmo_server.server;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.demo.mmo.mmo_server.protocals.base.Protocal.Response;

public class MessageEncoder extends ProtocolEncoderAdapter {
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		Response.Builder builder = (Response.Builder) message;
		byte[] bytes = builder.build().toByteArray();
		IoBuffer buffer = IoBuffer.allocate(bytes.length + 4).setAutoExpand(true);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		out.write(buffer);
	}
}