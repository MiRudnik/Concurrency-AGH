package zad1;

public class Main {



    public static void main(String argv[]){

        Buffer buffer = new Buffer(40);

        Processor toB = new Processor(0,'B', 300, buffer);
        Processor toC = new Processor(1,'C',200, buffer);
        Processor toD = new Processor(2,'D', 500, buffer);
        Processor toE = new Processor(3,'E', 100, buffer);

        Thread B = new Thread(toB);
        Thread C = new Thread(toC);
        Thread D = new Thread(toD);
        Thread E = new Thread(toE);

        B.start();
        C.start();
        D.start();
        E.start();

        try {
            B.join();
            C.join();
            D.join();
            E.join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
