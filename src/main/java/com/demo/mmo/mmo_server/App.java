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
		Request request = Request.newBuilder().setProtocal(1).setData(data).build();
		byte[] bytes = request.toByteArray();
		System.out.println(bytes.length);

		try {
			Request result = Request.parseFrom(bytes);
			int protocal = result.getProtocal();
			ByteString response = result.getData();
			Data101 v = Data101.parseFrom(response);
			int age = v.getAge();
			String name=  v.getName();
			int sex = v.getSex();
			int size = v.getSerializedSize();
			System.out.println(age + " " + name+" "+sex+" "+size);
			
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
