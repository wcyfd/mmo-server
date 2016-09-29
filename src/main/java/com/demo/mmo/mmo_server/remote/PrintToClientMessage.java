package com.demo.mmo.mmo_server.remote;

import java.util.Map;

import com.google.protobuf.Descriptors.FieldDescriptor;

public class PrintToClientMessage {
	private final static String format = "response roleId:";

	public static void print(Integer roleId, int protocal, Map<FieldDescriptor, Object> map) {

//		System.out.println("-------------" + format + roleId + " protocal:" + protocal + "-------------");
		if (map != null) {
			for (Map.Entry<FieldDescriptor, Object> entry : map.entrySet()) {
				String fieldName = entry.getKey().getName();
				System.out
						.println(format + roleId + " protocal:" + protocal + " " + fieldName + ":" + entry.getValue());
			}
		}
	}
}
