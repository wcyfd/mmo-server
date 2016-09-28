package com.demo.mmo.mmo_server.game.schedule;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FightScheduled {
	private static ScheduledExecutorService fightScheduled = new ScheduledThreadPoolExecutor(1);
	private static void upMatchTime() {
		fightScheduled.scheduleAtFixedRate(new Runnable() {
			public void run() {
				
			}
		}, 0, 1, TimeUnit.SECONDS);
	}
}
