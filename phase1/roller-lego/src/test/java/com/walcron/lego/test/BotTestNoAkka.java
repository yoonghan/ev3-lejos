package com.walcron.lego.test;

import com.walcron.lego.test.akka.TestWithoutAkka;

public class BotTestNoAkka {
	public static void main(String args[]) {
		System.out.println("RUN");
		new TestWithoutAkka().start();
	}
}
