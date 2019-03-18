package zad2a;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Arrays.fill;


public class Buffer {

    private char[] buffer;
    private int size;
    private int reserved;
    private Lock lock = new ReentrantLock();
    private Condition putting;
    private Condition taking;

    Buffer(int size){
        this.buffer = new char[size];
        fill(buffer, '_');
        this.size = size;
        this.reserved = 0;
        this.putting = this.lock.newCondition();
        this.taking = this.lock.newCondition();
    }

    void put(int amount, char value){
        lock.lock();
        try {
            while (amount > size - reserved) {
                putting.await();
            }
            reserved += amount;
            System.out.print("[PUT " + amount + "]\t");
            for(int i=0;amount > 0;i++){
                if(buffer[i] == '_'){
                    buffer[i] = value;
                    amount--;
                }
            }
            printBuffer();
            taking.signalAll();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }


    void take(int amount){
        lock.lock();
        try{
            while(amount > reserved){
                taking.await();
            }
            reserved -= amount;
            System.out.print("[TAKEN " + amount + "]\t");
            for(int i=0;amount > 0;i++){
                if(buffer[i] != '_'){
                    buffer[i] = '_';
                    amount--;
                }
            }
            printBuffer();
            putting.signalAll();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    private void printBuffer(){
        for(int i=0; i<size; i++){
            System.out.print(buffer[i] + " ");
        }
        System.out.print("\n");
    }

}
