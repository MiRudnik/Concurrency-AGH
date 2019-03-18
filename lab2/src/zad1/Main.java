package zad1;

public class Main {

    public static void main(String[] args) {

        BinarySemaphore semaphore = new BinarySemaphore();
        Counter counter = new Counter(semaphore);

        UpThread upThread = new UpThread(counter);
        DownThread downThread = new DownThread(counter);

        upThread.start();
        downThread.start();

        try {
            upThread.join();
            downThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
