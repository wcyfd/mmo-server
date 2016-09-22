package com.demo.mmo.mmo_server.game.navigation;

import org.apache.mina.core.session.IoSession;

import com.google.protobuf.ByteString;

public interface ActionSupport {
	public void execute(ByteString data,IoSession session);
	
}
