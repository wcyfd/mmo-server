package com.demo.mmo.mmo_server.game.module.fight.action;

import org.apache.mina.core.session.IoSession;

import com.demo.mmo.mmo_entity.game.entity.net.Fight.CS_302;
import com.demo.mmo.mmo_entity.game.entity.net.base.Protocal.Response;
import com.demo.mmo.mmo_server.game.cache.RoleCache;
import com.demo.mmo.mmo_server.game.module.fight.service.FightService;
import com.demo.mmo.mmo_server.game.navigation.ActionSupport;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class FightMoveAction implements ActionSupport {

	private FightService fightService;

	public void setFightService(FightService fightService) {
		this.fightService = fightService;
	}

	@Override
	public void execute(ByteString data, IoSession session) {
		try {
			CS_302 cs302 = CS_302.parseFrom(data);
			float x = cs302.getX();
			float y = cs302.getY();
			int roleId = RoleCache.getRoleIdBySession(session);
			fightService.move(session, roleId, x, y);

		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

}
