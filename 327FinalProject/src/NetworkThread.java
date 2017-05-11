import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

/* Kristian Naranjo
 * Oscar Valdez
 * Jeannine Westerkamp
 * Josh Andreasian
 * Files Associated: RuntimeThread.java and TCPserver.java
 * Description: The RuntimeThread will spawn a NetworkThread whenever it sees a nextPrime, nextEvenFib, or nextLargerRand
 * request in the requestQueue. The NetworkThread connects to the TCPserver via a socket using the server's IP address
 * and the same port number. It then sends a request received from the RuntimeThread. The NetworkThread will then 
 * add each result from the TCPserver into the returnQueue.
 */

public class NetworkThread extends Thread{

	private ConcurrentLinkedQueue<Return> returnQueue = new ConcurrentLinkedQueue<Return>();
	private Request request;

	public NetworkThread(Request request, ConcurrentLinkedQueue<Return> returnQueue){
		this.returnQueue= returnQueue;
		this.request = request;
	}
	
	public void run(){
		String host = "localhost"; // ip address of host/server
		int port = 4445;
        String fromServer = null;
		
		try (
	            Socket socket = new Socket(host, port);
				// for clients to send to server
	            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				// for clients to receive data from server
	            BufferedReader in = new BufferedReader(
	                new InputStreamReader(socket.getInputStream()));
	        ) {
	            if (request.getRequest() != null) {
	            	out.println(request.getRequest()); // send request to server
	            	fromServer = in.readLine();	// get message back from server
	            	returnQueue.add(new Return(fromServer, request.getRequest(), request.getID()));
	            }
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host " + host);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " +
	                host);
	            System.exit(1);
	        }
	}	
}
