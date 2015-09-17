package puzzlecourse.UI;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import puzzlecourse.logic.GameRound;

/**
 *
 * @author aleksi
 */
public class MouseControl {
    
    private static EventHandler<MouseEvent> mouseClick(final GameRound round, final ImageView[][] images, final int y, final int x){
        EventHandler<MouseEvent> mouseclick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (round.makeMove(y,x) ) {
                    G_Updater.assignImagesToImageViews(round, images);
                }
                
                 System.out.println("Clicked at " + y + ", " + x);
                 t.consume();
            }
        };
        return mouseclick;
    }
    
    public static void assignMouseActions(GameRound round, ImageView[][] images) {
        for (int i=0; i< images.length; i++) {
            for (int j = 0; j < images.length; j++) {
                images[i][j].setOnMouseClicked(mouseClick(round, images, i,j));
            }
            
        }
        
        
    }
    
    
}
