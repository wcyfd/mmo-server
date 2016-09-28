package com.demo.mmo.mmo_server.game.module.fight.action;

import org.apache.mina.core.session.IoSession;

import com.demo.mmo.mmo_server.game.cache.RoleCache;
import com.demo.mmo.mmo_server.game.module.fight.service.FightService;
import com.demo.mmo.mmo_server.game.navigation.ActionSupport;
import com.google.protobuf.ByteString;

public class FightJoinAction implements ActionSupport {

	private FightService fightService;

	public void setFightService(FightService fightService) {
		this.fightService = fightService;
	}

	@Override
	public void execute(ByteString data, IoSession session) {
		int roleId = RoleCache.getRoleIdBySession(session);
		fightService.joinFight(roleId);
	}

}
