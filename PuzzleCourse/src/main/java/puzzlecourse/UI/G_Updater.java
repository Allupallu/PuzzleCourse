/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzlecourse.UI;

import java.awt.Rectangle;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import puzzlecourse.logic.GameRound;
import puzzlecourse.logic.ImageLoader;

/**
 *
 * @author aleksi
 */
public class G_Updater {
    
    
    
    public static ImageView[][] makeImageViewTable(GameRound round) {
        ImageView[][] images = new ImageView[round.getBoardSize()][round.getBoardSize()];
        
        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < images.length; j++) {
                images[i][j] = new ImageView();
            }
        }
        
        return images;
    }
    public static void assignImagesToImageViews(GameRound round, ImageView[][] images) {
        
        if (ImageLoader.loadImages()) {
            for (int i = 0; i < round.getBoardSize(); i++) {
                for (int j = 0; j < round.getBoardSize(); j++) {
                int type = round.getTypeAt(i, j);
                images[i][j].setImage(ImageLoader.getImage(type));
                }
            }
            
            
            
        }
        
        
    }

    static void addImageViewsToNote(ImageView[][] images, GridPane testBox) {
        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < images.length; j++) {
                testBox.add(images[i][j], j , i);
            }
        }
    }
    
}
