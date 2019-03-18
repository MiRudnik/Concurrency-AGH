package zad1;

public class UpThread extends Thread {

    private Counter counter;

    public UpThread(Counter counter){
        this.counter = counter;
    }

    public void run(){
        for( int i=0;i<1000;i++){
            counter.increase();
        }
    }
}
