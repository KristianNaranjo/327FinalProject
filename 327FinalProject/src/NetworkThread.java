import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NetworkThread extends Thread{

	private ConcurrentLinkedQueue<Integer> returnQueue = new ConcurrentLinkedQueue<Integer>();
	private String request;

	public NetworkThread(String request, ConcurrentLinkedQueue<Integer> returnQueue){
		this.returnQueue= returnQueue;
		this.request = request;
	}
	
	public void setRequest(String request){
		this.request = request;
	}
	
	public void run(){
		String host = "localhost"; // ip address of host/server
		int port = 4445;
        String fromServer = null;
        //later: response from server. need it here ´cause of catch
		
		try (
	            Socket socket = new Socket(host, port);
				// for clients to send to server
	            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				// for clients to receive data from server
	            BufferedReader in = new BufferedReader(
	                new InputStreamReader(socket.getInputStream()));
	        ) {
	            while (request != null || request != "quit") {
	            	out.println(request); // send request to server
	            	fromServer = in.readLine();	// get message back from server
	            	returnQueue.add(Integer.parseInt(fromServer));
	            	//add the sever´s message to the returnQueue
	                if (fromServer.equals("quit"))
	                	break;       
	            }
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host " + host);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " +
	                host);
	            System.exit(1);
	        } 
			//do not know, if it is really needed
			catch(IllegalStateException e){
	        	System.err.println("The result of the server was no integer: " + fromServer);
	        	System.exit(1);
	        }
	}	
}
