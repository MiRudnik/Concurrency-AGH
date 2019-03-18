package zad3;

public class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < 50;   i++) {
            buffer.put("message "+i);
            System.out.println("Message "+ i + " put");
        }

    }
}
