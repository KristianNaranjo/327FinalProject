import java.util.concurrent.ConcurrentLinkedQueue;

public class RuntimeThread extends Thread {


	private ConcurrentLinkedQueue<Request> mRequestQueue = new ConcurrentLinkedQueue<Request>();
	private ConcurrentLinkedQueue<Integer> mReturnQueue = new ConcurrentLinkedQueue<Integer>();
	
	public RuntimeThread(){
		mRequestQueue = TCPclient.requestQueue;
		mReturnQueue = TCPclient.returnQueue;
	}
	
	public void run(){
		Request item = null;
        Thread t;
        
        switch (mRequestQueue.peek().getRequest()) {
        	case "nextEven": 
        		item = mRequestQueue.poll();
        	    t = new Thread(new LocalThread(item, mReturnQueue));
				t.start();
        		break;
        	case "nextOdd":
        		item = mRequestQueue.poll();
        	    t = new Thread(new LocalThread(item, mReturnQueue));
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
        System.out.println(mRequestQueue.peek());
		System.out.println(mReturnQueue.peek());
	}

}
