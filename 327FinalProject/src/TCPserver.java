import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPserver {
	
	private static int mLastFib;
	private static int mLastFib2;
	private static int mLastLargerRand;
	private static int mLastPrime;

	public static void main(String[] args) throws IOException{
		int port = 4448;
		
		try(
			ServerSocket serverSocket = new ServerSocket(port);
		){
			ArrayList<TCPprotocol> tcpThreads = new ArrayList<TCPprotocol>();
			
			int[] arr = {mLastFib, mLastFib2, mLastLargerRand, mLastPrime};
			
			while(true){
				Socket clientSocket = serverSocket.accept();
				tcpThreads.add(new TCPprotocol(clientSocket, arr));
				tcpThreads.get(tcpThreads.size()-1).start(); // start newest thread
			}
		} catch(IOException e){	}

	}

}
