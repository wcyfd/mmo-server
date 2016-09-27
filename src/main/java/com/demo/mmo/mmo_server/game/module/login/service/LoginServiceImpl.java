package com.demo.mmo.mmo_server.game.module.login.service;

import com.demo.mmo.mmo_server.game.navigation.ResponseProtocal;
import com.demo.mmo.mmo_server.protocals.Login.SC_101;
import com.demo.mmo.mmo_server.protocals.Login.SC_101.Classmate;
import com.demo.mmo.mmo_server.protocals.base.Protocal.Response;

public class LoginServiceImpl implements LoginService {

	@Override
	public Response.Builder login(String name, String account) {

		SC_101.Builder builder101 = SC_101.newBuilder();

		builder101.putSchoolMap("small school", "2");
		builder101.putSchoolMap("mediem school", "4");
		builder101.putSchoolMap("high school", "3");
		builder101.putSchoolMap("high school", "16");
		builder101.putSchoolMap("hig2h school", "3");
		builder101.putSchoolMap("hi3gh school", "3");
		builder101.putSchoolMap("4high school", "3");
		builder101.putSchoolMap("h3igh school", "3");
		builder101.putSchoolMap("hi2gh school", "3");
		builder101.putSchoolMap("hi1gh school", "3");
		for (int i = 0; i < 5; i++) {
			builder101.addClassmates(Classmate.newBuilder().setName("mates" + i).setSex(1).setAge(14));
		}
		SC_101 data = builder101.build();

		return Response.newBuilder().setProtocal(ResponseProtocal.LOGIN).setData(data.toByteString());
	}

}
