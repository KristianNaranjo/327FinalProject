import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPserver {
	private static String tInput;
	private static int lastFib = 0;
	private static int lastFib2 = 0;
	private static int lastLargerRand = 0;
	private static int lastPrime = 0;

	public static void main(String[] args) throws IOException{
		int port = 4449;
		
		try(
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
		){
			String input, output;
			
			TCPprotocol t = new TCPprotocol("Establish Connection",lastFib,lastFib2,lastLargerRand,lastPrime);
			t.start();
			output = t.getOutput();
			out.println(output);
			
			ArrayList<TCPprotocol> tcpThreads = new ArrayList<TCPprotocol>();
			
			while((input = in.readLine()) != null){
				tcpThreads.add(new TCPprotocol(input,lastFib,lastFib2,lastLargerRand,lastPrime));
				tcpThreads.get(tcpThreads.size()-1).start(); // start newest thread
				output = tcpThreads.get(tcpThreads.size()-1).getOutput();
				out.println(output);
				if(output.equals("quit")){
					break;
				}
			}
		} catch(IOException e){
			System.out.println("Exception caught when trying to listen on port "
	                + port + " or listening for a connection");
	            System.out.println(e.getMessage());
		}

	}

}
