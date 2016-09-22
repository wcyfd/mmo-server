package com.demo.mmo.mmo_server.game.module.login.action;

import org.apache.mina.core.session.IoSession;

import com.demo.mmo.mmo_server.game.module.login.service.LoginService;
import com.demo.mmo.mmo_server.game.navigation.ActionSupport;
import com.demo.mmo.mmo_server.protocals.Login.LoginRequest101;
import com.demo.mmo.mmo_server.protocals.base.Protocal.Response;
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
			LoginRequest101 data101 = LoginRequest101.parseFrom(data);
			String name = data101.getName();
			String account = data101.getAccount();
			Response.Builder builder = loginService.login(name, account);
			session.write(builder);
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
