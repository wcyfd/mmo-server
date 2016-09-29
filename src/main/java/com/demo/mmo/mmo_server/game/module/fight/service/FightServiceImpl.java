package com.demo.mmo.mmo_server.game.module.fight.service;

import java.util.Map;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

import com.demo.mmo.mmo_entity.game.entity.bo.Role;
import com.demo.mmo.mmo_entity.game.entity.net.Fight.FightInfo;
import com.demo.mmo.mmo_entity.game.entity.net.Fight.SC_301;
import com.demo.mmo.mmo_entity.game.entity.net.Fight.SC_302;
import com.demo.mmo.mmo_entity.game.entity.net.Fight.SC_303;
import com.demo.mmo.mmo_entity.game.entity.net.base.Protocal.Response;
import com.demo.mmo.mmo_entity.game.entity.net.base.Protocal.Response.Builder;
import com.demo.mmo.mmo_entity.game.entity.po.Room;
import com.demo.mmo.mmo_server.game.cache.RoleCache;
import com.demo.mmo.mmo_server.game.cache.RoomCache;
import com.demo.mmo.mmo_server.game.cache.SessionCache;
import com.demo.mmo.mmo_server.game.common.ErrorCode;
import com.demo.mmo.mmo_server.game.navigation.ResponseProtocal;
import com.demo.mmo.mmo_server.server.Utils;
import com.google.protobuf.ByteString;

public class FightServiceImpl implements FightService {

	@Override
	public Builder joinFight(IoSession session) {
		Role role = new Role();
		RoleCache.addRole(role);
		int roleId = role.getRoleId();
		
		this.saveSession(session, role);

		Builder resp = Response.newBuilder();
		resp.setProtocal(ResponseProtocal.FIGHT_JOIN);

		SC_301.Builder sc301 = SC_301.newBuilder();

		Room room = RoomCache.getRoomMap().get(0);
		FightInfo.Builder fightInfoBuilder = this.createFightInfoBuilder(roleId);
		room.getFightInfoMap().put(roleId, fightInfoBuilder);

		sc301.setX(fightInfoBuilder.getX());
		sc301.setY(fightInfoBuilder.getY());

		sc301.setErrorCode(ErrorCode.SUCCESS);

		resp.setData(sc301.build().toByteString());
		return resp;
	}

	private void saveSession(IoSession session, Role role) {
		IoSession oldSession = SessionCache.getSessionById(role.getRoleId());
		if (oldSession != null) { // 该账号已登录
			oldSession.setAttribute("roleId", null);
			oldSession.close(false);
		}

		// session绑定ID
		session.setAttribute("roleId", role.getRoleId());
		// session放入缓存
		SessionCache.addSession(role.getRoleId(), session);
	}

	/**
	 * 创建战斗信息
	 * 
	 * @param roleId
	 * @return
	 * @author wcy 2016年9月28日
	 */
	private FightInfo.Builder createFightInfoBuilder(int roleId) {
		FightInfo.Builder builder = FightInfo.newBuilder();
		builder.setX(1.0f);
		builder.setY(2.0f);
		builder.setRoleId(roleId);
		builder.setTime(Utils.getNowLongTime());
		return builder;
	}

	@Override
	public void move(IoSession session, int roleId, float x, float y) {
		Builder respBuilder = Response.newBuilder();
		respBuilder.setProtocal(ResponseProtocal.FIGHT_MOVE);

		Room room = RoomCache.getRoomMap().get(0);
		Map<Integer, FightInfo.Builder> fightMap = room.getFightInfoMap();
		FightInfo.Builder builder = fightMap.get(roleId);
		float oldX = builder.getX();
		float oldY = builder.getY();
		long lastTime = builder.getTime();
		long nowTime = Utils.getNowLongTime();

		if (this.checkDistance(lastTime, oldX, oldY, nowTime, x, y)) {
			builder.setX(x);
			builder.setY(y);
			builder.setTime(nowTime);
		}
		ByteString data = SC_302.newBuilder().setRoleId(builder.getRoleId()).setTime(builder.getTime())
				.setX(builder.getX()).setY(builder.getY()).build().toByteString();
		respBuilder.setData(data);
		// 位置改变后的消息返回
		session.write(respBuilder);
		
		this.noticeOtherRolePosition();
	}

	/**
	 * 
	 * @param oldX
	 * @param oldY
	 * @param x
	 * @param y
	 * @return
	 * @author wcy 2016年9月29日
	 */
	private boolean checkDistance(long lastTime, float oldX, float oldY, long nowTime, float x, float y) {
		return true;
	}

	/**
	 * 告诉其他人你自己的位置
	 * 
	 * @param room
	 * @param roleId
	 * @author wcy 2016年9月29日
	 */
	@Override
	public void noticeOtherRolePosition() {
		Room room = RoomCache.getRoomMap().get(0);
		Builder respBuilder = room.getNoticer();
		respBuilder.setProtocal(ResponseProtocal.FIGHT_NOTICE_EVERY_POSITION);
		Map<Integer, FightInfo.Builder> fightInfoMap = room.getFightInfoMap();
		for (FightInfo.Builder builder : fightInfoMap.values()) {
			Set<Integer> set = fightInfoMap.keySet();
			for (Integer roleId : set) {
				IoSession session = SessionCache.getSessionById(roleId);
				if (session != null) {
					SC_303 sc303 = SC_303.newBuilder().setFightInfo(builder.build()).build();
					session.write(respBuilder.setData(sc303.toByteString()));
				}
			}
		}

	}
}
