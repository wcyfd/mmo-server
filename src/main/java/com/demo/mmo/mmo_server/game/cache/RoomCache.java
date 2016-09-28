package com.demo.mmo.mmo_server.game.cache;

import java.util.HashMap;
import java.util.Map;

import com.demo.mmo.mmo_entity.game.entity.po.Room;

public class RoomCache {
	private static Map<Integer, Room> roomMap = new HashMap<>();

	public static Map<Integer, Room> getRoomMap() {
		return roomMap;
	}
}
