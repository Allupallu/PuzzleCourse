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
import puzzlecourse.logic.ImageLoader;

/**
 * Kaikkien rakastamille pelinkeskeyttäville dialogeille.
 * @author aleksi
 */
public class DialogLayer {
    
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
        hide();
    }
    private static void makeLayerBase() {
        layerBase = new StackPane();
        layerBase.setPrefSize(ScalabilityLogic.getWindowWidth(),
                              ScalabilityLogic.getWindowHeight());
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
        
        int dialogBoxWidth = ScalabilityLogic.getWindowWidth() / 10 * 6;
        int dialogBoxHeight = ScalabilityLogic.getWindowHeight() / 8 * 2;
        int dialogBoxX = dialogBoxWidth / 6 * 2;
        int dialogBoxY = (int) (dialogBoxHeight * 2.5);
        
        makeDialogPortrait(dialogBoxWidth,dialogBoxHeight,dialogBoxX,dialogBoxY);
        makeDialogBG(dialogBoxWidth,dialogBoxHeight,dialogBoxX,dialogBoxY);
        makeDialogText(dialogBoxWidth,dialogBoxHeight,dialogBoxX,dialogBoxY);
        dialogGroup.getChildren().addAll(dialogTextBackGround,
                                         dialogText,
                                         dialogPortrait);
    }
    private static void makeDialogPortrait(int dialogBoxWidth, int dialogBoxHeight,
                                           int dialogBoxX, int dialogBoxY) {
        dialogPortrait = new ImageView();
        dialogPortrait.setPreserveRatio(true);
        dialogPortrait.setLayoutX(dialogBoxX+dialogBoxWidth-300);
        dialogPortrait.setLayoutY(dialogBoxY-300);
    }
    
    private static void makeDialogBG(int dialogBoxWidth, int dialogBoxHeight,
                                     int dialogBoxX, int dialogBoxY) {
        dialogTextBackGround = new Rectangle(dialogBoxWidth,
                                             dialogBoxHeight,
                                             Color.WHITE);
        dialogTextBackGround.setLayoutX(dialogBoxX);
        dialogTextBackGround.setLayoutY(dialogBoxY);   
    }
    private static void makeDialogText(int dialogBoxWidth, int dialogBoxHeight,
                                       int dialogBoxX, int dialogBoxY) {
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
                System.out.println("It works.");
                if (!dialogQueue.isEmpty() && dialogQueue.peek().dialogClickFinish()) {
                    dialogQueue.poll();
                    hide();
                }
            }
        });
    }
    
    private static void hide() {
        layerBase.toBack();
        layerBase.setVisible(false);
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
        layerBase.toFront();
        layerBase.setVisible(true);
        dialogQueue.addAll(dialogList);
        updatePortrait();
    }
    
    private static void updatePortrait() {
        dialogPortrait.setImage(ImageLoader.loadImage(
                                dialogQueue.peek().getPlayer().getImageID()));
        
    }
    
    /**
     * Päivittää dialogin, jos on päivitettävää.
     * @return onko päivitettävää
     */
    public static boolean timedUpdate() {
        if (dialogQueue.isEmpty()) return false;
        
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
