/* Kristian Naranjo
 * Oscar Valdez
 * Jeannine Westerkamp
 * Josh Andreasian
 * Files Associated: TCPclient.java
 * Description: The RuntimeThread is spawned by the TCPclient class and uses both the requestQueue and returnQueue.
 * It will continue to dequeue from the requestQueue while there are still requests. It will either spawn a localThread
 * or a networkThread depending on the request. A request for nextEven or nextOdd will spawn the localThread. Otherwise,
 * the networkThread will be spawned.
 */
import java.util.concurrent.ConcurrentLinkedQueue;

public class RuntimeThread extends Thread {


	private ConcurrentLinkedQueue<Request> mRequestQueue = new ConcurrentLinkedQueue<Request>();
	private ConcurrentLinkedQueue<Return> mReturnQueue = new ConcurrentLinkedQueue<Return>();
	private int[] lastEven = new int[1];
	private int[] lastOdd = new int[1];
	
	public RuntimeThread(){
		mRequestQueue = TCPclient.requestQueue;
		mReturnQueue = TCPclient.returnQueue;
	}
	
	public void run(){
		Request item = null;
        Thread t;
        while(!mRequestQueue.isEmpty()){
	        switch (mRequestQueue.peek().getRequest()) {
	        	case "nextEven": 
	        		item = mRequestQueue.poll();
	        	    t = new Thread(new LocalThread(item, mReturnQueue, lastEven, lastOdd));
					t.start();
	        		break;
	        	case "nextOdd":
	        		item = mRequestQueue.poll();
	        	    t = new Thread(new LocalThread(item, mReturnQueue, lastEven, lastOdd));
					t.start();
	        		break;
	        	case "nextEvenFib":
	        		item = mRequestQueue.poll();
	        	    t = new Thread(new NetworkThread(item, mReturnQueue));
					t.start();
	        		break;
	            case "nextLargerRand": 
	            	item = mRequestQueue.poll();
	        	    t = new Thread(new NetworkThread(item, mReturnQueue));
					t.start();
	                break;
	            case "nextPrime": 
	            	item = mRequestQueue.poll();
	        	    t = new Thread(new NetworkThread(item, mReturnQueue));
					t.start();
	                break;
	            default: System.out.println("Invalid Entry");
	                break;
	        }
	        System.out.println("Sent request " + item.getID() +": "+item.getRequest());
        }
	}

}
