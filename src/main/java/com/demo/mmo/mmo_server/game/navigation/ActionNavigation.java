package com.demo.mmo.mmo_server.game.navigation;

import java.util.HashMap;
import java.util.Map;

import com.demo.mmo.mmo_server.game.module.login.action.LoginAction;

public class ActionNavigation {
	private static Map<Integer, ActionSupport> navigation = new HashMap<>();

	private static LoginAction loginAction;

	public static ActionSupport getAction(int protocal) {
		return navigation.get(protocal);
	}

	public static void init() {
		navigation.put(ActionProtocal.LOGIN, loginAction);
	}

	public static void setLoginAction(LoginAction loginAction) {
		ActionNavigation.loginAction = loginAction;
	}
}
