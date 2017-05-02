import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class LocalThread extends Thread{
	private int lastEven;
	private int lastOdd;
	private String mRequest;
	private Queue<Integer> mReturnQueue = new LinkedList<Integer>();
	private ReentrantLock lock = new ReentrantLock();
	
	public LocalThread(String request, Queue<Integer> returnQueue){
		mRequest = request;
		mReturnQueue = returnQueue;
	}
	
	public void run(){
		switch(mRequest){
		case "nextEven":
			mReturnQueue.add(nextEven());
			break;
		case "nextOdd":
			mReturnQueue.add(nextOdd());
			break;				
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
