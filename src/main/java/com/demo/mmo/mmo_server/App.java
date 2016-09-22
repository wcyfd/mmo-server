package com.demo.mmo.mmo_server;

import com.demo.mmo.mmo_server.game.navigation.ResponseNavigation;
import com.demo.mmo.mmo_server.protocals.Login;
import com.demo.mmo.mmo_server.protocals.Login.LoginRequest101;
import com.demo.mmo.mmo_server.protocals.Login.LoginResponse101;
import com.demo.mmo.mmo_server.protocals.Login.LoginResponse101.Classmate;
import com.demo.mmo.mmo_server.protocals.base.Protocal.Request;
import com.demo.mmo.mmo_server.protocals.base.Protocal.Response;
import com.demo.mmo.mmo_server.remote.PrintToClientMessage;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		ResponseNavigation.init();
		System.out.println("Hello World!");
		ByteString data = Login.LoginRequest101.newBuilder().setAccount("wcyAccount").setName("wcyName").build()
				.toByteString();
		Request request = Request.newBuilder().setProtocal(1).setData(data).build();
		byte[] bytes = request.toByteArray();
		System.out.println(bytes.length);

		try {
			Request result = Request.parseFrom(bytes);
			ByteString protocalData = result.getData();
			LoginRequest101 v = LoginRequest101.parseFrom(protocalData);
			LoginResponse101.Builder builder = LoginResponse101.newBuilder().setAge(20).setSex(1).setSuccess(true);
			builder.putSchoolMap("small school", "2");
			builder.putSchoolMap("mediem school", "4");
			builder.putSchoolMap("high school", "3");
			builder.putSchoolMap("high school", "16");
			builder.putSchoolMap("hig2h school", "3");
			builder.putSchoolMap("hi3gh school", "3");
			builder.putSchoolMap("4high school", "3");
			builder.putSchoolMap("h3igh school", "3");
			builder.putSchoolMap("hi2gh school", "3");
			builder.putSchoolMap("hi1gh school", "3");
			for(int i = 0;i<5;i++){
				builder.addClassmates(Classmate.newBuilder().setName("mates"+i).setSex(1).setAge(14));				
			}
			LoginResponse101 response101 = builder.build();
			Response response = Response.newBuilder().setProtocal(20101).setData(response101.toByteString()).build();
			int p = response.getProtocal();
			ByteString respData = response.getData();
			
			
			PrintToClientMessage.print(0, p, ResponseNavigation.getAttribute(p, respData));

		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
