package com.walcron.lego.test;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

public class BotItTest {
	
	private final Key enter = Button.ENTER;
	
	public BotItTest() {
		new FirstTireListener().start();
		new SecondTireListener().start();
		System.out.println("Start");
		implementStopButton();
	}
	
	public void implementStopButton() {
		enter.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(Key k) {
				System.exit(0);
			}
			
			@Override
			public void keyPressed(Key k) {
				System.exit(0);
			}
		});
		enter.waitForPressAndRelease();
	}
	
	public static void main(String args[]) {
		new BotItTest();
	}
}

class FirstTireListener extends Thread {
	private final EV3LargeRegulatedMotor MOTOR_A = new EV3LargeRegulatedMotor(MotorPort.A);
	private final EV3TouchSensor TOUCHSENSOR_1 = new EV3TouchSensor(SensorPort.S1);
	private final SensorMode SENSOR_MODE = TOUCHSENSOR_1.getTouchMode();
	private final float[] SAMPLE_SIZE = new float[] {(float)SENSOR_MODE.sampleSize()};
	
	public void run() {
		while(true) {
			SENSOR_MODE.fetchSample(SAMPLE_SIZE, 0);
			if(SAMPLE_SIZE[0] == 1 ) {
				MOTOR_A.rotate(360);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class SecondTireListener extends Thread {
	
	private final EV3LargeRegulatedMotor MOTOR_D = new EV3LargeRegulatedMotor(MotorPort.D);
	private final EV3IRSensor IR_SENSOR = new EV3IRSensor(SensorPort.S2);
	private final SensorMode DISTANCE_MODE = IR_SENSOR.getDistanceMode();
	private final float[] SAMPLE_SIZE = new float[] {(float)DISTANCE_MODE.sampleSize()};
	
	public void run() {
		while(true) {
			DISTANCE_MODE.fetchSample(SAMPLE_SIZE, 0);
			if(SAMPLE_SIZE[0] < 1 ) {
				MOTOR_D.rotate(360);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}