package com.demo.mmo.mmo_server.game.module.login.action;

import org.apache.mina.core.session.IoSession;

import com.demo.mmo.mmo_entity.game.entity.net.Login.CS_101;
import com.demo.mmo.mmo_entity.game.entity.net.base.Protocal.Response;
import com.demo.mmo.mmo_server.game.module.login.service.LoginService;
import com.demo.mmo.mmo_server.game.navigation.ActionSupport;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class LoginAction implements ActionSupport {
	private LoginService loginService;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	@Override
	public void execute(ByteString data, IoSession session) {
		try {
			CS_101 data101 = CS_101.parseFrom(data);
			String account = data101.getAccount();
			Response.Builder builder = loginService.login(account);
			session.write(builder);
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
