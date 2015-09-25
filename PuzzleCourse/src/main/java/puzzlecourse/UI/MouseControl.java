package puzzlecourse.UI;


import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import puzzlecourse.logic.GameRound;

/**
 * Hoitaa hiiriohjaukseen liittyviä asioita.
 * @author aleksi
 */
public class MouseControl {
    
    private static EventHandler<MouseEvent> mouseClick(final GameRound round, final int y, final int x){
        EventHandler<MouseEvent> mouseclick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (round.makeMove(y,x) ) {
                    G_Updater.setMoveActive(true);
                    System.out.println("Clicked at " + y + ", " + x); 
                }
                
                 t.consume();
            }
        };
        return mouseclick;
    }
    
    /**
     * Liittää piirtolaudan kuvat pelilogiikan laudan liikkeisiin.
     * @param round pelilogiikan erä
     */
    public static void assignMouseActions(GameRound round) {
        ImageView[][] images = G_Updater.getImages();
        for (int i=0; i< images.length; i++) {
            for (int j = 0; j < images.length; j++) {
                images[i][j].setOnMouseClicked(mouseClick(round, i,j));
            }
            
        }
        
        
    }
    
    
}
