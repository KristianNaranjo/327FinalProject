import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Request {
	
	private String mRequest;
	private Condition mCond;
	private ReentrantLock mLock;
	public Request(String request, Condition cond, ReentrantLock lock){
		mRequest = request;
		mCond = cond;
		mLock = lock;
	}
	
	public String getRequest() {
		return mRequest;
	}
	
	public Condition getCondition() {
		return mCond;
	}

	public ReentrantLock getLock() {
		return mLock;
	}
}
