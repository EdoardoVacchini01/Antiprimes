package antiprimes;

public class NumberProcessor extends Thread{
	AntiPrimesSequence sequence;
	Number toProcess;
	
	public NumberProcessor(AntiPrimesSequence sequence) {
		this.sequence = sequence;
	}
	
	public void run() {
		while(true) {
			acceptRequests();
			sequence.addAntiPrime(AntiPrimes.nextAntiPrimeAfter(getNextToProcess()));
		}
	}
	
	public synchronized void acceptRequests() {
		this.toProcess = null;
	}
	
	public synchronized Number getNextToProcess() {
		while( toProcess == null) {
			try {
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return toProcess;
	}
	
	public synchronized void nextAntiprime(Number number) {
		if(toProcess != null) {
			if(number.getValue() == toProcess.getValue())
				return;
			else
				try {
					wait();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		this.toProcess = number;
		notify();
	}
	
}
