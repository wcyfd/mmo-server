package com.demo.mmo.mmo_server.game.module.login.service;

import org.apache.mina.core.session.IoSession;

import com.demo.mmo.mmo_server.protocals.base.Protocal.Response.Builder;

public interface LoginService {
	
	Builder login(String account);
	
	Builder createRole(String account,String name);
	
	Builder getRoleData(String account,IoSession session);
}
