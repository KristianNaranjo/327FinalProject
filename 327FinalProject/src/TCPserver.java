/* Kristian Naranjo
 * Oscar Valdez
 * Jeannine Westerkamp
 * Josh Andreasian
 * Files Associated: Spawns threads of class TCPprotocol and communicates
 * with TCPclient via sockets
 * Description: This program initializes a socket using a specific port number that
 * clients must use to connect with. Variables are initializes as arrays so they can be
 * passed by reference to each thread. This allows for the server to be stateful, because
 * it can keep track of the values of the variables. A thread is spawned when a client connects
 * with the server and is added to a list of threads. Having multiple threads for clients
 * allows for the server to be concurrent.*/

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPserver {

	public static void main(String[] args) throws IOException{
		int port = 4445; // clients must use this same port number
		
		try(
			ServerSocket serverSocket = new ServerSocket(port);
		){			
			// use arrays to pass by reference to keep server stateful
			long[] lastFib = {0,0};
			int[] lastLargerRand = {0};
			int[] lastPrime = {0};
			
			while(true){
				// establish connection between client and server
				Socket clientSocket = serverSocket.accept();
				// spawn a new thread for each new connection to a client (to be concurrent)
				TCPprotocol t = new TCPprotocol(clientSocket, lastFib, lastLargerRand, lastPrime);
				t.start();
			}
		} catch(IOException e){	}

	}

}
