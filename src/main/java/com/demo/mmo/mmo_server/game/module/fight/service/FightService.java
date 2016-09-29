package com.demo.mmo.mmo_server.game.module.fight.service;

import org.apache.mina.core.session.IoSession;

import com.demo.mmo.mmo_entity.game.entity.bo.Role;
import com.demo.mmo.mmo_entity.game.entity.net.base.Protocal.Response;
import com.demo.mmo.mmo_entity.game.entity.net.base.Protocal.Response.Builder;
import com.demo.mmo.mmo_entity.game.entity.po.Room;

public interface FightService {

	public Builder joinFight(IoSession session);

	public void move(IoSession session, int roleId, float x, float y);

	void noticeOtherRolePosition();

}
