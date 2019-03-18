package zad3;

public class Buffer{

    private String message;
    private int full;

    public Buffer(){
        this.message = "";
        this.full = 0;
    }

    public synchronized void put(String message){
        while(full != 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.message = message;
        this.full = 0;
        notifyAll();
    }

    public synchronized String take(){
        while(full != 0){
            try{
                wait();
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        String message = this.message;
        this.full = 1;
        notifyAll();
        return message;
    }
}

