package zad2b;

public class Consumer implements Runnable{

    private int id;
    private int max;
    private Buffer buffer;

    Consumer(int id, int max, Buffer buffer){
        this.id = id;
        this.max = max;
        this.buffer = buffer;
    }

    public void run(){
        while(true){
            int rand = (int) (Math.random() * max + 1);
            long start = System.nanoTime();
            buffer.take(id, rand);
            long end = System.nanoTime();
            //System.out.println((end-start)/1000.0);
        }
    }
}
