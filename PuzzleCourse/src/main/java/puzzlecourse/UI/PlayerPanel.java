/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzlecourse.UI;

import java.util.LinkedList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    
    private static final List<PlayerPanel> panels = new LinkedList<>();
    
    private final Player player;
    private final BorderPane portraitPane;
    private ImageView portrait;
    private Node activeTurnIndicator;
    
    private final int[] collectedPieces;
    private Text[] collectedText;
    private boolean updateRequired;
    
    // Hajoaa useaksi metodiksi, kunhan olen vähemmän väsynyt.
    public PlayerPanel(Player player) {
        panels.add(this);
        this.player = player;
        
        collectedPieces = new int[6];
        makeCollectedText();
        portraitPane = makePortraitPane();
        
        Text playerName = new Text(0, 0, player.getName());
        playerName.setFont(new Font(20));
        
        
        getChildren().addAll(portraitPane, playerName);
        
        getChildren().addAll(makeResources());
        
        makeAbilityButton(0);
        makeAbilityButton(1);
        
    }
    private void makeAbilityButton(int i) {
        if (player.isHuman()) {
            Button playerSkill = new Button();
            HBox skillReqs = makeRequirementsVisual(i);
            playerSkill.setText(player.getAbility(i).getAbilityName());
            playerSkill.setMinWidth(ScalabilityLogic.getSidePanelSize()/4*3);
            assignActionToAbilityButton(playerSkill, i);
            getChildren().addAll(playerSkill, skillReqs);
        }
    }
    private void assignActionToAbilityButton(Button b, final int i) {
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                G_Updater.useAbility(i);
            }
        });
    }
    
    private HBox makeRequirementsVisual(int i) {
        HBox requirements = new HBox();
        for (int j = 0 ; j < 6; j++) {
            ImageView resourceIcon = new ImageView(ImageLoader.getImage(j));
            resourceIcon.setPreserveRatio(true);
            resourceIcon.setFitWidth(ScalabilityLogic.getColorPieceImageSize()/6);
            Text reqText = new Text(0, 0, ""+player.getAbility(i).getRequirement(j));
            requirements.getChildren().addAll(resourceIcon, reqText);
        }
        return requirements;
    }

    
    private BorderPane makePortraitPane() {
        int portraitSize = ScalabilityLogic.getSidePanelSize();
        int pictureSize = portraitSize-20;
        
        makeActiveTurnIndicator(pictureSize);
        
        makePortraitImageView(pictureSize);
        
        StackPane framed = new StackPane();
        framed.getChildren().addAll(activeTurnIndicator, portrait);
        
        BorderPane ret = new BorderPane();
        ret.setCenter(framed);
        
        return ret;
    }
    private void makeActiveTurnIndicator(int pictureSize) {
        activeTurnIndicator = new Circle(pictureSize/2, Color.YELLOW);
        activeTurnIndicator.setVisible(player.isHuman());
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
            collectedText[i].setFont(new Font(ScalabilityLogic.getColorPieceImageSize()/4));
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
        resourceIcon.setFitWidth(ScalabilityLogic.getColorPieceImageSize()/3);
        resourceBox.getChildren().addAll(resourceIcon, collectedText[type]);
        return resourceBox;
    }
    
    
    private boolean updateResourceType(int type) {
        if (collectedPieces[type] < player.getCollected(type)) {
            collectedPieces[type]++;
            return true;
        } else if (collectedPieces[type] > player.getCollected(type)) {
            collectedPieces[type]--;
            return true; }
        else {
            return false;
        }
    }
    private void updateResourceText(int type) {
        collectedText[type].setText(""+collectedPieces[type]);
    }
    
    private void updatePanel() {
        updateRequired = true;
        for (int i = 0 ; i < collectedPieces.length; i++) {
            if (updateResourceType(i)) {
                updateResourceText(i);
                updateRequired = false;
            }
        }
    }
    
    /**
     * Päivittää paneelin graafiset.
     */
    public static void updatePanels() {
        for (PlayerPanel p : panels) {
            p.updatePanel();
        }
    }
    
}
