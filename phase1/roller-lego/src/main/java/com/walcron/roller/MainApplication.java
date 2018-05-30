package com.walcron.roller;

import com.walcron.lego.roller.ScalaRoverRoller;
import com.walcron.lego.roller.hardware.LegoLargeMotorA;
import com.walcron.lego.roller.hardware.LegoLargeMotorB;
import com.walcron.lego.roller.hardware.LegoMediumMotorC;
import com.walcron.lego.roller.hardware.TouchSensor1;
import com.walcron.lego.roller.util.Property;

public final class MainApplication {
	
	private MainApplication() {
		LegoLargeMotorA legoMotorA = new LegoLargeMotorA();
		LegoLargeMotorB legoMotorB = new LegoLargeMotorB();
		LegoMediumMotorC legoMotorC = new LegoMediumMotorC();
		TouchSensor1 touchSensor = new TouchSensor1();
		
		new ScalaRoverRoller(legoMotorA, legoMotorB, legoMotorC, touchSensor, true);
		System.out.println("Connects to:" + Property.getServer());
		System.out.println("Ready, Player One!");
		
		lejos.hardware.Sound.beep();
	}
	
	public static void main(String args[]) {
		new MainApplication();
	}
}
