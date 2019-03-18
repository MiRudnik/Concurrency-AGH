package zad2;

public class Semaphore {

    private final int resources;
    private int available;

    public Semaphore(int resources){
        this.resources = resources;
        this.available = resources;
    }

    public synchronized void lock(){
        while(this.available == 0){
            try{
                wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        this.available --;
        notifyAll();
    }

    public synchronized void unlock(){
        while(this.available == resources){
            try{
                wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        this.available ++;
        notifyAll();
    }

}
