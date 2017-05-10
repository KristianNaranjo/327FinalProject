import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class LocalThread extends Thread{
	private int[] mLastEven;
	private int[] mLastOdd;
	private Request mRequest;
	private ConcurrentLinkedQueue<String> mReturnQueue;
	private ReentrantLock lock = new ReentrantLock();
	
	public LocalThread(Request request, ConcurrentLinkedQueue<String> returnQueue,
			int[] lastEven, int[] lastOdd){
		mRequest = request;
		mReturnQueue = returnQueue;
		mLastEven = lastEven;
		mLastOdd = lastOdd;
	}
	
	public void run(){
		switch(mRequest.getRequest()){
			case "nextEven":
				mReturnQueue.add(Integer.toString(nextEven()));
				break;
			case "nextOdd":
				mReturnQueue.add(Integer.toString(nextOdd()));
				break;				
		}
		mRequest.getLock().lock();
		try{
			mRequest.getCondition().signal();
		}
		finally{
			mRequest.getLock().unlock();
		}
	}
	
	public int nextEven(){
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
