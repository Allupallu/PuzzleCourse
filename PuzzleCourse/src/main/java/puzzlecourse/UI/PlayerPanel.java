/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzlecourse.UI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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
    private final ImageView portrait;
    
    private final int[] collectedPieces;
    private Text[] collectedText;
    
    // Hajoaa useaksi metodiksi, kunhan olen vähemmän väsynyt.
    public PlayerPanel(Player player) {
        this.player = player;
        portrait = new ImageView();
        portrait.setFitWidth(ScalabilityLogic.getSidePanelSize());
        portrait.setImage(ImageLoader.loadImage(player.getImageID()));
        collectedPieces = new int[6];
        makeCollectedText();
        
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
            
            getChildren().addAll(portrait, playerName,
                                 skill1Reqs, playerSkill1,
                                 skill2Reqs, playerSkill2);
        } else {
            this.setAlignment(Pos.TOP_RIGHT);
            getChildren().addAll(portrait, playerName);
        }
        getChildren().addAll(collectedText);
    }
    
    private void makeCollectedText() {
        collectedText = new Text[collectedPieces.length];
        for (int i = 0 ; i < collectedPieces.length; i++) {
            collectedText[i] = new Text(0,0, ""+collectedPieces[i]);
        }
    }
    
    public void addXPiecesOfTypeY(int x, int y) {
        collectedPieces[y] += x;
    }
    
    public void updatePanel() {
        
    }
    
}
