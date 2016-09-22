package com.demo.mmo.mmo_server.game.module.login.service;

import com.demo.mmo.mmo_server.protocals.base.Protocal.Response;

public interface LoginService {
	Response.Builder login(String name,String account);
}
