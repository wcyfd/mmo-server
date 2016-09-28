package com.demo.mmo.mmo_server.game.module.fight.action;

import org.apache.mina.core.session.IoSession;

import com.demo.mmo.mmo_server.game.module.fight.service.FightService;
import com.demo.mmo.mmo_server.game.navigation.ActionSupport;
import com.demo.mmo.mmo_server.protocals.Fight.CS_301;
import com.demo.mmo.mmo_server.protocals.Fight.CS_302;
import com.demo.mmo.mmo_server.protocals.base.Protocal.Response;
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
			int x = cs302.getX();
			int y = cs302.getY();
			int roleId = cs302.getRoleId();
			Response.Builder builder = fightService.move( roleId, x, y);
			session.write(builder);

		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

}
