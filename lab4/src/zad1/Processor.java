package zad1;

public class Processor implements Runnable {

    private int index;
    private char value;
    private int time;
    private Buffer buffer;

    public Processor(int index, char value, int time, Buffer buffer){
        this.index = index;
        this.value = value;
        this.time = time;
        this.buffer = buffer;
    }

    public void run(){
        for(int i=0;i<buffer.getSize();i++){
            try {
                buffer.semaphores[index].acquire();
                Thread.sleep(time);
                buffer.process(i, value);
                buffer.semaphores[index+1].release();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }

    }


}
