package zad2;

public class Customer extends Thread {

    private int customerID;
    private Semaphore semaphore;


    public Customer(int customerID, Semaphore semaphore){
        this.customerID = customerID;
        this.semaphore = semaphore;
    }

    public void run(){
        semaphore.lock();
        System.out.println("[Customer " + customerID + "] I took a busket.");
        try {
            sleep(500);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        semaphore.unlock();
        System.out.println("[Customer " + customerID + "] I returned a busket.");
    }

}
