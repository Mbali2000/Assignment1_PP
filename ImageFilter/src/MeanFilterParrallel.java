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
    static int surPix;
    static int win;
    static int sq;
    
    

    //arguments
    int sHeight;
    int eHeight;
    int sWidth;
    int eWidth;
    int hi;
    
    static int seqCut = 100;

    public MeanFilterParrallel(int sH, int eH, int sW, int eW){

        sHeight = sH;
        eHeight = eH;
        sWidth = sW;
        eWidth = eW; 

    }

    //mean calculation method
    public void meanCompute(){
        //nt sideP = (img.getWidth() - 1)/2;
        for(int row = surPix; row < img.getHeight() - surPix; row++){
            for( int col = surPix; col < eWidth; col++){

                int  red = 0, gre = 0, blu = 0, aph = 0;

                for(int x = row; x <= surPix; x++){
                    for(int y = col; y <= surPix; y++){

                        //get pixel at specific position
                        int p = img.getRGB(x, y);

                

                        //get rgb values at specific pixel
                        red += (p>>16) & 0xff;
                        gre += (p>>8) & 0xff;
                        blu += (p) & 0xff;
                        aph += (p>>24) & 0xff;

                    }
                }
                int sq = win*win;
                int r = red/sq;
                int g = gre/sq;
                int b = blu/sq;
                int a = aph/sq;
                
                int newPix = (0xff000000)|((r) >> 16) | ((g) >> 8) | ((b) >> 0) | ((a) >> 24);
    
                newPic.setRGB(col, row, newPix);  




            }
            
             
        }



    }

    //crating pool of threads
    //static final ForkJoinPool fjPool = new ForkJoinPool();

    //instantiates mean filtering algorithm
    @Override
    protected void compute() {
        if((eWidth - sWidth) < seqCut){
            
            meanCompute();
            return;  
            }
        {
            
            MeanFilterParrallel left = new MeanFilterParrallel(sHeight, eHeight, sWidth, (sWidth+eWidth)/2 );
            MeanFilterParrallel right = new MeanFilterParrallel(sHeight, eHeight, (sWidth+eWidth)/2, eWidth);
            invokeAll(left, right);
            
        }
        
    }

    public static void main(String[] args) throws IOException {
        try (//input prompts
        Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter window size: ");
            win = sc.nextInt();
        }

        /*System.out.println("Enter input file name: ");
        String input = sc.nextLine();

        System.out.println("Enter output file name: ");
        String output = sc.nextLine();*/

        surPix = (win-1)/2;

        //image read in
        try {
            f = new File("C:\\Users\\Thabani\\Desktop\\Assignment1_PP\\Assignment1_PP\\ImageFilter\\import_img\\image2.jpg");
            img = ImageIO.read(f);// assigning file to image variable
            newPic = ImageIO.read(f);
        } catch (Exception e) {
            //handle exception
            System.out.println(e);
        } 

        ForkJoinPool fj = new ForkJoinPool();
        MeanFilterParrallel mp = new MeanFilterParrallel(win, img.getHeight() - win, win, img.getWidth());
        fj.invoke(mp);


        //image export
        try {
            f = new File("C:\\Users\\Thabani\\Desktop\\Assignment1_PP\\Assignment1_PP\\ImageFilter\\export_img\\output.jpg");
            ImageIO.write(newPic,"jpg", f);
            //System.out.println("=====");
        } catch (Exception e) {
            //handle exception
            System.out.println(e);
        }

    }

    
}
