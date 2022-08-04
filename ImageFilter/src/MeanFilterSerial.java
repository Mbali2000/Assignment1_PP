public class MeanFilterSerial extends javax.swing.jpanel{
    BufferedImage img;
    File f;
    int[][][] rgb;

    PUBLIC MeanFilterSerial(){
        img = null;
        f = null;
    }

    @override
    public paint(Graphic g){
        super.paint(g);
        if(img !=null){
            g.drawImage(img, 0, 0, null);
        }
    }

    //get image pixels
    public void getPixels(){
        for(int row = 0; row<img.getHeight(); row++){
            for(int col =0; col<img.getHeight(); col++){
                Color c = new Color(img.getRGB(col, row));//returns rgb value of each pixel in image
                rgbB[0][row][col] = c.getRed();
                rgbB[1][row][col] = c.getGreen();
                rgbB[2][row][col] = c.getBlue();
            }
        }

    }

    public static void main(String[]args) throws IOException{
        //read in image
        try {
            f = new File("pic.jpg");
            img = ImageIO.read(f);// assigning file to image variable
            rgbB = newint[3][img.getHeight()][img.getWidth()];
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e);
        } 

        //image smoothing
        getPixels(); //loads image pixels
        for(int row = 1; row< img.getHeight()-1; row++){
            for(int col = 1; col< img.getWidth() - 1; col++){

                int mean =
            }
        }

    }


}