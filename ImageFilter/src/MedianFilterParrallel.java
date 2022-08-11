import java.util.concurrent.RecursiveTask;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


public class MedianFilterParrallel extends RecursiveTask {

    static BufferedImage img;
    static File f;
    static BufferedImage newPic = null;

    public MedianFilterParrallel(){
        img = null;
    }


   

    public static void main(String[] args) {
        
    }
    
}
