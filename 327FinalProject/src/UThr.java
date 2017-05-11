/* Kristian Naranjo
 * Oscar Valdez
 * Jeannine Westerkamp
 * Josh Andreasian
 * Files Associated: TCPclient.java and RuntimeThread.java
 * Description: There are 8 or more uThr's running on the same local machine as its runtimeThr. 
 * Each uThr executes 20 iterations. At each iteration a uThr randomly selects one of 5 commands 
 * (nextEven, nextOdd, nextEvenFib, nextLargerRand, nextPrime) to enqueue in the requestQue, 
 * along with any other needed pieces of data, and waits for the result produced by this command. 
 * The uThread will continue to look for items in the return queue as long as it is not empty.
 */
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class UThr extends Thread {
	
	private ConcurrentLinkedQueue<Request> mRequestQueue;
	private ConcurrentLinkedQueue<Return> mReturnQueue;
	private ReentrantLock lock = new ReentrantLock();
	private int [] id;
	
	
	public UThr () { 
		// use shared request and return queues
		mRequestQueue = TCPclient.requestQueue;
		mReturnQueue = TCPclient.returnQueue;
		// reference id from TCPclient
		this.id = TCPclient.id;
	}
	
	public void run()  { 
		Random rand = new Random();
		int selector;
		String command [] = {"nextEven", "nextOdd", "nextEvenFib", "nextLargerRand", "nextPrime"};
		Return reply;
		
		// add to request queue 
		for (int i = 0; i < 20; i++) { // 20 iterations
			selector = rand.nextInt(5); // random command 1-5
			mRequestQueue.add(new Request(id[0]++,command[selector]));
			
		}
		
		// continue to check returnQueue and see if it is empty
		while(true){
			// lock the critical section to ensure correct output
			lock.lock();
			try{
				if(!mReturnQueue.isEmpty()){
					reply = mReturnQueue.poll();
					if(reply != null){
						// print a request and its value
						System.out.println(" Request ID: " + reply.getID() + " got " + reply.getRequest() + ": " + reply.getValue());
					}
				}
			}
			finally{
				lock.unlock();
			}
		}
		
	}	

}
