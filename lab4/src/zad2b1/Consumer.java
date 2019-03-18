package zad2b1;

public class Consumer implements Runnable{

    private int max;
    private Buffer buffer;

    Consumer(int max, Buffer buffer){
        this.max = max;
        this.buffer = buffer;
    }

    public void run(){
        while(true){
            int rand = (int) (Math.random() * max + 1);
            long start = System.nanoTime();
            buffer.take(rand);
            long end = System.nanoTime();
            //System.out.println((end-start)/1000.0);
        }
    }
}
