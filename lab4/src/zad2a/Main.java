package zad2a;

import java.util.ArrayList;

public class Main {

    public static void main(String argv[]){

        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();

        int M = 50;
        int PK = 2;
        Buffer buffer = new Buffer(2*M);

        for(int i=0; i<PK;i++){
            char value = 'A';
            value += i;
            producers.add(new Producer(M, value, buffer));
            consumers.add(new Consumer(M, buffer));
        }

        for(int i=0; i<PK;i++){
            threads.add(new Thread(producers.get(i)));
            threads.add(new Thread(consumers.get(i)));
        }

        for(Thread t : threads){
            t.start();
        }

        try {
            for (Thread t : threads) {
                t.join();
            }
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }

    }
}
