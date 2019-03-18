package zad3;

import static java.lang.Thread.sleep;

public class Client implements Runnable{

    private int pair;
    private Waiter waiter;

    public Client(int pair, Waiter waiter){
        this.pair = pair;
        this.waiter = waiter;
    }

    public void run(){

        for (int i = 0; i < 5; i++) {
            try {
                waiter.book(this.pair);
                System.out.format("[Pair %d] Started eating.\n", this.pair);
                sleep(1000);
                waiter.free();
                System.out.format("[Pair %d] Finished eating.\n", this.pair);
                //sleep(250);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
