package com.demo.mmo.mmo_server.game.navigation;

import java.util.HashMap;
import java.util.Map;

import com.demo.mmo.mmo_server.game.module.fight.action.FightJoinAction;
import com.demo.mmo.mmo_server.game.module.fight.action.FightMoveAction;
import com.demo.mmo.mmo_server.game.module.login.action.LoginAction;

public class ActionNavigation {
	private static Map<Integer, ActionSupport> navigation = new HashMap<>();

	private static LoginAction loginAction;

	private static FightJoinAction fightJoinAction;
	private static FightMoveAction fightMoveAction;

	public static ActionSupport getAction(int protocal) {
		return navigation.get(protocal);
	}

	public static void init() {
		navigation.put(ActionProtocal.LOGIN, loginAction);

		navigation.put(ActionProtocal.FIGHT_JOIN, fightJoinAction);
		navigation.put(ActionProtocal.FIGHT_MOVE, fightMoveAction);
	}

	public static void setLoginAction(LoginAction loginAction) {
		ActionNavigation.loginAction = loginAction;
	}

	public static void setFightJoinAction(FightJoinAction fightJoinAction) {
		ActionNavigation.fightJoinAction = fightJoinAction;
	}

	public static void setFightMoveAction(FightMoveAction fightMoveAction) {
		ActionNavigation.fightMoveAction = fightMoveAction;
	}
}
