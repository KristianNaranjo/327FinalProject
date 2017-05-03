/* 
 * There are 8 or more uThr's running on the same local machine as its runtimeThr. 
 * Each uThr executes 20 iterations. 
 * At each iteration a uThr randomly selects one of 5 commands 
 * (nextEven, nextOdd, nextEvenFib, nextLargerRand, nextPrime) to enqueue in the requestQue, 
 * along with any other needed pieces of data, and waits for the result produced by this command. 
 * After its result is enqueued in the returnQue, this thread fetches the returned value and outputs on the terminal.
 */
public class uThr extends Thread {

}
