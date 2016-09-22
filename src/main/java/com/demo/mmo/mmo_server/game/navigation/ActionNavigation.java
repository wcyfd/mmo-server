package com.demo.mmo.mmo_server.game.navigation;

import java.util.HashMap;
import java.util.Map;

public class ActionNavigation {
	public static Map<Integer, ActionSupport> navigation = new HashMap<>();

	public static ActionSupport getAction(int protocal) {
		return navigation.get(protocal);
	}
}
