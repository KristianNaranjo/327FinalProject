import java.util.Random;

public class TCPprotocol extends Thread {
	
	private int mLastFib;
	private int mLastFib2;
	private int mLastLargerRand;
	private int mLastPrime;
	
	private String input;
	private String output;
	
	
	public TCPprotocol(String input, int lastFib, int lastFib2,
			int lastLargerRand, int lastPrime){
		this.input = input;
		mLastFib = lastFib;
		mLastFib2 = lastFib2;
		mLastLargerRand = lastLargerRand;
		mLastPrime = lastPrime;
	}
	
	public void run(){
		switch(input){
			case "nextPrime":
				output = Integer.toString(nextPrime());
				break;
			case "nextLargerRand":
				output = Integer.toString(nextLargerRand());
				break;
			case "nextEvenFib":
				output = Integer.toString(nextEvenFib());
				break;
			case "quit":
				output = "quit";
				break;
			default:
				output = "Connected";
				break;
		}
	}
	
	public String getOutput(){
		return output;
	}
	
	public int nextPrime(){
		while(!isPrime(++mLastPrime)){
		}
		return mLastPrime;
	}
	
	public boolean isPrime(int n){
		if(n<2)
			return false;
		for(int i = 2; i <= (int)Math.sqrt(n); i++){
			if(n % i == 0)
				return false;
		}
		return true;
	}
	
	public int nextEvenFib(){
		if(mLastFib == 0 && mLastFib2 == 0){
			mLastFib2 = 1;
		}
		do{
			int temp = mLastFib2;
			mLastFib2 += mLastFib;
			mLastFib = temp;
		} while(mLastFib2 % 2 != 0);
		return mLastFib2;
	}
	
	public int nextLargerRand(){
		Random rand = new Random();
		mLastLargerRand = rand.nextInt(100)+mLastLargerRand;
		return mLastLargerRand;
	}

}
