package zad2;

public class UpThread extends Thread {

    Counter counter;

    public UpThread(Counter counter){
        this.counter = counter;
    }

    public void run(){
        for( int i=0;i<100000000;i++){
            this.counter.increase();
        }
    }
}
