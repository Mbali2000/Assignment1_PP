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
    public void readPixels(){

    }

    public static void main(String[]args) throws IOException{
        //read in image
        try {
            f = new File("pic.jpg");
            img = ImageIO.read(f);
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e);
        }  

    }


}