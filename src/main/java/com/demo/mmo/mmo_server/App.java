package com.demo.mmo.mmo_server;

import java.net.InetSocketAddress;

import com.demo.mmo.mmo_server.game.navigation.ActionNavigation;
import com.demo.mmo.mmo_server.game.navigation.ResponseNavigation;
import com.demo.mmo.mmo_server.remote.ClientHandler;
import com.demo.mmo.mmo_server.server.ServerConfig;
import com.demo.mmo.mmo_server.server.WanServer;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		ActionNavigation.init();
		ResponseNavigation.init();
		
		ServerConfig.loadConfig(10001);
		WanServer.startIOServer(new ClientHandler(), new InetSocketAddress(ServerConfig.getWanServerPort()));
	}

//	public static void main(String[] args) {
//		ResponseNavigation.init();
//		System.out.println("Hello World!");
//		ByteString data = Login.CS_101.newBuilder().setAccount("wcyAccount").setName("wcyName").build().toByteString();
//		Request request = Request.newBuilder().setProtocal(1).setData(data).build();
//		byte[] bytes = request.toByteArray();
//
//		try {
//			Request result = Request.parseFrom(bytes);
//			ByteString protocalData = result.getData();
//			CS_101 v = CS_101.parseFrom(protocalData);
//
//			long s1 = System.currentTimeMillis();
//			SC_101.Builder builder = SC_101.newBuilder().setAge(20).setSex(1).setSuccess(true);
//			long s4 = System.currentTimeMillis();
//
//			System.out.println(s4 - s1);
//
//			builder.putSchoolMap("small school", "2");
//			builder.putSchoolMap("mediem school", "4");
//			builder.putSchoolMap("high school", "3");
//			builder.putSchoolMap("high school", "16");
//			builder.putSchoolMap("hig2h school", "3");
//			builder.putSchoolMap("hi3gh school", "3");
//			builder.putSchoolMap("4high school", "3");
//			builder.putSchoolMap("h3igh school", "3");
//			builder.putSchoolMap("hi2gh school", "3");
//			builder.putSchoolMap("hi1gh school", "3");
//			for (int i = 0; i < 5; i++) {
//				builder.addClassmates(Classmate.newBuilder().setName("mates" + i).setSex(1).setAge(14));
//			}
//			long s5 = System.currentTimeMillis();
//			SC_101 response101 = builder.build();
//
//			System.out.println(s5 - s4);
//
//			Response response = Response.newBuilder().setProtocal(20101).setData(response101.toByteString()).build();
//			int p = response.getProtocal();
//			ByteString respData = response.getData();
//			long s2 = System.currentTimeMillis();
//
//			System.out.println(s2 - s5);
//
//			PrintToClientMessage.print(0, p, ResponseNavigation.getAttribute(p, respData));
//
//			long s3 = System.currentTimeMillis();
//
//			System.out.println(s3 - s2);
//			System.out.println(response.getSerializedSize());
//		} catch (InvalidProtocolBufferException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
