package puzzlecourse.UI;

import javafx.stage.Screen;

/**
 * Laskee kokoja erilaisille näytöille ja resoluutioille.
 * @author aleksi
 */
public class ScalabilityLogic {
   
    private static int COLORPIECE_IMAGE_SIZE = 91;
    
    public static int getScreenHeight() {
        return (int)Screen.getPrimary().getVisualBounds().getHeight();
    }
    public static int getScreenWidth() {
        return (int)Screen.getPrimary().getVisualBounds().getWidth();
    }
    
    public static int getColorPieceImageSize() {
        return Math.min( COLORPIECE_IMAGE_SIZE , Math.min(getScreenHeight() / 10, getScreenWidth() / 14));
    }
    
    public static int getWindowWidth() {
        return getColorPieceImageSize() * 14;
    }
    public static int getWindowHeight() {
        return getColorPieceImageSize() * 10;
    }
    
    public static int getSidePanelSize() {
        return getColorPieceImageSize() * 3;
    }
    
    
}
