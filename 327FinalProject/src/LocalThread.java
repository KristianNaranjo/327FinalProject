import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class LocalThread extends Thread{
	private int lastEven;
	private int lastOdd;
	private Request mRequest;
	private ConcurrentLinkedQueue<String> mReturnQueue;
	private ReentrantLock lock = new ReentrantLock();
	
	public LocalThread(Request request, ConcurrentLinkedQueue<String> returnQueue){
		mRequest = request;
		mReturnQueue = returnQueue;
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
			lastEven += 2;
			return lastEven;
		}
		finally{
			lock.unlock();
		}
	}
	
	public int nextOdd(){
		lock.lock();
		try{
			if(lastOdd == 0){
				lastOdd++;
			}
			else{
				lastOdd += 2;
			}
			return lastOdd;
		}
		finally{
			lock.unlock();
		}
	}
}
