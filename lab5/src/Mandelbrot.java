import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.concurrent.*;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private final long WIDTH = 800;
    private final long HEIGHT = 600;
    private BufferedImage I;

    private Mandelbrot(int threads, int tasks){
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage((int)WIDTH, (int)HEIGHT, BufferedImage.TYPE_INT_RGB);
        // creating threads
        ExecutorService threadPool = Executors.newFixedThreadPool(threads);
        // list for Future objects
        ArrayList<Future<ArrayList<Integer>>> results = new ArrayList<>();
        // loop assigning tasks
        for (int i = 0; i < tasks; i++) {
            // creating tasks
            Callable<ArrayList<Integer>> mandelbrotTask = new MandelbrotTask((HEIGHT * WIDTH) * i / tasks,
                    (HEIGHT * WIDTH) * (i + 1) / tasks);
            // get pending result
            Future<ArrayList<Integer>> future = threadPool.submit(mandelbrotTask);
            results.add(future);
        }
        try {
            // loop retrieving each computation values
            for (long i = 0; i < tasks; i++) {
                // wait for result of each task and assign it
                ArrayList<Integer> singleResult = results.get((int)i).get();
                // get info from where to start coloring for given task result
                long start = HEIGHT * WIDTH * i / (long) tasks;
                // loop using each pixel value
                for (long j = 0; j < singleResult.size(); j++) {
                    int iter = singleResult.get((int) j);
                    I.setRGB((int) ((start + j) / HEIGHT), (int) ((start + j) % HEIGHT), iter | (iter << 8));
                }
            }
        }
        catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
    }


    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {

        for(int i=0; i<15; i++) {
            long start = System.nanoTime();
            new Mandelbrot(8, 800*600).setVisible(true);
            long end = System.nanoTime();
            System.out.println((end - start)/1000.0);
        }
    }
}
