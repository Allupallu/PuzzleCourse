package puzzlecourse.UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import puzzlecourse.logic.ImageLoader;

/**
 * Erän päätyttyä ilmestyvän lopputuloksen luokka.
 * @author aleksi
 */
public class ResultPopup {
    
    private static final int POPUPWIDTH = Math.min( 600 , ScalabilityLogic.getWindowWidth()/2 );
    private static final int POPUPHEIGHT = ScalabilityLogic.getWindowHeight()/2;
    private static final int POPUP_X = ScalabilityLogic.getWindowWidth()/ 2 - POPUPWIDTH / 2;
    private static final int POPUP_Y = POPUPHEIGHT / 8;
    
    /**
     * Luo ja palauttaa sopivan lopputulosilmoituksen.
     * @param victory voittiko pelaaja 1
     * @return lopputulosilmoitus-Group
     */
    public static Group getResultScreen( boolean victory) {
        Group screen = new Group();
        screen.setManaged(false);
        Rectangle bg = getBg(victory);
        Rectangle popup = getPopup();
        ImageView resultImage = getResultImage(victory);
        Text resultText = getResultText(victory);
        Button retryBtn = getRetryButton();
        screen.getChildren().addAll(bg, popup, resultImage, resultText, retryBtn);
        return screen;
    }
    
    private static Rectangle getBg(boolean victory) {
        Color bgColor = victory ? Color.GREEN : Color.RED;
        Rectangle bg = new Rectangle( ScalabilityLogic.getWindowWidth(),
                                      ScalabilityLogic.getWindowHeight(),
                                      bgColor);
        bg.setWidth(ScalabilityLogic.getWindowWidth());
        bg.setHeight(ScalabilityLogic.getWindowHeight());
        bg.setOpacity(0.9);
        bg.setManaged(false);
        return bg;
    }
    private static Rectangle getPopup() {
        Rectangle popup = new Rectangle ( POPUPWIDTH,
                               POPUPHEIGHT,
                               Color.LIGHTGREY);
        popup.setLayoutX(POPUP_X);
        popup.setLayoutY(POPUP_Y);
        return popup;
    }
    private static ImageView getResultImage(boolean victory) {
        ImageView resultImage = new ImageView();
        resultImage.setImage( ImageLoader.loadImage( victory ? "victory" : "defeat" ) );
        int padding = 10;
        resultImage.setFitWidth(POPUPWIDTH-2*padding);
        
        resultImage.setLayoutX(POPUP_X+padding);
        resultImage.setLayoutY(POPUP_Y+padding);
        
        return resultImage;
    }
    private static Text getResultText(boolean victory) {
        Text text = new Text();
        
        int padding = 20;
        
        text.setLayoutX(POPUP_X + padding);
        text.setLayoutY(POPUP_Y + POPUPHEIGHT - 100);
        text.setWrappingWidth(POPUPWIDTH - 2*padding);
        
        text.setText( victory ? "Onnittelut! Keräsit tarpeeksi innovaatiota valmistuaksesi."
                              : "Professorisi keräsi eläkerahastonsa ja lähti omille teilleen. Valmistumisesi jää haaveeksi." );
        
        return text;
    }
    private static Button getRetryButton() {
        Button btn = new Button();
        btn.setText("Uudestaan!");
        btn.setLayoutX(POPUP_X + POPUPWIDTH*2/3);
        btn.setLayoutY(POPUP_Y + POPUPHEIGHT - 30);
        setBtnAction(btn);
        return btn;
    }
    private static void setBtnAction(Button b) {
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                GUI.setRoot(1);
            }
        });
    }
    
    
}
