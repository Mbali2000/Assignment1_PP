import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import javax.imageio.ImageIO;


public class MeanFilterParrallel extends RecursiveAction{

    //golobal variables
    static BufferedImage img;
    static File f;
    static BufferedImage newPic;
    static String input;
    static String output;
    static int win;
    static int width;
    static int height;
    static int p;
    static int avg = win*win;

    //arguments
    int lo;
    int hi;
    int [] arr;
    static final int seqCut = win;

    public MeanFilterParrallel(int l, int h, int [] a){
        lo = l; hi = h; arr = a;

    }

    static final ForkJoinPool fjPool = new ForkJoinPool();

    //instantiates mean filtering algorithm
    @Override
    protected void compute() {
        if((hi - lo) < seqCut){
            //mean algorithm from sequential code
            int red = 0;
            int gre = 0;
            int blu = 0;
            int aph = 0;

            for(int x = 0; x < win; x++){
                for (int y = 0; y < win; y++){

                    //get pixel at specific position
                    p = img.getRGB(x, y);

                    //get rgb values at specific pixel
                    red = red + (p>>16) & 0xff;
                    gre = gre + (p>>8) & 0xff;
                    blu = blu + (p>>0) & 0xff;
                    aph = aph + (p>>24) & 0xff;



                }
                //set new pixel value
             
            }
        }else{
            
            MeanFilterParrallel left = new MeanFilterParrallel(lo, (hi+lo)/2, arr);
            MeanFilterParrallel right = new MeanFilterParrallel((hi+lo)/2, hi, arr);
            left.fork();

                MeanFilterParrallel rAns = right.compute(); 
                MeanFilterParrallel lAns = left.join();

                return lAns + rAns ;
        }
        
    }

    public static void main(String[] args) throws IOException {
        //input prompts
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter input file name: ");
        input = sc.nextLine();

        System.out.println("Enter output file name: ");
        output = sc.nextLine();

        System.out.println("Enter window size: ");
        win = sc.nextInt();

        //image read in
        try {
            f = new File("C:\\Users\\Thabani\\Desktop\\Assignment1_PP\\Assignment1_PP\\ImageFilter\\import_img"+input);
            img = ImageIO.read(f);// assigning file to image variable
            newPic = ImageIO.read(f);
        } catch (Exception e) {
            //handle exception
            System.out.println(e);
        } 

        //image size 
        width = img.getWidth();
        height = img.getHeight();
        
        //executing parallel algorithm
        static int MeanFilterParrallel(int [] array){
            MeanFilterParrallel t = new MeanFilterParrallel(l, h, a);
            ForkJoinPool.commonPool().invoke(t);

            return t.ans;
        }

        //image export
        try {
            f = new File("C:\\Users\\Thabani\\Desktop\\Assignment1_PP\\Assignment1_PP\\ImageFilter\\export_img"+ output);
            ImageIO.write(newPic,"jpg", f);
            //System.out.println("=====");
        } catch (Exception e) {
            //handle exception
            System.out.println(e);
        }

    }

    
}
