import java.io.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import javax.swing.JPanel;
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

        //image smoothing
        getPixels(); //loads image pixels
        for(int row = 1; row< img.getHeight()- 1; row++){
            for(int col = 1; col< img.getWidth() - 1; col++){
                
                //window of 3 by 3 row and column
                int meanRed = meanRed + rgb[0][row-1][col+1];
                int meanGreen = meanGreen + rgb[1][row][col];
                int meanBlue = meanBlue + rgb[2][row][col];

                img.setRGB(col, row, rgb);;
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