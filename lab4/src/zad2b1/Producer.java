package zad2b1;

public class Producer implements Runnable{

    private int max;
    private char value;
    private Buffer buffer;

    Producer(int max, char value, Buffer buffer){
        this.max = max;
        this.value = value;
        this.buffer = buffer;
    }

    public void run(){
        while(true){
            int rand = (int) (Math.random() * max + 1);
            long start = System.nanoTime();
            buffer.put(rand, value);
            long end = System.nanoTime();
            //System.out.println((end-start)/1000.0);
        }
    }
}
