import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* 
 * There are 8 or more uThr's running on the same local machine as its runtimeThr. 
 * Each uThr executes 20 iterations. 
 * At each iteration a uThr randomly selects one of 5 commands 
 * (nextEven, nextOdd, nextEvenFib, nextLargerRand, nextPrime) to enqueue in the requestQue, 
 * along with any other needed pieces of data, and waits for the result produced by this command. 
 * After its result is enqueued in the returnQue, this thread fetches the returned value and outputs on the terminal.
 */
public class UThr extends Thread {
	
	private static ConcurrentLinkedQueue<Request> mRequestQueue = new ConcurrentLinkedQueue<Request>();
	private static ConcurrentLinkedQueue<Integer> mReturnQueue = new ConcurrentLinkedQueue<Integer>();
	private ReentrantLock lock = new ReentrantLock();
	
	
	public UThr (ConcurrentLinkedQueue<Request> requestQueue, ConcurrentLinkedQueue<Integer> returnQueue) { 
		mRequestQueue = requestQueue;
		mReturnQueue = returnQueue;
	}
	
	public void run()  { 
		Random rand = new Random();
		int selector;
		String command [] = {"nextEven", "nextOdd", "nextEvenFib", "nextLargerRand", "nextPrime"};
		String reply; 
		
		// add to request queue 
		for (int i = 0; i < 20; i++) { // 20 iterations
			selector = rand.nextInt(5); // random command 1-5
			ReentrantLock lock = new ReentrantLock();
			Condition cond = lock.newCondition();
			mRequestQueue.add(new Request(command[selector],cond,lock));
		

			// read from return queue
			lock.lock();
			try{
				cond.await();
			
				if (!mReturnQueue.isEmpty()) { // while queue is not empty  
					try { 
					 reply = mReturnQueue.poll().toString();
					 System.out.println("Reply from runtime thread: " + reply);
					} catch (Exception e) { 
						System.out.println("Empty Queue");
					}
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally{
				lock.unlock();
			}
			
		}
		
		
		
	}	

}
