package zad2b;

import java.util.ArrayList;
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
    private LinkedList<Integer> consumersWaiting;
    private LinkedList<Integer> producersWaiting;

    Buffer(int size){
        this.buffer = new char[size];
        fill(buffer, '_');
        this.size = size;
        this.reserved = 0;
        this.putting = this.lock.newCondition();
        this.taking = this.lock.newCondition();
        this.firstProducer = this.lock.newCondition();
        this.firstConsumer = this.lock.newCondition();
        this.consumersWaiting = new LinkedList<>();
        this.producersWaiting = new LinkedList<>();
    }

    void put(int id, int amount, char value){
        lock.lock();
        try {
            this.producersWaiting.addLast(id);
            while (producersWaiting.getFirst() != id) {
                putting.await();
            }
            while (amount > size - reserved) {
                firstProducer.await();
            }
            this.producersWaiting.removeFirst();
            reserved += amount;
            System.out.print("[PUT " + amount + "]\t");
            for(int i=0;amount > 0;i++){
                if(buffer[i] == '_'){
                    buffer[i] = value;
                    amount--;
                }
            }
            printBuffer();
            // move next one as first
            putting.signalAll();
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


    void take(int id, int amount){
        lock.lock();
        try{
            this.consumersWaiting.addLast(id);
            while(consumersWaiting.getFirst() != id){
                taking.await();
            }
            while(amount > reserved){
                firstConsumer.await();
            }
            this.consumersWaiting.removeFirst();
            reserved -= amount;
            System.out.print("[TOOK " + amount + "]\t");
            for(int i=0;amount > 0;i++){
                if(buffer[i] != '_'){
                    buffer[i] = '_';
                    amount--;
                }
            }
            printBuffer();
            // move next one as first
            taking.signalAll();
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
