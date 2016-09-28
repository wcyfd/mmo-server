package com.demo.mmo.mmo_server.game.module.fight.service;

import java.util.Map;

import com.demo.mmo.mmo_entity.game.entity.po.FightInfo;
import com.demo.mmo.mmo_entity.game.entity.po.Room;
import com.demo.mmo.mmo_server.game.cache.RoomCache;
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

		Map<Integer, Room> roomMap = RoomCache.getRoomMap();

		Room room = roomMap.get(0);
		if (room == null) {
			room = new Room();
			room.setWidth(1024.0f);
			room.setHeight(1024.0f);
			room.setId(0);
			roomMap.put(room.getId(), room);
		}
		sc301.setErrorCode(1);
		FightInfo fightInfo = this.createFight(roleId);
		sc301.setSelfX(fightInfo.getX()).setSelfY(fightInfo.getY());
		for (FightInfo info : room.getClientFightInfoMap().values()) {
			SC_301.FightInfo scFightInfo = SC_301.FightInfo.newBuilder().setX(info.getX()).setY(info.getY())
					.setRoleId(info.getRoleId()).build();
			sc301.addOtherFightinfo(scFightInfo);
		}
		room.getClientFightInfoMap().put(roleId, fightInfo);

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
		int initX = 0;
		int initY = 0;

		fightInfo.setX(initX);
		fightInfo.setY(initY);
		return fightInfo;
	}

	@Override
	public Builder move(int roleId, int x, int y) {
		Builder respBuilder = Response.newBuilder();
		respBuilder.setProtocal(ResponseProtocal.FIGHT_MOVE);
		long nowTime = Utils.getNowLongTime();
		ByteString data = SC_302.newBuilder().setRoleId(roleId).setX(x).setY(y).setTime(nowTime).build().toByteString();
		respBuilder.setData(data);

		return respBuilder;
	}
	
	public void refreshPosition(int roleId,int x,int y){
		Room room = RoomCache.getRoomMap().get(0);
		for(FightInfo fightInfo :room.getClientFightInfoMap().values()){
			
		}
	}

	public long getPing(int roleId, long time) {
		long nowTime = Utils.getNowLongTime();
		long deltaTime = nowTime - time;
		return deltaTime / 2;
	}

}
