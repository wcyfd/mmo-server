package com.demo.mmo.mmo_server.game.module.fight.service;

import com.demo.mmo.mmo_entity.game.entity.bo.Role;
import com.demo.mmo.mmo_server.protocals.base.Protocal.Response;

public interface FightService {
	
	public Response.Builder joinFight(int roleId);
	
	public Response.Builder move(int roleId, float x, float y);
	

}
