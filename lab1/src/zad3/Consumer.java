package zad3;

public class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < 25;   i++) {
            String message = buffer.take();
            System.out.println(message +  " taken");
        }

    }
}

