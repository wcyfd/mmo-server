package com.demo.mmo.mmo_server.game.navigation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.demo.mmo.mmo_entity.game.entity.net.Fight.SC_301;
import com.demo.mmo.mmo_entity.game.entity.net.Fight.SC_302;
import com.demo.mmo.mmo_entity.game.entity.net.Fight.SC_303;
import com.demo.mmo.mmo_entity.game.entity.net.Login.SC_101;
import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.InvalidProtocolBufferException;

public class ResponseNavigation {

	private static Map<Integer, Class<?>> classMap = new HashMap<>();

	public static void init() {
		classMap.put(ResponseProtocal.LOGIN, SC_101.class);
		classMap.put(ResponseProtocal.FIGHT_JOIN, SC_301.class);
		classMap.put(ResponseProtocal.FIGHT_MOVE, SC_302.class);
		classMap.put(ResponseProtocal.FIGHT_NOTICE_EVERY_POSITION, SC_303.class);
	}

	@SuppressWarnings("unchecked")
	public static Map<FieldDescriptor, Object> getAttribute(int responseNavigationId, ByteString data)
			throws InvalidProtocolBufferException {
		try {
			Class<?> clz = classMap.get(responseNavigationId);
			Method method = clz.getMethod("parseFrom", ByteString.class);
			Object object = method.invoke(null, data);
			return (Map<FieldDescriptor, Object>) clz.getMethod("getAllFields").invoke(object);

		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		byte[] data = new byte[] { 10, 19, 8, 1, 21, 0, 0, -128, 63, 29, 0, 0, 0, 64, 32, -39, -2, -102, -82, -9, 42 };
		try {
			SC_303 sc303 = SC_303.parseFrom(data);

			System.out.println(sc303.getFightInfo());
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
