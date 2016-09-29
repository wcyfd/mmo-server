package com.demo.mmo.mmo_server.game.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.mina.core.session.IoSession;

import com.demo.mmo.mmo_entity.game.entity.bo.Role;

public class RoleCache {
	private static ReentrantLock lock = new ReentrantLock();
	private static Map<Integer, Role> roleMap = new ConcurrentHashMap<>();
	private static Map<String,Role> accountMap = new ConcurrentHashMap<>();

	public static Role getRoleById(int roleId) {
		return roleMap.get(roleId);
	}

	public static int getRoleIdBySession(IoSession session) {
		Integer roleId = (Integer)session.getAttribute("roleId");
		if (roleId == null)
			roleId = 0;
		return roleId;
	}
	
	public static Role getRoleByAccount(String account){
		if(account == null)
			return null;
		return accountMap.get(account);
	}

	public static void addRole(Role role) {
		try {
			lock.lock();
			role.setRoleId(roleMap.size() + 1);
			roleMap.put(role.getRoleId(), role);
//			accountMap.put(role.getAccount(), role);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}

}
