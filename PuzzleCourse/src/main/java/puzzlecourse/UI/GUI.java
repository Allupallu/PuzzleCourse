package puzzlecourse.UI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import puzzlecourse.logic.GameRound;

/**
 * Graafinen käyttöliittymä.
 * @author aleksi
 */
public class GUI extends Application {

    private static StackPane root = new StackPane();
    private static int activeStage;
    
    
    /**
     * Ikkunan tilan muokkaamiseen.
     * Pelitila on 1.
     * Voittotila on 2.
     * Häviötila on 3.
     * @param stage mihin tilaan siirrytään.
     */
    public static void setRoot(int stage) {
        if (activeStage == stage) {
            return;
        } else {
           if (activeStage != 1) {
               root.getChildren().clear();
           }
        }
        switch(stage) {
            case 1: root = getRoot();
                    activeStage = 1;
                    break;
            case 2: root.getChildren().add(ResultPopup.getResultScreen(true));
                    activeStage = 2;
                    break;
            case 3: root.getChildren().add(ResultPopup.getResultScreen(false));
                    activeStage = 3;
                    break;
                    
            
            
        }
    }
    
    
    @Override
    public void start(Stage primaryStage) {

        setRoot(1);
        //root = getRoot();
        
        Scene scene = new Scene(root, 
                                ScalabilityLogic.getWindowWidth(),
                                ScalabilityLogic.getWindowHeight());
        
        setupTimeLine();
        
        primaryStage.setTitle("Puzzle Course");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    
    private static StackPane getRoot() {
        
        GameRound round = setupRound();
        BorderPane gameView = getGameView(round);
        
        
        MouseControl.assignMouseActions(round);
        root.getChildren().addAll(gameView, DialogLayer.getDialogLayerBase());
        return root;
    }
    
    private static void setupTimeLine() {
        Timeline update = new Timeline(new KeyFrame(Duration.millis(30),
                                                    G_Updater.timedUpdate()));
        update.setCycleCount(Timeline.INDEFINITE);
        update.play();
    }
    
    private static GameRound setupRound() {
        GameRound round = new GameRound();
        round.newBoard();
        return round;
    }
    
    private static BorderPane getGameView(GameRound round) {
        BorderPane gameView = new BorderPane();
        
        Group boardGroup = new Group();
        boardGroup.setManaged(false);
       
        int boardSizePixels = ScalabilityLogic.getColorPieceImageSize() * 8;
       
        Rectangle lockBlock = getLock(boardSizePixels);
                
        StackPane container = new StackPane();
        container.setPrefSize(boardSizePixels, boardSizePixels );
        container.getChildren().add(boardGroup);
        container.getChildren().add(lockBlock);
        
        G_Updater.setup(boardGroup, round ,lockBlock);
        
        gameView.setLeft( new PlayerPanel( round.getPlayer(0) ) );
        gameView.setRight( new PlayerPanel( round.getPlayer(1) ) );
        gameView.setCenter(container);
        
        round.introduceOpponent();
        
        return gameView;
    }
    private static Rectangle getLock(int boardSizePixels) {
        Rectangle lock = new Rectangle(); // osittain läpinäkyvä lukitsemaan lauta
                                                // animaatioiden ajaksi
        lock.setManaged(false);
        
        lock.setHeight(boardSizePixels);
        lock.setWidth(boardSizePixels);
        lock.setOpacity(0.4);
        return lock;
    }
    
    /**
     * Graafisen kaikille JavaDoc :D
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }

}
