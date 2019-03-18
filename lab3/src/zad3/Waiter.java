package zad3;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {

    private Lock lock = new ReentrantLock();
    private ArrayList<Integer> pairsWaiting;
    private Condition freed;
    private Condition paired;
    private int currentPair;
    private int spots;

    public Waiter(){
        this.pairsWaiting = new ArrayList<Integer>();
        this.freed = lock.newCondition();
        this.paired = lock.newCondition();
        this.currentPair = 0;
        this.spots = 2;
    }

    void book(int pair){
        lock.lock();
        try{
            pairsWaiting.add(pair);
            while(!this.isPaired(pair)){
                // companion isn't here
                paired.await();
            }
            // has a companion
            while((this.spots%2 != 0 || this.spots == 0) && pair != currentPair){
                freed.await();
                // both waiting for their turn
            }
            // one person comes in
            this.spots --;
            this.currentPair = pair;
            // calls their companion
            freed.signalAll();
            if(spots%2 == 1){
                pairsWaiting.remove(Integer.valueOf(pair));
                pairsWaiting.remove(Integer.valueOf(pair));
                // removing from queue
            }
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    void free(){
        lock.lock();
        this.currentPair = 0;
        this.spots ++;
        // freeing after second person left
        if(this.spots%2 == 0){
            freed.signal();
        }
        lock.unlock();
    }

    private boolean isPaired(int pair){
        int exists = 0;
        for(int pairNumber : pairsWaiting){
            if(pairNumber == pair){
                exists++;
            }
        }
        if(exists == 1){
            return false;
        }
        else{
            paired.signalAll();
            return true;
        }
    }

}
