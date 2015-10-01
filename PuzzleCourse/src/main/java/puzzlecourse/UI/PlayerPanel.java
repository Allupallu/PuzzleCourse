/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzlecourse.UI;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import puzzlecourse.containers.Player;
import puzzlecourse.logic.ImageLoader;

/**
 * Luo ja ylläpitää pelaajien sivupaneelit.
 * @author aleksi
 */
public class PlayerPanel extends VBox {
    
    private final Player player;
    private final BorderPane portraitPane;
    private ImageView portrait;
    private Node activeTurnIndicator;
    
    private final int[] collectedPieces;
    private Text[] collectedText;
    
    // Hajoaa useaksi metodiksi, kunhan olen vähemmän väsynyt.
    public PlayerPanel(Player player) {
        this.player = player;
        collectedPieces = new int[6];
        makeCollectedText();
        portraitPane = makePortraitPane();
        
        Text playerName = new Text(0, 0, player.getName());
        playerName.setFont(new Font(20));
        
        if (player.isHuman()) {
            Button playerSkill1 = new Button();
            Text skill1Reqs = new Text(0,0,"Requires: ----");
            playerSkill1.setText("Weep loudly");
            playerSkill1.setMinWidth(ScalabilityLogic.getSidePanelSize()/2);
            
            
            Text skill2Reqs = new Text(0,0,"Requires: ----");
            Button playerSkill2 = new Button();
            playerSkill2.setText("Do squats.");
            playerSkill2.setMinWidth(ScalabilityLogic.getSidePanelSize()/2);
            
            getChildren().addAll(portraitPane, playerName,
                                 skill1Reqs, playerSkill1,
                                 skill2Reqs, playerSkill2);
        } else {
            this.setAlignment(Pos.TOP_RIGHT);
            getChildren().addAll(portraitPane, playerName);
        }
        getChildren().addAll(makeResources());
    }
    
    private BorderPane makePortraitPane() {
        int portraitSize = ScalabilityLogic.getSidePanelSize();
        int pictureSize = portraitSize-20;
        activeTurnIndicator = new Circle(pictureSize/2, Color.YELLOW);
        makePortraitImageView(pictureSize);
        
        StackPane framed = new StackPane();
        framed.getChildren().addAll(activeTurnIndicator, portrait);
        
        BorderPane ret = new BorderPane();
        ret.setCenter(framed);
        
        return ret;
    }
    private void makePortraitImageView(int size) {
        portrait = new ImageView();
        portrait.setFitWidth(size);
        portrait.setImage(ImageLoader.loadImage(player.getImageID()));
    }
    
    private void makeCollectedText() {
        collectedText = new Text[collectedPieces.length];
        for (int i = 0 ; i < collectedPieces.length; i++) {
            collectedText[i] = new Text(0,0, ""+collectedPieces[i]);
        }
    }
    
    private HBox[] makeResources() {
        HBox[] resources = new HBox[collectedText.length];
        for (int i = 0 ; i < collectedText.length; i++) {
            resources[i] = makeResourceHBox(i);
        }
        return resources;
    }
    
    private HBox makeResourceHBox(int type) {
        HBox resourceBox = new HBox();
        ImageView resourceIcon = new ImageView(ImageLoader.getImage(type));
        resourceIcon.setPreserveRatio(true);
        resourceIcon.setFitWidth(14);
        resourceBox.getChildren().addAll(resourceIcon, collectedText[type]);
        return resourceBox;
    }
    
    
    /**
     * Tää ei oo vielä toimiva systeemi hei yritä ees jou
     * @param currentPlayer 
     */
    public void currentPlayerIndicatorUpdater(Player currentPlayer) {
        activeTurnIndicator.setVisible(this.player == player);  
    }
    
    public void addXPiecesOfTypeY(int x, int y) {
        collectedPieces[y] += x;
    }
    
    public void updatePanel() {
        
    }
    
}
