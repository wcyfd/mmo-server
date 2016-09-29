package com.demo.mmo.mmo_server.game.schedule;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.demo.mmo.mmo_server.game.module.fight.service.FightService;
import com.demo.mmo.mmo_server.server.SpringContext;

public class FightScheduled {
	final static FightService fightService = (FightService) SpringContext.getBean("fightService");
	private static ScheduledExecutorService fightScheduled = new ScheduledThreadPoolExecutor(1);

	public static void start() {
		verifyPosition();
	}

	private static void verifyPosition() {
		fightScheduled.scheduleAtFixedRate(new Runnable() {
			public void run() {
				fightService.noticeOtherRolePosition();
			}
		}, 0, 1000, TimeUnit.MILLISECONDS);
	}
}
