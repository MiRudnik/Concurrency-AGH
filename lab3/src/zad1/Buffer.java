package zad1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer{

    private String message;
    private final Lock lock = new ReentrantLock();
    private final Condition full = lock.newCondition();
    private final Condition empty = lock.newCondition();

    public Buffer(){
        this.message = "";
    }

    public void put(String message){

        lock.lock();
        try {
            while(!this.message.equals("")){
                empty.await();
            }
            this.message = message;
            full.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally{
            lock.unlock();
        }
    }

    public String take(){
        lock.lock();
        String msg = "";
        try {
            while(this.message.equals("")){
                full.await();
            }
            msg = this.message;
            this.message = "";
            empty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally{
            lock.unlock();
        }
        return msg;
    }
}

