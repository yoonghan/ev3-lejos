import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This program links to Superman. Change the Address and install Superman into lego 
 *
 */
public class Aquaman {
	private static final int PORT = 9999;
	//Change Address
	//Should work if the address is 10.0.1.1
	private static final String ADDRESS = "192.168.1.48";
	
	public static void main(String args[]) {
		try(Socket socket = new Socket(ADDRESS, PORT)) {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("punch punch kick kick");
		}
		catch(IOException e) {
			System.out.println("Cannot Connect la.");
		}
	}
}
