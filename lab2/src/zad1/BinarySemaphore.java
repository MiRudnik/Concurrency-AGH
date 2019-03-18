package zad1;

public class BinarySemaphore {

    private boolean unlocked;

    public BinarySemaphore(){
        this.unlocked = true;
    }

    public synchronized void lock(){
        while(!this.unlocked){
            try{
                wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        this.unlocked = false;
        //notifyAll();
    }

    public synchronized void unlock(){

        while(this.unlocked){
            try{
                wait();
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.unlocked = true;
        notifyAll();
    }


}
