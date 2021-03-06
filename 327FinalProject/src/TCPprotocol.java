/* Kristian Naranjo
 * Oscar Valdez
 * Jeannine Westerkamp
 * Josh Andreasian
 * Files Associated: Created as a thread from TCPserver (each thread uses its own socket)
 * Description: This class is used by the TCPserver as a thread for each client. It
 * takes in the clientSocket as a parameter to ensure that it is connection-oriented.
 * It also takes in array variables that are passed by reference to update values that
 * are stored in the TCPserver class, allowing for the server to be stateful. Each client
 * having its own thread ensures that the server is concurrent as well. */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class TCPprotocol extends Thread {
	
	private ReentrantLock lock = new ReentrantLock();
	
	private long [] mLastFib;
	private int [] mLastLargerRand;
	private int [] mLastPrime;
	
	private String input;
	private String output;
	
	private PrintWriter out;
	private BufferedReader in;
	private Socket mClientSocket;
	
	public TCPprotocol(Socket clientSocket, long[] lastFib, int[] lastLargerRand, int[] lastPrime){
		// initialize variables referenced from server
		mClientSocket = clientSocket;
		mLastFib = lastFib;
		mLastLargerRand = lastLargerRand;
		mLastPrime = lastPrime;
		initializeInNOut();
	}
	
	public void initializeInNOut(){
		// initialize input and output from client
		try {
			// to send to client
			out = new PrintWriter(mClientSocket.getOutputStream(),true);
			// to receive from client
			in = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		try{
			
			input = in.readLine(); // get input from client
				
			while(!input.equals("quit")){
				output = "";
				switch(input){
				/** add a mutex around each manipulation of the output variable
				 * to prevent a race condition
				*/
					case "nextPrime":
						output += Integer.toString(nextPrime());
						break;
					case "nextLargerRand":
						output += Integer.toString(nextLargerRand());
						break;
					case "nextEvenFib":
						output += Long.toString(nextEvenFib());
						break;
					default:
						output = "You entered: " + input;
						break;
				}
				lock.lock();
				try{
					/* ensure that the correct output value is
					 * displayed by adding a mutex for the critical section
					*/
					out.println(output);
					input = in.readLine();
					if(input == null){
						break;
					}
				}
				finally { lock.unlock();}
			}
			out.println("quit");
		} catch(IOException e){	}
	}
	
	public int nextPrime(){
		// lock critical section to prevent race condition
		lock.lock();
		try{
			while(!isPrime(++mLastPrime[0])){
			}
			return mLastPrime[0];
		}
		finally{
			lock.unlock();
		}
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
	
	public long nextEvenFib(){
		lock.lock();
		try{ // lock the critical section to prevent a race condition
			if(mLastFib[0] == 0 && mLastFib[1] == 0){
				mLastFib[1] = 1;
			}
			do{
				long temp = mLastFib[1];
				mLastFib[1] += mLastFib[0];
				mLastFib[0] = temp;
			} while(mLastFib[1] % 2 != 0);
			return mLastFib[1];
		}
		finally{
			lock.unlock();
		}
	}
	
	public int nextLargerRand(){
		lock.lock();
		try{ // lock the critical section to prevent a race condition
			Random rand = new Random();
			mLastLargerRand[0] = rand.nextInt(100) + mLastLargerRand[0];
			return mLastLargerRand[0];
		}
		finally{
			lock.unlock();
		}
	}

}
