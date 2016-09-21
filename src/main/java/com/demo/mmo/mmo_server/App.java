package com.demo.mmo.mmo_server;

import java.util.HashMap;
import java.util.Map;

import com.demo.mmo.mmo_server.protocals.Protocal.Data101;
import com.demo.mmo.mmo_server.protocals.Protocal.Request;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		ByteString data = Data101.newBuilder().setAge(20).setName("wcy").setSex(1).build().toByteString();
		Request request = Request.newBuilder().setProtocal(101).setData(data).build();
		byte[] bytes = request.toByteArray();

		try {
			Request result = Request.parseFrom(bytes);
			int protocal = result.getProtocal();
			Data101 resultData = (Data101)getByteString(protocal,result.getData());
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static GeneratedMessageV3 getByteString(int protocal, ByteString res) {
		GeneratedMessageV3 v = null;
		try {
			if (protocal == 101) {
				v = Data101.parseFrom(res);
			}
			return v;
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
