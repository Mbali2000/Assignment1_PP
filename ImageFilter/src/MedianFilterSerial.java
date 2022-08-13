import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import javax.imageio.*;




public class MedianFilterSerial {
// use areas for rgb values so that you can sort them afterwards

static BufferedImage img;
static File f;
static BufferedImage newPic;
static String output;

//time stamp
static long sTime;
static long eTime;

public MedianFilterSerial(){
    img = null;
    f = null;

}
public static void main(String[]args) throws IOException{
    try (Scanner sc = new Scanner(System.in)) {
        //file name
       /*  System.out.println("Enter input image name: ");
        String input = sc.nextLine();
        
        //output file name
        System.out.println("Enter output image name: ");
        output = sc.nextLine();*/

        //window size
        System.out.println("Enter Window size: ");
        int win = sc.nextInt();
        

        int arrLength = win*win;

        //read in image
        try {
            f = new File("C:\\Users\\Thabani\\Desktop\\Assignment1_PP\\Assignment1_PP\\ImageFilter\\import_img\\image2.jpg");
            img = ImageIO.read(f);// assigning file to image variable
            newPic = ImageIO.read(f);
        } catch (Exception e) {
            //handle exception
            System.out.println(e);
        } 

        //get image width and height
        //int width = img.getWidth();
        //int height = img.getHeight();
        //int tot = win^2;

        
        ArrayList<Integer>  red = new ArrayList<Integer>();
        ArrayList<Integer> gre= new ArrayList<Integer>();
        ArrayList<Integer> blu = new ArrayList<Integer>();
        ArrayList<Integer> aph = new ArrayList<Integer>();

        sTime = System.currentTimeMillis(); //start of execution

        //window limit
        int surpix = (win -1)/2;

        //image smoothing
        for(int row = surpix; row< img.getHeight() - surpix; row++){
            for(int col = surpix; col< img.getWidth() - surpix; col++){
                //int sumR=0;int sumG=0;int sumB=0;

                
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
                //System.out.println("before clear" + aph.size());
            
                Collections.sort(red);
                Collections.sort(gre);
                Collections.sort(blu);
                Collections.sort(aph);

                int div = (arrLength - 1)/2;
                
                int a = aph.get(div);
                int r = red.get(div);
                int g = gre.get(div);
                int b = blu.get(div);


                //new pixel rgb values that are the median values of the middle position of the sorted array
                int np =(a<<24)|(r<<16)|(g<<8)|(b); 

                //sets new pixel value with the median value
                newPic.setRGB(col, row, np);  // row and column need to be the width and height if the window size

                aph.clear();
                red.clear();
                gre.clear();
                blu.clear();
                //System.out.println("after clear" +aph.size());

            }
        }
        eTime = System.currentTimeMillis(); //end of execution
    }
    //write image
    try {
        //String output;
        f = new File("C:\\Users\\Thabani\\Desktop\\Assignment1_PP\\Assignment1_PP\\ImageFilter\\export_img\\output.jpg");
        ImageIO.write(newPic,"jpg", f);
    } catch (Exception e) {
        //handle exception
        System.out.println(e);
    }

    //total execution duration
    System.out.println("Total execution time: "+(eTime-sTime)+"ms");

}


}

