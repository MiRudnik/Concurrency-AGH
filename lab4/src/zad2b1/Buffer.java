package zad2b1;

import java.util.LinkedList;
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
    private Condition firstProducer;
    private Condition firstConsumer;
    private boolean isProducing = false;
    private boolean isConsuming = false;

    Buffer(int size){
        this.buffer = new char[size];
        fill(buffer, '_');
        this.size = size;
        this.reserved = 0;
        this.putting = this.lock.newCondition();
        this.taking = this.lock.newCondition();
        this.firstProducer = this.lock.newCondition();
        this.firstConsumer = this.lock.newCondition();
    }

    void put(int amount, char value){
        lock.lock();
        try {
            while (isProducing) {
                putting.await();
            }
            isProducing = true;
            while (amount > size - reserved) {
                firstProducer.await();
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
            isProducing = false;
            // choose a producer
            putting.signal();
            // call first consumer
            firstConsumer.signal();
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
            while(isConsuming){
                taking.await();
            }
            isConsuming = true;
            while(amount > reserved){
                firstConsumer.await();
            }
            reserved -= amount;
            System.out.print("[TOOK " + amount + "]\t");
            for(int i=0;amount > 0;i++){
                if(buffer[i] != '_'){
                    buffer[i] = '_';
                    amount--;
                }
            }
            printBuffer();
            isConsuming = false;
            // choose next consumer
            taking.signal();
            // call first producer
            firstProducer.signal();
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
