package zad2;

public class Main {

    public static void main(String[] args) {

        PrintingMonitor printingMonitor = new PrintingMonitor(3);

        User user1 = new User(1, printingMonitor);
        User user2 = new User(2, printingMonitor);
        User user3 = new User(3, printingMonitor);
        User user4 = new User(4, printingMonitor);

        Thread userThread1 = new Thread(user1);
        Thread userThread2 = new Thread(user2);
        Thread userThread3 = new Thread(user3);
        Thread userThread4 = new Thread(user4);

        userThread1.start();
        userThread2.start();
        userThread3.start();
        userThread4.start();

        try{
            userThread1.join();
            userThread2.join();
            userThread3.join();
            userThread4.join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
