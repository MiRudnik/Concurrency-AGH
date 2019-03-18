package zad2;

public class DownThread extends Thread {

    Counter counter;

    public DownThread(Counter counter){
        this.counter = counter;
    }

    public void run(){
        for( int i=0;i<100000000;i++){
            this.counter.decrease();
        }
    }

}
