import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;

/**
 * Much complex program, this is using bluetooth connection.
 * Setup
 * 1. Setup Lejos to connect bluetooth to the phone. Once found and able to connect it's done, need not look back wether it is still connecting or can connect
 * 2. Install this into Lejos. The brick will hang as it only waits for signal.
 * Refer to Android project
 */

public class Batman {
	
	public static void main(String[] args) {
        BTConnector connector = new BTConnector();
        new ExitButton().start();

        System.out.println("0. Wait for signal");

        NXTConnection conn = connector.waitForConnection(0, NXTConnection.RAW);

        InputStream is = conn.openInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is), 1);

        String message = "";

        external:
        while (true){

            System.out.println("1. Started");
            message = "";

            try {
                message = br.readLine();
                if(message == null) {
                	break external;
                }
                System.out.println("2. Message: " + message);
            } catch (IOException e) {
                e.printStackTrace(System.out);
                break external;
            }
        }
	}
}

class ExitButton extends Thread {
	
	private final Key buttonExit = Button.ENTER;

	public void init() {
		buttonExit.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(Key k) {
				System.exit(0);
			}
			
			@Override
			public void keyPressed(Key k) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void run() {
		buttonExit.waitForPressAndRelease();
	}
}
