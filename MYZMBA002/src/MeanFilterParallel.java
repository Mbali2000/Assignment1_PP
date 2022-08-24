import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import javax.imageio.ImageIO;


public class MeanFilterParallel extends RecursiveAction{

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
    
    //time stamps
    static long sTime;
    static long eTime;
    static String output;

    //sequential cutoff
    static int seqCut = 100;

    public MeanFilterParallel(int sH, int eH, int sW, int eW){

        sHeight = sH;
        eHeight = eH;
        sWidth = sW;
        eWidth = eW; 

    }

    //mean calculation method
    public void meanCompute(){
        //nt sideP = (img.getWidth() - 1)/2;
        for(int row = surPix; row < img.getHeight() - surPix; row++){
            for( int col = surPix; col < img.getWidth() - surPix; col++){

                int  red = 0, gre = 0, blu = 0, aph = 0;

                for(int x = row - surPix; x <= row +surPix; x++){
                    for(int y = col - surPix; y <= col+surPix; y++){

                        //get pixel at specific position
                        int p = img.getRGB(y, x);

                

                        //get rgb values at specific pixel
                        red += (p>>16) & 0xff;
                        gre += (p>>8) & 0xff;
                        blu += (p) & 0xff;
                        aph += (p>>24) & 0xff;

                    }
                }
                sq = win*win;
                int r = red/sq;
                int g = gre/sq;
                int b = blu/sq;
                int a = aph/sq;
                //System.out.println("twofortw0");
                int newPix = (a<< 24) |(r<< 16) | (g<< 8) | (b) ;
      
    
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
        else{   

           
            MeanFilterParallel left = new MeanFilterParallel(sHeight, eHeight, sWidth, (sWidth+eWidth)/2 );
            MeanFilterParallel right = new MeanFilterParallel(sHeight, eHeight, (sWidth+eWidth)/2, eWidth);
            invokeAll(left, right);
            
        }
        
    }

    public static void main(String[] args) throws IOException {
        try (//input prompts
        Scanner sc = new Scanner(System.in)) {

        //System.out.println("Enter input file name: ");
        String input = args[0];

        //System.out.println("Enter output file name: ");
        String output = args[1];

        //System.out.println("Enter window size: ");
        String num = args[2];
        win = Integer.parseInt(num);
        

        

        surPix = (win-1)/2;

        //image read in
        try {
            f = new File("import_img/"+ input);
            img = ImageIO.read(f);// assigning file to image variable
            newPic = ImageIO.read(f);
        } catch (Exception e) {
            //handle exception
            System.out.println(e);
        } 
    

        

        ForkJoinPool fj = new ForkJoinPool();
        MeanFilterParallel mp = new MeanFilterParallel(0, img.getHeight(), 0, img.getWidth());
        // time stamp at start of algorithm execution run
        sTime = System.currentTimeMillis(); 
        fj.invoke(mp);

        //time stamp at end of algorithm execution taking at a time when all threads are finished with their execution
        eTime = System.currentTimeMillis(); 


        //image export
        try {
            f = new File("export_img/"+ output);
            ImageIO.write(newPic,"jpg", f);
            //System.out.println("=====");
        } catch (Exception e) {
            //handle exception
            System.out.println(e);
        }
    

        //duration calculation
        System.out.println("Total execution time: "+(eTime- sTime) +"ms");

        }
    }

    
}
