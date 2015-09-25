/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzlecourse.UI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import puzzlecourse.logic.GameRound;

/**
 * Graafinen käyttöliittymä.
 * @author aleksi
 */
public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        /*
        Tämä metodi on järkyn pitkä väliaikaisesti.
        Tulee hajoamaan moneksi palaksi, kunhan pääsen siihen asti.     
        */
        
        
        StackPane root = new StackPane();

        BorderPane gameView = new BorderPane();
        
        Group boardGroup = new Group();
        boardGroup.setManaged(false);
        Group boardBgGroup = new Group();
        boardGroup.setManaged(false);
        
        Canvas lockCanvas = new Canvas(); // läpinäkyvä lakana lukitsemaan lauta
                                          // animaatioiden ajaksi
        
        int boardSizePixels = ScalabilityLogic.getColorPieceImageSize() * 8;
        int sidePanelSize = (ScalabilityLogic.getWindowWidth()-boardSizePixels) / 2;
        
        lockCanvas.setHeight(boardSizePixels);
        lockCanvas.setWidth(boardSizePixels);
        
        StackPane container = new StackPane();
        container.setPrefSize(boardSizePixels, boardSizePixels );
        container.getChildren().add(boardGroup);
        container.getChildren().add(lockCanvas);
        
        GameRound round = new GameRound();
        round.newBoard();
        
        G_Updater.setup(boardGroup, round ,lockCanvas);
        
        gameView.setLeft( new PlayerPanel( round.getPlayer(0) ) );
        gameView.setRight( new PlayerPanel( round.getPlayer(1) ) );
        gameView.setCenter(container);
        
        
        MouseControl.assignMouseActions(round);
        root.getChildren().add(gameView);
        Scene scene = new Scene(root, 
                                ScalabilityLogic.getWindowWidth(),
                                ScalabilityLogic.getWindowHeight());
        
        Timeline update = new Timeline(new KeyFrame(Duration.millis(30),
                                                    G_Updater.timedUpdate()));
        update.setCycleCount(Timeline.INDEFINITE);
        update.play();

        primaryStage.setTitle("Puzzle Course");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
