/* Kristian Naranjo
 * Oscar Valdez
 * Jeannine Westerkamp
 * Josh Andreasian
 * Files Associated: UThr.java and RuntimeThread.java
 * Description: This program first initializes both the request queue and the return queue that will
 * be used by the threads in the program. This file spawns 8 uThreads and 1 runtime thread.
 * The uThr will add requests to the request queue and the runtimeThread will send requests to
 * either the local thread or the network thread. The local thread and the network thread will
 * add their results to the return queue.
 * */

import java.util.concurrent.ConcurrentLinkedQueue;

public class TCPclient {
	// initialize queues as static so they can be referenced by other classes
	static ConcurrentLinkedQueue<Request> requestQueue = new ConcurrentLinkedQueue<Request>();
	static ConcurrentLinkedQueue<Return> returnQueue = new ConcurrentLinkedQueue<Return>();
	// reference id number by UThr so each request has a new id
	static int[] id = new int[1];

	public static void main(String[] args) {
		
		// spawn 8 uThreads
		for(int i=0; i<8; i++){
			UThr u = new UThr();
			u.start();
		}
		
		// spawn the RuntimeThread
		RuntimeThread r = new RuntimeThread();
		r.start();
	}

}
