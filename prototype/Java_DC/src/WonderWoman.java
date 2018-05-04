
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

/**
 * This just grinds the motor to test that the program is working.
 * 
 * Port A is the large motor.
 *
 */
public class WonderWoman {
	public static void main(String args[]) {
		RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.A);
		m.setSpeed(10);
		m.rotate(360);
		m.rotate(360);
		m.rotate(360);
		m.close();
	}
}
