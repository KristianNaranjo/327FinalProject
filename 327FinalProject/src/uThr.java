import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/* 
 * There are 8 or more uThr's running on the same local machine as its runtimeThr. 
 * Each uThr executes 20 iterations. 
 * At each iteration a uThr randomly selects one of 5 commands 
 * (nextEven, nextOdd, nextEvenFib, nextLargerRand, nextPrime) to enqueue in the requestQue, 
 * along with any other needed pieces of data, and waits for the result produced by this command. 
 * After its result is enqueued in the returnQue, this thread fetches the returned value and outputs on the terminal.
 */
public class uThr extends Thread {
	
	private Queue<String> mRequestQueue = new LinkedList<String>();
	private Queue<Integer> mReturnQueue = new LinkedList<Integer>();
	private ReentrantLock lock = new ReentrantLock();
	
	
	public uThr (Queue<String> requestQueue, Queue<Integer> returnQueue) { 
		mRequestQueue = requestQueue;
		mReturnQueue = returnQueue;
	}
	
	/*
	public void initializeInNOut(){
		// initialize input and output from runtimeThr
		try {
			// to send to runtime thread 
			out = new PrintWriter(mSocket.getOutputStream(),true);
			// to receive from runtime thread 
			in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	*/
	
	public void run()  { 
		Random rand = new Random();
		int selector;
		String command [] = {"nextEven", "nextOdd", "nextEvenFib", "nextLargerRand", "nextPrime"};
		String reply; 
		
		// add to request queue 
		for (int i = 0; i < 20; i++) { // 20 iterations
			selector = rand.nextInt(5) + 1; // random command 1-5
			mRequestQueue.add(command[selector]);
		}

		// read from return queue 
		while (!mReturnQueue.isEmpty()) { // while queue is not empty  
			try { 
			 reply = mReturnQueue.poll().toString();
			 System.out.println("Reply from runtime thread" + reply);
			} catch (Exception e) { 
				System.out.println("Emtpy Queue");
			}
		}
		
		
	}	

}