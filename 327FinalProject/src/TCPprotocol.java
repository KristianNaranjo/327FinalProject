import java.util.Random;

public class TCPprotocol extends Thread {
	
	private int lastFib = 0;
	private int lastFib2 = 0;
	private int lastLargerRand = 0;
	private int lastPrime = 0;
	
	private String input;
	private String output;
	
	
	public TCPprotocol(String input){
		this.input = input;
		run();
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
	
	public String getReturn(){
		return output;
	}
	
	public int nextPrime(){
		while(!isPrime(++lastPrime)){
		}
		return lastPrime;
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
		if(lastFib == 0 && lastFib2 == 0){
			lastFib2 = 1;
		}
		do{
			int temp = lastFib2;
			lastFib2 += lastFib;
			lastFib = temp;
		} while(lastFib2 % 2 != 0);
		return lastFib2;
	}
	
	public int nextLargerRand(){
		Random rand = new Random();
		lastLargerRand = rand.nextInt(100)+lastLargerRand;
		return lastLargerRand;
	}

}
