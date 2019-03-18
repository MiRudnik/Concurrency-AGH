package zad2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Arrays.fill;

public class PrintingMonitor {

    private Lock lock = new ReentrantLock();
    private int freePrinters;
    private boolean[] printers;
    private Condition freed;

    public PrintingMonitor(int number){
        this.freePrinters = number;
        this.printers = new boolean[number];
        fill(printers,true);
        this.freed = lock.newCondition();
    }

    int book(){
        lock.lock();
        int id = -1;
        try {
            while (freePrinters == 0) {
                freed.await();
            }
            for(int i=0;i<printers.length;i++){
                if(printers[i]) {
                    printers[i] = false;
                    freePrinters--;
                    id = i;
                    break;
                }
            }
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        finally{
            lock.unlock();
        }
        return id;
    }

    void free(int id){
        lock.lock();
        printers[id] = true;
        freePrinters++;
        freed.signal();
        lock.unlock();
    }

}
