/* Kristian Naranjo
 * Oscar Valdez
 * Jeannine Westerkamp
 * Josh Andreasian
 * Files Associated: Communicates with TCPserver via socket
 * Description: This program first initializes the ip address of the host,
 * along with the same port number declared in the server program. It then
 * initializes variables to get the input and output stream from the server.
 * The server will give output depending on what is entered by the user.
 * The user can enter nextPrime, nextLargerRand, or nextEvenFib and they will
 * get the next 5 corresponding numbers. If the user enters anything else, the
 * server will return "Connected". Or, the client will stop if the user enters quit.
 * */

import java.util.concurrent.ConcurrentLinkedQueue;

public class TCPclient {
	static ConcurrentLinkedQueue<Request> requestQueue = new ConcurrentLinkedQueue<Request>();
	static ConcurrentLinkedQueue<String> returnQueue = new ConcurrentLinkedQueue<String>();

	public static void main(String[] args) {
		
		for(int i=0; i<8; i++){
			UThr u = new UThr(requestQueue, returnQueue);
			u.start();
		}
		
		RuntimeThread r = new RuntimeThread();
		r.start();
	}

}
