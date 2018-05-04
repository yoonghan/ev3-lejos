package com.walcron.roller;

import com.walcron.lego.roller.ScalaRoverRoller;
import com.walcron.lego.roller.hardware.LegoLargeMotorA;
import com.walcron.lego.roller.hardware.LegoLargeMotorB;
import com.walcron.lego.roller.hardware.TouchSensor1;

public final class MainApplication {
	
	private MainApplication() {
		LegoLargeMotorA legoMotorA = new LegoLargeMotorA();
		LegoLargeMotorB legoMotorB = new LegoLargeMotorB();
		TouchSensor1 touchSensor = new TouchSensor1();
		
		new ScalaRoverRoller(legoMotorA, legoMotorB, touchSensor, true);
	}
	
	public static void main(String args[]) {
		new MainApplication();
	}
}
