package antiprimes;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


/**
 * Represent the sequence of antiprimes found so far.
 */
public class AntiPrimesSequence extends Observable{

    /**
     * The numbers in the sequence.
     */
    private List<Number> antiPrimes = new ArrayList<>();
    NumberProcessor thread;

    /**
     * Create a new sequence containing only the first antiprime (the number '1').
     */
    public AntiPrimesSequence() {
        this.reset();
        thread = new NumberProcessor(this);
    	thread.start();
    }

    /**
     * Clear the sequence so that it contains only the first antiprime (the number '1').
     */
    public synchronized void reset() {
        antiPrimes.clear();
        antiPrimes.add(new Number(1, 1));
    }

    /**
     * Find a new antiprime and add it to the sequence.
     */
    public void computeNext() {
        //antiPrimes.add(AntiPrimes.nextAntiPrimeAfter(getLast()));
    	thread.nextAntiprime(this.getLast());
    }

    /**
     * Return the last antiprime found.
     */
    public synchronized Number getLast() {
        int n = antiPrimes.size();
        return antiPrimes.get(n - 1);
    }

    /**
     * Return the last k antiprimes found.
     */
    public synchronized List<Number> getLastK(int k) {
        int n = antiPrimes.size();
        if (k > n)
            k = n;
        return antiPrimes.subList(n - k, n);
    }    
    
    public synchronized void addAntiPrime(Number number) {
    	this.antiPrimes.add(number);
    	this.setChanged();
    	this.notifyObservers();
    }
    
}
