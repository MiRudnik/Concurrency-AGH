package zad1;

public class Counter{

    private int number;
    private BinarySemaphore semaphore;

    public Counter(BinarySemaphore semaphore){
        this.number = 0;
        this.semaphore = semaphore;
    }

    public void increase(){
        semaphore.lock();
        this.number ++;
        System.out.println(number);
        semaphore.unlock();
    }

    public void decrease(){
        semaphore.lock();
        this.number --;
        System.out.println(number);
        semaphore.unlock();
    }

}
