package zad2;

public class Counter{

    public int number;

    public Counter(){
        this.number = 0;
    }

    public synchronized void increase(){
        this.number ++;
    }

    public synchronized void decrease(){
        this.number --;
    }

}
