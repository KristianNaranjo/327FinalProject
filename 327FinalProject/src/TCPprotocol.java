import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPprotocol extends Thread {
	
	private int mLastFib; // protocols 0
	private int mLastFib2; // 1
	private int mLastLargerRand; //2
	private int mLastPrime; // 3
	
	private int [] protocols;
	
	private String input;
	private String output;
	
	private PrintWriter out;
	private BufferedReader in;
	private Socket mClientSocket;
	
	public TCPprotocol(Socket clientSocket, int[] arr){
		mClientSocket = clientSocket;
		protocols = arr;
		initializeInNOut();
	}
	
	public void initializeInNOut(){
		try {
			out = new PrintWriter(mClientSocket.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		try{
			// output will be null if you haven't connected to the socket yet
			
			if(output == null)
				out.println("Connected");
				
			input = in.readLine();
				
			while(!input.equals("quit")){
				switch(input){
					case "nextPrime":
						output = Integer.toString(nextPrime());
						break;
					case "nextLargerRand":
						output = Integer.toString(nextLargerRand());
						break;
					case "nextEvenFib":
						output = Integer.toString(nextEvenFib());
						break;
					default:
						output = "Connected";
						break;
				}
				out.println(output);
				input = in.readLine();
				if(input == null){
					break;
				}
			}
			out.println("quit");
		} catch(IOException e){
			System.out.println("Exception caught when trying to listen on port "
	                + mClientSocket.getPort() + " or listening for a connection");
	            System.out.println(e.getMessage());
	            return;
		}
	}
	
	public int nextPrime(){
		while(!isPrime(++protocols[3])){
		}
		return protocols[3];
	}
	
	public boolean isPrime(int n){
		if(n<2)
			return false;
		for(int i = 2; i <= (int)Math.sqrt(n); i++){
			if(n % i == 0)
				return false;
		}
		return true;
	}
	
	public int nextEvenFib(){
		if(protocols[0] == 0 && protocols[1] == 0){
			protocols[1] = 1;
		}
		do{
			int temp = protocols[1];
			protocols[1] += protocols[0];
			protocols[0] = temp;
		} while(protocols[1] % 2 != 0);
		return protocols[1];
	}
	
	public int nextLargerRand(){
		Random rand = new Random();
		protocols[2] = rand.nextInt(100)+protocols[2];
		return protocols[2];
	}

}
