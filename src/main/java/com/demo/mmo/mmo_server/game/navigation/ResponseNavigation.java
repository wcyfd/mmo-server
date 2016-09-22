package com.demo.mmo.mmo_server.game.navigation;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.demo.mmo.mmo_server.protocals.Login.SC_101;
import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.InvalidProtocolBufferException;

public class ResponseNavigation {

	private static Map<Integer, Class<?>> classMap = new HashMap<>();

	@SuppressWarnings("unchecked")
	public static Map<FieldDescriptor, Object> getAttribute(int responseNavigationId, ByteString data)
			throws InvalidProtocolBufferException {
		try {
			Class<?> clz = classMap.get(responseNavigationId);
			Object object = clz.getMethod("parseFrom", ByteString.class).invoke(null, data);
			return (Map<FieldDescriptor, Object>) clz.getMethod("getAllFields").invoke(object);

		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void init() {
		classMap.put(ResponseProtocal.LOGIN, SC_101.class);
	}
}
