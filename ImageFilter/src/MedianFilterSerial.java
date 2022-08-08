import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import javax.imageio.*;




public class MedianFilterSerial {
// use areas for rgb values so that you can sort them afterwards

static BufferedImage img;
static File f;

//time stamp
static long sTime;
static long eTime;

public MedianFilterSerial(){
    img = null;
    f = null;

}
public static void main(String[]args) throws IOException{
    try (Scanner sc = new Scanner(System.in)) {
        System.out.println("Enter Window size: ");
        int win = sc.nextInt();

        int arrLength = win*win;

        //read in image
        try {
            f = new File("C:\\Users\\Thabani\\Desktop\\Assignment1_PP\\Assignment1_PP\\ImageFilter\\importimg\\Face.jpeg");
            img = ImageIO.read(f);// assigning file to image variable
        } catch (Exception e) {
            //handle exception
            System.out.println(e);
        } 

        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();

        int np = 0;
        int [] red = new int[win];
        int [] gre = new int[win];
        int [] blu = new int[win];

        sTime = System.currentTimeMillis(); //start of execution

        //image smoothing
        for(int row = 1; row< height- 1; row++){
            for(int col = 1; col< width - 1; col++){
                //int sumR=0;int sumG=0;int sumB=0;

                for(int x=0; x<win; x++){
                    for(int y=0; y<win; y++){
                
                        //get image pixel at specific coordinate
                        int p = img.getRGB(0, 0);
                        //window of 3 by 3 row and column
                        red[y] = +(p>>16) & 0xff;
                        gre[y] =  +(p>>8) & 0xff;
                        blu[y] =  +(p) & 0xff;
                    }
                }
                Arrays.sort(red);
                Arrays.sort(gre);
                Arrays.sort(blu);

                //new pixel rgb values that are the median values of the middle position of the sorted array
                np = (red[(arrLength-1)/2])|(gre[(arrLength-1)/2])|(blu[(arrLength-1)/2]); 

                //sets new pixel value with the median value
                img.setRGB(col, row, np);  // row and column need to be the width and height if the window size

            }
        }
        eTime = System.currentTimeMillis(); //end of execution
    }
    //write image
    try {
        f = new File("C:\\Users\\Thabani\\Desktop\\Assignment1_PP\\Assignment1_PP\\ImageFilter\\exportimg");
        ImageIO.write(img,"jpg", f);
    } catch (Exception e) {
        //handle exception
        System.out.println(e);
    }

    System.out.println("Total execution time: "+(eTime-sTime)+"ms");

}




}

