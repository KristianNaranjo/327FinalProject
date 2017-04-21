import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPserver {
	/*
	 * Don't need these for now
	private static String tInput;
	private static int lastFib = 0;
	private static int lastFib2 = 0;
	private static int lastLargerRand = 0;
	private static int lastPrime = 0;
	*/

	public static void main(String[] args) throws IOException{
		int port = 4445;
		
		try(
			ServerSocket serverSocket = new ServerSocket(port);
		){
			ArrayList<TCPprotocol> tcpThreads = new ArrayList<TCPprotocol>();
			
			while(true){
				Socket clientSocket = serverSocket.accept();
				tcpThreads.add(new TCPprotocol(clientSocket));
				tcpThreads.get(tcpThreads.size()-1).start(); // start newest thread
			}
		} catch(IOException e){
			System.out.println("Exception caught when trying to listen on port "
	                + port + " or listening for a connection");
	            System.out.println(e.getMessage());
		}

	}

}
