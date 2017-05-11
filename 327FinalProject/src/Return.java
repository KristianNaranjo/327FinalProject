import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//create an object Return to add requests to the return queue
public class Return {
	private String mVal;
	private String mRequest;
	private int mID;
	
	public Return(String value, String request, int id){
		mVal = value;
		mRequest = request;
		mID = id;
	}
	
	// get value calculated for a request
	public String getValue(){
		return mVal;
	}

	// get original request name
	public String getRequest(){
		return mRequest;
	}
	
	// get id number for a request made
	public int getID(){
		return mID;
	}
}
