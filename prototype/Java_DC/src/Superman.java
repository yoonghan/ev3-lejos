import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

/**
 * Simple program using server listener. This links with Aquaman program.
 * The lego brain is the server, while Aquaman is the the client.
 * 
 * How to test
 * 1. Install this into Lego Brain
 * 2. Execute Aquaman
 *
 */
public class Superman {
	
	private static final int PORT = 9999;
	
	private Superman() {
		
		try (ServerSocket sc = new ServerSocket(PORT); RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.A)){
			Socket socks = sc.accept();
			BufferedReader br = new BufferedReader(new InputStreamReader(socks.getInputStream()), 256);
			System.out.println("Hi Aquaman, " + br.readLine());
			
			m.rotate(360);
			m.rotate(-360);
		}
		catch (IOException e) {
			System.out.println("Verbindungen Fail");
			Delay.msDelay(1000);
		}
		
	}
	
	public static void main(String args[]) {
		new Superman();
	}
}
