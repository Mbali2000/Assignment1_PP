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
           /*  //input file
            System.out.println("Enter inout file name: ");
            String input = sc.nextLine();

            //output file name
            System.out.println("Enter output file name: ");
            output = sc.nextLine();*/

            //window size
            System.out.println("Enter Window Size: ");

            int win = sc.nextInt();// size of window

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
           // int width = img.getWidth();
            //int height = img.getHeight();


            
            //surround pixels
            int surpix = (win-1)/2;

             sTime = System.currentTimeMillis(); // time stamp at start of algorithm execution run 
            //image smoothing
            for(int row = surpix ;row < img.getHeight() - surpix; row++){
                for(int col = surpix; col < img.getWidth() - surpix; col++){
                    
                     int red = 0;
                     int gre = 0;
                     int blu = 0;
                     int aph = 0;
                    for(int x= row - surpix; x<= row + surpix; x++){
                        for(int y= col - surpix; y<= col + surpix; y++){
                            //get image pixel at specific coordinate
                            int p = img.getRGB(y,x);
                            //window of 3 by 3 row and column
                            aph +=  (p>>24) & 0xff;
                            red +=  (p>>16) & 0xff;
                            gre +=  (p>>8) & 0xff;
                            blu +=   p & 0xff;
                            
                        
                        }
                       
                    }
                    //curRow = row;222
                    //new pixel+
                    int cnt= win*win;
                    int r = red/cnt;
                    int g = gre/cnt;
                    int b = blu/cnt;
                    int a = aph/cnt;
                    
                    
                    int npix = (a<<24)|(r<<16)|(g<<8)|(b);
                    //System.out.println(npix + " =====");
                    //sets new pixel value with average
                    newPic.setRGB(col, row, npix);  // row and column need to be the width and height if the window size
                }
                
            }
            eTime = System.currentTimeMillis(); //time stamp at end of algorithm execution
        }
            
        
        //write image
        try {
            f = new File("C:\\Users\\Thabani\\Desktop\\Assignment1_PP\\Assignment1_PP\\ImageFilter\\export_img\\output.jpg");
            ImageIO.write(newPic,"jpg", f);
            //System.out.println("=====");
        } catch (Exception e) {
            //handle exception
            System.out.println(e);
        }

        System.out.println("Total execution time: "+(eTime- sTime) +"ms");

    }
    

}



