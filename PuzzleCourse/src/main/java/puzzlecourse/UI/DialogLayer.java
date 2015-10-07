package puzzlecourse.UI;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import puzzlecourse.containers.Dialog;
import puzzlecourse.containers.Player;
import puzzlecourse.logic.ImageLoader;

/**
 * Kaikkien rakastamille pelinkeskeyttäville dialogeille.
 * @author aleksi
 */
public class DialogLayer {
    
    
    private static final int dialogBoxWidth = ScalabilityLogic.getWindowWidth() / 10 * 6;
    private static final int dialogBoxHeight = ScalabilityLogic.getWindowHeight() / 8 * 2;
    private static final int dialogBoxX = dialogBoxWidth / 6 * 2;
    private static final int dialogBoxY = (int) (dialogBoxHeight * 2.5);
        
    
    private static StackPane layerBase;
    
    private static Rectangle gameLock;
    
    private static Group dialogGroup;
    private static Node dialogTextBackGround;
    private static Text dialogText;
    private static ImageView dialogPortrait;
    
    private static boolean isSetUp;
    private static Queue<Dialog> dialogQueue;
    
    
    
    private static void setup() {
        
        makeLayerBase();
        setupMouseAction();
        makeGameLock();        
        makeDialogGroup();

        layerBase.getChildren().addAll(gameLock, dialogGroup);
        
        dialogQueue = new LinkedList<>();
        
        isSetUp = true;
    }
    private static void makeLayerBase() {
        layerBase = new StackPane();
        layerBase.setPrefSize(ScalabilityLogic.getWindowWidth(),
                              ScalabilityLogic.getWindowHeight()); 
        hide();
    }
    private static void makeGameLock() {
        gameLock = new Rectangle(ScalabilityLogic.getWindowWidth(),
                                 ScalabilityLogic.getWindowHeight(),
                                 Color.BLACK);
        gameLock.setOpacity(.95);    
    }
     
    private static void makeDialogGroup() {
        dialogGroup = new Group();
        dialogGroup.setManaged(false);
        
        makeDialogPortrait();
        makeDialogBG();
        makeDialogText();
        dialogGroup.getChildren().addAll(dialogTextBackGround,
                                         dialogText,
                                         dialogPortrait);
    }
    private static void makeDialogPortrait() {
        dialogPortrait = new ImageView();
        dialogPortrait.setPreserveRatio(true);
        dialogPortrait.setLayoutY(dialogBoxY-300);
    }
    
    private static void makeDialogBG() {
        dialogTextBackGround = new Rectangle(dialogBoxWidth,
                                             dialogBoxHeight,
                                             Color.WHITE);
        dialogTextBackGround.setLayoutX(dialogBoxX);
        dialogTextBackGround.setLayoutY(dialogBoxY);   
    }
    private static void makeDialogText() {
        dialogText = new Text();
        int fontSize = Math.min(dialogBoxHeight/4 , 30);
        dialogText.setFont(new Font(fontSize));
        dialogText.setManaged(false);
        dialogText.setLayoutX(dialogBoxX + 20);
        dialogText.setLayoutY(dialogBoxY + fontSize*1.5);
        dialogText.setWrappingWidth(dialogBoxWidth - 40);
        
    }
    
    private static void setupMouseAction() {
        layerBase.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (!dialogQueue.isEmpty() && dialogQueue.peek().dialogClickFinish()) {
                    dialogQueue.poll();
                    if (dialogQueue.isEmpty()) hide();
                    else updatePortrait();
                }
            }
        });
    }
    
    private static void hide() {
        layerBase.toBack();
        layerBase.setVisible(false);
    }
    private static void show() {
        layerBase.toFront();
        layerBase.setVisible(true);
        updatePortrait();
    }
    
    public static StackPane getDialogLayerBase() {
        if (!isSetUp) setup();
        return layerBase;
    }
    
    /**
     * Lisää dialogin ja tuo sen päällimmäiseksi.
     * @param dialogList lisättävät dialogit
     */
    public static void addDialog(List<Dialog> dialogList) {
        if (!isSetUp) setup();
        dialogQueue.addAll(dialogList);
    }
    
    private static void updatePortrait() {
        Player player = dialogQueue.peek().getPlayer();
        dialogPortrait.setImage(ImageLoader.loadImage(
                                player.getImageID()));
        int xOffset;
        if (player.isHuman()) xOffset = dialogBoxWidth;
        else xOffset = 300;
        dialogPortrait.setLayoutX(dialogBoxX+dialogBoxWidth-xOffset);
        
    }
    
    /**
     * Päivittää dialogin, jos on päivitettävää.
     * @return onko päivitettävää
     */
    public static boolean timedUpdate() {
        if (dialogQueue.isEmpty()) return false;
        
        if (!layerBase.isVisible()) {
            show();
        }
        
        dialogText.setText( dialogQueue.peek().getText() );
        
        checkForTimedDialogEnd();
        
        return true;
    }
    
    private static void checkForTimedDialogEnd() {
        if (dialogQueue.peek().dialogTimedFinish()) {
            dialogQueue.poll();
            if (dialogQueue.isEmpty()) hide();
            else updatePortrait();
        }
    }
    
}
