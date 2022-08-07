import java.io.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.*;

import javax.swing.plaf.ColorUIResource;

import javafx.scene.paint.Color;

public class MeanFilterSerial{
    static BufferedImage img;
    static File f;
    

    public MeanFilterSerial(){
        img = null;
        f = null;
    }


    //get image pixels
    public static void getPixels(){
        for(int row = 0; row<img.getHeight(); row++){
            for(int col =0; col<img.getHeight(); col++){
               
            }
        }

    }

    public static void main(String[]args) throws IOException{
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
        int red = 0;
        int gre = 0;
        int blu = 0;

        //image smoothing
        for(int row = 1; row< img.getHeight()- 1; row++){
            for(int col = 1; col< img.getWidth() - 1; col++){
                int sumR=0;int sumG=0;int sumB=0;
                
                //get image pixel at specific coordinate
                int p = img.getRGB(0, 0);
                //window of 3 by 3 row and column
                red = red + (p>>16) & 0xff;
                gre = gre + (p>>8) & 0xff;
                blu = blu + (p) & 0xff;

            }
        }

        //new pixel
        np = (red/(k*k)<<16)|(gre/(k*k)<<8)|(blu/(k*k)); //k is the window size 3x3 *inside window for loop

        //sets new pixel value with average
        img.setRGB(col, row, p);  // row and column need to be the width and height if the window size


        //write image
        try {
            f = new File("C:\\Users\\Thabani\\Desktop\\Assignment1_PP\\Assignment1_PP\\ImageFilter\\exportimg");
            ImageIO.write(img,"jpg", f);
        } catch (Exception e) {
            //handle exception
            System.out.println(e);
        }

    }



}