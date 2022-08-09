import java.io.*;
import java.io.File;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import javax.imageio.*;

public class MeanFilterSerial{
    static BufferedImage img;
    static File f;
    static BufferedImage newPic;

    //time stamps
    static long sTime;
    static long eTime;
    static String output;

    public MeanFilterSerial(){
        img = null;
        f = null;
    }


    public static void main(String[]args) throws IOException{
        try (
        // set window size manipulation
        Scanner sc = new Scanner(System.in)) {
            //input file
            System.out.println("Enter inout file name: ");
            String input = sc.nextLine();

            //output file name
            System.out.println("Enter output file name: ");
            output = sc.nextLine();

            //window size
            System.out.println("Enter Window Size: ");

            int win = sc.nextInt();// size of window

            //read in image
            try {
                f = new File(input);
                img = ImageIO.read(f);// assigning file to image variable
                newPic = ImageIO.read(f);
            } catch (Exception e) {
                //handle exception
                System.out.println(e);
            } 
            

            //get image width and height
            int width = img.getWidth();
            int height = img.getHeight();


            
            

             sTime = System.currentTimeMillis(); // time stamp at start of algorithm execution run 
            //image smoothing
            for(int row = 1; row< height- 1; row++){
                for(int col = 1; col< width - 1; col++){
                     //int curRow = row;
                     //int curCol = col;
                     int red = 0;
                     int gre = 0;
                     int blu = 0;
                     int aph = 0;
                    for(int x=0; x<win; x++){
                        for(int y=0; y<win; y++){
                            //get image pixel at specific coordinate
                            int p = img.getRGB(x,y);
                            //window of 3 by 3 row and column
                            red = red + (p>>16) & 0xff;
                            gre = gre + (p>>8) & 0xff;
                            blu = blu + (p) & 0xff;
                            aph = aph + (p>>24) & 0xff;
                            //curCol++;
                        }
                        //curRow++;
                        //curCol = col;
                    }
                    //curRow = row;
                    //new pixel
                    int npix = (0xff000000)|(red/(win*win)<<16)|(gre/(win*win)<<8)|(blu/(win*win))|(aph/(win*win)<<24);
                    //System.out.println(npix + " =====");
                    //sets new pixel value with average
                    newPic.setRGB(col, row, npix);  // row and column need to be the width and height if the window size
                }
                
            }
            eTime = System.currentTimeMillis(); //time stamp at end of algorithm execution
        }

        
        //write image
        try {
            f = new File(output);
            ImageIO.write(newPic,"jpg", f);
            //System.out.println("=====");
        } catch (Exception e) {
            //handle exception
            System.out.println(e);
        }

        System.out.println("Total execution time: "+(eTime- sTime) +"ms");

    }



}