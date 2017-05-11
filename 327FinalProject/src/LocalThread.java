/* Kristian Naranjo
 * Oscar Valdez
 * Jeannine Westerkamp
 * Josh Andreasian
 * Files Associated: RuntimeThread.java
 * Description: The local thread is spawned by the runtimeThread if it gets a request for nextEven or nextOdd.
 * It will then calculate the value for the specified request and add it to the returnQueue.
 */
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class LocalThread extends Thread{
	private int[] mLastEven;
	private int[] mLastOdd;
	private Request mRequest;
	private ConcurrentLinkedQueue<Return> mReturnQueue;
	private ReentrantLock lock = new ReentrantLock();
	
	public LocalThread(Request request, ConcurrentLinkedQueue<Return> returnQueue,
			int[] lastEven, int[] lastOdd){
		// initialize variables passed by reference
		mRequest = request;
		mReturnQueue = returnQueue;
		mLastEven = lastEven;
		mLastOdd = lastOdd;
	}
	
	public void run(){
		switch(mRequest.getRequest()){ // make calculation depending on request name
		// add results to return queue
			case "nextEven":
				mReturnQueue.add(new Return(Integer.toString(nextEven()), mRequest.getRequest(), mRequest.getID()));
				break;
			case "nextOdd":
				mReturnQueue.add(new Return(Integer.toString(nextOdd()), mRequest.getRequest(), mRequest.getID()));
				break;				
		}
	}
	
	public int nextEven(){
		// lock critical section to prevent race condition
		lock.lock();
		try{
			mLastEven[0] += 2;
			return mLastEven[0];
		}
		finally{
			lock.unlock();
		}
	}
	
	public int nextOdd(){
		// lock critical section to prevent race condition
		lock.lock();
		try{
			if(mLastOdd[0] == 0){
				mLastOdd[0]++;
			}
			else{
				mLastOdd[0] += 2;
			}
			return mLastOdd[0];
		}
		finally{
			lock.unlock();
		}
	}
}
