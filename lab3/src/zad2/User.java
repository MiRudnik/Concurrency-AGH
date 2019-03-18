package zad2;


import static java.lang.Thread.sleep;

public class User implements Runnable {

    private int id;
    private PrintingMonitor printingMonitor;

    public User(int id, PrintingMonitor printingMonitor) {
        this.id = id;
        this.printingMonitor = printingMonitor;
    }

    public void run() {

        for (int i = 0; i < 10; i++) {

            System.out.format("[User %d] Created task.\n",this.id);
            int printer = printingMonitor.book();
            System.out.format("[User %d] Using printer no. %d.\n", this.id, printer+1);
            try {
                sleep(500);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            printingMonitor.free(printer);
        }

    }
}


