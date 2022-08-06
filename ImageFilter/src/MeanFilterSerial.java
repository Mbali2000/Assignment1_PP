import java.io.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;

import javafx.scene.paint.Color;

public class MeanFilterSerial extends JPanel{
    static BufferedImage img;
    static File f;
    static int rgb[][][];

    public MeanFilterSerial(){
        img = null;
        f = null;
    }


    //get image pixels
    public static void getPixels(){
        for(int row = 0; row<img.getHeight(); row++){
            for(int col =0; col<img.getHeight(); col++){
                Color c = new Color(img.getRGB(col, row), col, col, col);//returns rgb value of each pixel in image
                rgb[0][row][col] = (int) c.getRed();   //red rgb value at row and height specific position
                rgb[1][row][col] = (int) c.getGreen(); //green rgb value at row and height specific position
                rgb[2][row][col] = (int) c.getBlue();  //blue rgb value at row and height specific value
            }
        }

    }

    public static void main(String[]args) throws IOException{
        //read in image
        try {
            f = new File("C:\\Users\\Thabani\\Desktop\\Assignment1_PP\\Assignment1_PP\\ImageFilter\\importimg\\Face.jpeg");
            img = ImageIO.read(f);// assigning file to image variable
            rgb = new int[3][img.getHeight()][img.getWidth()];
        } catch (Exception e) {
            //handle exception
            System.out.println(e);
        } 

        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();

        //get pixel value
        int p = img.getRGB(0, 0);

        //image smoothing
        getPixels(); //loads image pixels
        for(int row = 1; row< img.getHeight()- 1; row++){
            for(int col = 1; col< img.getWidth() - 1; col++){
                int sumR=0;int sumG=0;int sumB=0;
                
                //window of 3 by 3 row and column
                sumR = sumR + rgb[0][row][col];
                sumG = sumG + rgb[1][row][col];
                sumB = sumB + rgb[2][row][col];

                ColorUIResource c = new ColorUIResource(sumR, sumG, sumB);

                img.setRGB(col, row, c.getRGB());
            }
        }

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