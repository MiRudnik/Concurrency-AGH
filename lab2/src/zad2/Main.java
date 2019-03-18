package zad2;

public class Main {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(2);

        Customer customer1 = new Customer(1,semaphore);
        Customer customer2 = new Customer(2,semaphore);
        Customer customer3 = new Customer(3,semaphore);
        Customer customer4 = new Customer(4,semaphore);
        Customer customer5 = new Customer(5,semaphore);

        customer1.start();
        customer2.start();
        customer3.start();
        customer4.start();
        customer5.start();

        try {
            customer1.join();
            customer2.join();
            customer3.join();
            customer4.join();
            customer5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
