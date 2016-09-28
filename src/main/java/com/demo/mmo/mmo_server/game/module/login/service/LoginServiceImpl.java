package com.demo.mmo.mmo_server.game.module.login.service;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.mina.core.session.IoSession;

import com.demo.mmo.mmo_entity.game.entity.bo.Role;
import com.demo.mmo.mmo_server.game.cache.RoleCache;
import com.demo.mmo.mmo_server.game.cache.SessionCache;
import com.demo.mmo.mmo_server.game.common.ErrorCode;
import com.demo.mmo.mmo_server.game.navigation.ResponseProtocal;
import com.demo.mmo.mmo_server.protocals.Login.SC_101;
import com.demo.mmo.mmo_server.protocals.base.Protocal.Response;
import com.demo.mmo.mmo_server.protocals.base.Protocal.Response.Builder;

public class LoginServiceImpl implements LoginService {

	private ReentrantLock lock = new ReentrantLock();
	@Override
	public Builder login(String account) {		
		Builder respBuilder = Response.newBuilder();
		respBuilder.setProtocal(ResponseProtocal.LOGIN);
		
		return respBuilder;
	}

	@Override
	public Builder createRole(String account, String name) {
		// TODO Auto-generated method stub
		lock.lock();
		try{
			Role role = this.roleInit(account, name);
			RoleCache.addRole(role);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
		
		return null;
	}

	@Override
	public Builder getRoleData(String account, IoSession session) {
		// TODO Auto-generated method stub
		Role role = RoleCache.getRoleByAccount(account);
		IoSession oldSession = SessionCache.getSessionById(role.getRoleId());
		if (oldSession != null) { // 该账号已登录
			oldSession.setAttribute("roleId", null);
			oldSession.close(false);
		}

		// session绑定ID
		session.setAttribute("roleId", role.getRoleId());
		// session放入缓存
		SessionCache.addSession(role.getRoleId(), session);
		return null;
	}
	
	private Role roleInit(String account,String name){
		Role role = new Role();
		role.setName(name);
		role.setAccount(account);
		return role;
	}
	
	

	

}
