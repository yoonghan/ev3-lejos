package com.walcron.lego.test;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;

public class ButtonTest {
	private final Key enter = Button.ENTER;
	private final Key up = Button.UP;
	private final Key down = Button.DOWN;
	private final Key left = Button.LEFT;
	private final Key right = Button.RIGHT;
	
	public ButtonTest() {
		addUpListener();
		addDownListener();
		addLeftListener();
		addRightListener();
		addEnterListener();
	}
	
	private void addEnterListener() {
		Thread exitBtn = new Thread() {
			public void run() {
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
		};
		exitBtn.start();
	}
	
	private void addUpListener() {
		up.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(Key k) {
			}
			
			@Override
			public void keyPressed(Key k) {
				System.out.println("Up");
			}
		});
	}
	
	private void addDownListener() {
		down.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(Key k) {
			}
			
			@Override
			public void keyPressed(Key k) {
				System.out.println("Down");
			}
		});
	}
	
	private void addLeftListener() {
		left.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(Key k) {
			}
			
			@Override
			public void keyPressed(Key k) {
				System.out.println("Left");
			}
		});
	}
	
	private void addRightListener() {
		right.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(Key k) {
			}
			
			@Override
			public void keyPressed(Key k) {
				System.out.println("Right");
			}
		});
	}
	
	public static void main(String args[]) {
		System.out.println("Ready");
		new ButtonTest();
		System.out.println("Begin");
	}
}
