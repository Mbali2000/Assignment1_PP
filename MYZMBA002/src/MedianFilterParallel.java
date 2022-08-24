import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import javax.imageio.ImageIO;


public class MedianFilterParallel extends RecursiveAction {

    static BufferedImage img;
    static File f;
    static BufferedImage newPic;
    static int win;
    static int surpix;
    static int sq;


    //image bounds
    int sHeight;
    int eHeight;
    int sWidth;
    int eWidth;

    //time stamps
    static long sTime;
    static long eTime;
    static String output;
    static String input;

    //storage arraylists
    ArrayList<Integer>  red = new ArrayList<Integer>();
    ArrayList<Integer> gre= new ArrayList<Integer>();
    ArrayList<Integer> blu = new ArrayList<Integer>();
    ArrayList<Integer> aph = new ArrayList<Integer>();

    //sequential cutoff
    int seqCut = 350;



    public MedianFilterParallel(int sH,int eH,int sW,int eW){
        sHeight = sH;
        eHeight = eH;
        sWidth = sW;
        eWidth = eW; 
    }

    
    

    public void medianCompute(){
       for(int row = surpix ;row < img.getHeight() - surpix; row++){
        for(int col = surpix; col< img.getWidth() - surpix; col++){

            for(int x= row - surpix; x<= row + surpix; x++){
                for(int y= col - surpix; y<= col + surpix; y++){

                    //get image pixel at specific coordinate
                    int p = img.getRGB(y, x);
                    //window of 3 by 3 row and column
                    red.add((p>>16) & 0xff);
                    gre.add((p>>8) & 0xff);
                    blu.add((p) & 0xff);
                    aph.add((p>>24) & 0xff);

                }
            }
                //sort integer array before extraction median
                Collections.sort(red);
                Collections.sort(gre);
                Collections.sort(blu);
                Collections.sort(aph);

                //median extraction and allocation to variable from middle array position
                sq = win*win;
                int div = (sq - 1)/2;
                
                int a = aph.get(div);
                int r = red.get(div);
                int g = gre.get(div);
                int b = blu.get(div);


                //new pixel rgb values that are the median values of the middle position of the sorted array
                int np =(a<<24)|(r<<16)|(g<<8)|(b); 

                //sets new pixel value with the median value
                newPic.setRGB(col, row, np);  // row and column need to be the width and height if the window size

                //clearing existing arraylist before moving onto new window
                aph.clear();
                red.clear();
                gre.clear();
                blu.clear();
                

        }
       } 
       
    }

    public void compute(){
        if((eWidth - sWidth)< seqCut){

            medianCompute();
            return;

        }
        else{

            MedianFilterParallel left = new MedianFilterParallel(sHeight, eHeight, sWidth, (sWidth+eWidth)/2);
            MedianFilterParallel right = new MedianFilterParallel(sHeight, eHeight, (sWidth+eWidth)/2, eWidth);
            invokeAll(left, right);
        }

    }
   

    public static void main(String[] args) throws IOException{
        try (Scanner sc = new Scanner(System.in)) {
            //System.out.println("Enter Window size: ");
             input = args[0];

             output = args[1];

            String num = args[2];
            win = Integer.parseInt(num);

            surpix = (win-1)/2;
        

        }

        //read in image
        try {
            f = new File("import_img/"+input);
            img = ImageIO.read(f);// assigning file to image variable
            newPic = ImageIO.read(f);
        } catch (Exception e) {
            //handle exception
            System.out.println(e);
        } 

        

        ForkJoinPool fj = new ForkJoinPool();
        MedianFilterParallel mp = new MedianFilterParallel(0, img.getHeight(), 0, img.getWidth());
        // time stamp at start of algorithm execution run
        sTime = System.currentTimeMillis(); 

        fj.invoke(mp);

        //time stamp at end of algorithm execution taking at a time when all threads are finished with their execution
        eTime = System.currentTimeMillis(); 




        //write out image
        try {
            f = new File("export_img/"+output);
            ImageIO.write(newPic,"jpg", f);
        } catch (Exception e) {
            //handle exception
            System.out.println(e);
        }


        //duration calculation
        System.out.println("Total execution time: "+(eTime- sTime) +"ms");

        
    }
    
}
