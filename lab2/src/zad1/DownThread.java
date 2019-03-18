package zad1;

public class DownThread extends Thread {

    private Counter counter;

    public DownThread(Counter counter){
        this.counter = counter;
    }

    public void run(){
        for( int i=0;i<1000;i++){
            counter.decrease();
        }
    }

}
