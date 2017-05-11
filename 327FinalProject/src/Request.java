// create an object Request to add requests to the request queue
public class Request {
	private int mID;
	private String mRequest;
	public Request(int id, String request){
		mID = id;
		mRequest = request;
	}
	
	// use id to correctly identify a specific request
	public int getID(){
		return mID;
	}
	
	// return the request name
	public String getRequest() {
		return mRequest;
	}
}
