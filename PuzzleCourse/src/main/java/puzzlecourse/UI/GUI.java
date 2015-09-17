/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzlecourse.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import puzzlecourse.logic.GameRound;

/**
 *
 * @author aleksi
 */
public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        GridPane testBox = new GridPane();

        
        GameRound round = new GameRound();
        round.newBoard();
        
        ImageView[][] images = G_Updater.makeImageViewTable(round);
        G_Updater.assignImagesToImageViews(round, images);
        G_Updater.addImageViewsToNote(images, testBox);
        
        VBox left = new VBox();
        left.setMinWidth(120);
        root.setLeft(left);
        
        
        MouseControl.assignMouseActions(round, images);
        root.setCenter(testBox);
        Scene scene = new Scene(root, 1000, 800);

        primaryStage.setTitle("Puzzle Course");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
