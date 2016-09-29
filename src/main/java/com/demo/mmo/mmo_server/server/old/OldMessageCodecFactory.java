package com.demo.mmo.mmo_server.server.old;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 消息处理工厂
 *
 */
public class OldMessageCodecFactory implements ProtocolCodecFactory{
	private final OldMessageEncoder encoder;
	private final OldMessageDecoder decoder;
	
	public OldMessageCodecFactory(Charset charset) {
		this.encoder = new OldMessageEncoder();
		this.decoder = new OldMessageDecoder();
	}
	
	
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}

}
