 package puzzlecourse.logic;


import java.io.InputStream;
import javafx.scene.image.Image;

/**
 * Hoitaa kuvatiedostojen lataamisen.
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
     * Lataa laudan palasten kuvat.
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
                InputStream in = ImageLoader.class.getResourceAsStream( IMAGE_PATH + IMAGE_NAMES[i]);
                images[i] = new Image(in); 
                
            } catch (Exception e) {
                System.out.println("Ongelmia kuvan kanssa!" + i);
                loaded = false;
            }
        }

        return loaded;
    }
    
    /**
     * Lataa tietyn kuvan.
     * @param imageID kuvatiedoston nimi sans .png
     * @return palauttaa kuvan
     */
    public static Image loadImage(String imageID) {
        try {
                InputStream in = ImageLoader.class.getResourceAsStream( IMAGE_PATH + imageID + ".png");
                return new Image(in); 
               } catch (Exception e) {
                System.out.println("Ongelmia kuvan kanssa! " + imageID);
                return null;
            }
    }

    /**
     * Hakee tietyn tyyppisen palan kuvan
     * @param type tyyppi
     * @return palan kuva
     */
    public static Image getImage(int type) {
        if (!loaded || type < 0 || !(type < images.length)) {
            return null;
        }
        return images[type];
    }
    
    

}
