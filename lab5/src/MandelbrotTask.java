import java.util.ArrayList;
import java.util.concurrent.Callable;

public class MandelbrotTask implements Callable<ArrayList<Integer>> {

    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private final long HEIGHT = 600;
    private double zx, zy, cX, cY, tmp;

    private long from;
    private long to;

    public MandelbrotTask(long from, long to){
        this.from = from;
        this.to = to;
    }

    public ArrayList<Integer> call(){
        // list to keep answers
        ArrayList<Integer> results = new ArrayList<Integer>();
        for (long i = 0; i < to - from; i++){
            // call given method for each pixel
            results.add(computePixel((int) ((from + i) / HEIGHT), (int) ((from + i) % HEIGHT)));
        }
        return results;
    }

    public int computePixel(int x, int y){
        zx = zy = 0;
        cX = (x - 400) / ZOOM;
        cY = (y - 300) / ZOOM;
        int iter = MAX_ITER;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        return iter;
    }

}
