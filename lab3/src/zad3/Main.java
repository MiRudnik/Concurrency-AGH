package zad3;

public class Main {

    public static void main(String[] args) {

        Waiter waiter = new Waiter();

        Client female1 = new Client(1, waiter);
        Client male1 = new Client(1, waiter);
        Client female2 = new Client(2, waiter);
        Client male2 = new Client(2, waiter);
        Client female3 = new Client(3, waiter);
        Client male3 = new Client(3, waiter);
        Client female4 = new Client(4, waiter);
        Client male4 = new Client(4, waiter);

        Thread female1Thread = new Thread(female1);
        Thread male1Thread = new Thread(male1);
        Thread female2Thread = new Thread(female2);
        Thread male2Thread = new Thread(male2);
        Thread female3Thread = new Thread(female3);
        Thread male3Thread = new Thread(male3);
        Thread female4Thread = new Thread(female4);
        Thread male4Thread = new Thread(male4);

        female1Thread.start();
        male1Thread.start();
        female2Thread.start();
        male2Thread.start();
        female3Thread.start();
        male3Thread.start();
        female4Thread.start();
        male4Thread.start();

        try{
            female1Thread.join();
            male1Thread.join();
            female2Thread.join();
            male2Thread.join();
            female3Thread.join();
            male3Thread.join();
            female4Thread.join();
            male4Thread.join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
