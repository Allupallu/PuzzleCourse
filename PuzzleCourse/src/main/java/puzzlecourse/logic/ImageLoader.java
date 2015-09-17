package puzzlecourse.logic;


import java.io.InputStream;
import javafx.scene.image.Image;

/**
 *
 * @author aleksi
 */
public class ImageLoader {

    private static final String IMAGE_PATH = "/images/";
    private static final String[] IMAGE_NAMES = {"red.png",
                                                 "blue.png",
                                                 "green.png",
                                                 "brown.png",
                                                 "yellow.png",
                                                 "gray.png"};
    
    private static final int NUMBER_OF_IMAGES = IMAGE_NAMES.length;

    private static boolean loaded;
    private static Image[] images;

    /**
     *
     * @return Onnistuiko lataaminen
     */
    public static boolean loadImages() {
        if (loaded) {
            return true;
        }
        images = new Image[NUMBER_OF_IMAGES];
        
        loaded = true;

        for (int i = 0; i < NUMBER_OF_IMAGES; i++) {

            try {
               //System.out.println(ImageLoader.class.getResource("puzzlecourse/images/blue.png").toExternalForm());
        
                InputStream in = ImageLoader.class.getResourceAsStream( IMAGE_PATH + IMAGE_NAMES[i]);
                images[i] = new Image(in); 
                
               //System.out.println(ImageLoader.class.get);
               //images[i] = new Image(ImageLoader.class.getResource(IMAGE_PATH + IMAGE_NAMES[i]).toExternalForm()); 
               //System.out.println(ImageLoader.class.getResource("blue.png").getPath());
               //images[i] = new Image( IMAGE_PATH + IMAGE_NAMES[i] );
            } catch (Exception e) {
                System.out.println("Ongelmia kuvan kanssa!" + i);
                loaded = false;
            }
        }

        return loaded;
    }

    public static Image getImage(int a) {
        if (!loaded || a < 0 || !(a < images.length)) {
            System.out.println("Blammo.");
            return null;
        }
        return images[a];
    }
    
    

}
