package zad2;

public class Main {

    public static void main(String[] args) {

        Counter c = new Counter();

        UpThread upThread = new UpThread(c);
        DownThread downThread = new DownThread(c);

        upThread.start();
        downThread.start();

        try {
            upThread.join();
            downThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(c.number);

    }
}
