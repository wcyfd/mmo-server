package com.demo.mmo.mmo_server.game.module.fight.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

import com.demo.mmo.mmo_entity.game.entity.po.FightInfo;
import com.demo.mmo.mmo_entity.game.entity.po.Room;
import com.demo.mmo.mmo_server.game.cache.RoomCache;
import com.demo.mmo.mmo_server.game.cache.SessionCache;
import com.demo.mmo.mmo_server.game.common.ErrorCode;
import com.demo.mmo.mmo_server.game.navigation.ResponseProtocal;
import com.demo.mmo.mmo_server.protocals.Fight.SC_301;
import com.demo.mmo.mmo_server.protocals.Fight.SC_302;
import com.demo.mmo.mmo_server.protocals.base.Protocal.Response;
import com.demo.mmo.mmo_server.protocals.base.Protocal.Response.Builder;
import com.demo.mmo.mmo_server.server.Utils;
import com.google.protobuf.ByteString;

public class FightServiceImpl implements FightService {

	@Override
	public Builder joinFight(int roleId) {
		Builder resp = Response.newBuilder();
		resp.setProtocal(ResponseProtocal.FIGHT_JOIN);

		SC_301.Builder sc301 = SC_301.newBuilder();

		Room room = RoomCache.getRoomMap().get(0);

		sc301.setErrorCode(ErrorCode.SUCCESS);
		FightInfo fightInfo = this.createFight(roleId);
		sc301.setSelfX(fightInfo.getX()).setSelfY(fightInfo.getY());
		for (FightInfo info : room.getFightInfoMap().values()) {
			SC_301.FightInfo scFightInfo = SC_301.FightInfo.newBuilder().setX(info.getX()).setY(info.getY())
					.setRoleId(info.getRoleId()).build();
			sc301.addOtherFightinfo(scFightInfo);
		}
		room.getFightInfoMap().put(roleId, fightInfo);

		resp.setData(sc301.build().toByteString());

		return resp;
	}

	/**
	 * 创建战斗信息
	 * 
	 * @param roleId
	 * @return
	 * @author wcy 2016年9月28日
	 */
	private FightInfo createFight(int roleId) {
		FightInfo fightInfo = new FightInfo();
		fightInfo.setRoleId(roleId);
		float initX = 0.0f;
		float initY = 0.0f;

		fightInfo.setX(initX);
		fightInfo.setY(initY);
		return fightInfo;
	}

	@Override
	public Builder move(int roleId, float x, float y) {
		Builder respBuilder = Response.newBuilder();
		respBuilder.setProtocal(ResponseProtocal.FIGHT_MOVE);
		long nowTime = Utils.getNowLongTime();
		ByteString data = SC_302.newBuilder().setRoleId(roleId).setX(x).setY(y).setTime(nowTime).build().toByteString();
		respBuilder.setData(data);

		return respBuilder;
	}

	private void refreshPosition(int roleId, float x, float y) {
		Room room = RoomCache.getRoomMap().get(0);
		Map<Integer, FightInfo> fightInfoMap = room.getFightInfoMap();
		FightInfo fightInfo = fightInfoMap.get(roleId);
		float oldX = fightInfo.getX();
		float oldY = fightInfo.getY();
		if (this.checkPosition(x, y, oldX, oldY)) {
			fightInfo.setX(x);
			fightInfo.setY(y);
		}
		
		

	}
	
	private void sendPosition(Room room){
		Set<Integer> roleIdSet = room.getFightInfoMap().keySet();
		for(Integer roleId :roleIdSet){
			
		
		}
		
		
	}

	private boolean checkPosition(float x, float y, float oldX, float oldY) {
		// TODO Auto-generated method stub
		return true;
	}

	public long getPing(int roleId, long time) {
		long nowTime = Utils.getNowLongTime();
		long deltaTime = nowTime - time;
		return deltaTime / 2;
	}

}
