public class GetPixels{
    BufferedImage img;
    File f;

    public get GetPixels(){
        img = null;
        f = null;
    }

    @override
    public paint()

    public static void main(String[] args) throws IOException {
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