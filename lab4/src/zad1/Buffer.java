package zad1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;

import static java.util.Arrays.fill;

public class Buffer {

    private int size;
    char[] buffer;
    Semaphore[] semaphores;

    public Buffer(int N){
        this.size = N;
        this.buffer = new char[N];
        fill(buffer, 'A');
        this.semaphores = new Semaphore[N];
        // first process may enter every field from the beginning
        this.semaphores[0] = new Semaphore(N);
        for(int i=1; i<N; i++){
            this.semaphores[i] = new Semaphore(0);
        }
    }

    private Semaphore printing = new Semaphore(1);

    public void process(int field, char value){
        this.buffer[field] = value;
        try{
            printing.acquire();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        for(char c : buffer){
            System.out.print(c + " ");
        }
        System.out.print("\n");
        printing.release();
    }

    public int getSize(){
        return size;
    }
}
