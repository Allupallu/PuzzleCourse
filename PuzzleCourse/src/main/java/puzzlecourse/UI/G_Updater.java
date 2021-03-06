package puzzlecourse.UI;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import puzzlecourse.containers.Coordinate;
import puzzlecourse.containers.Waypoint;
import puzzlecourse.logic.GameRound;
import puzzlecourse.logic.ImageLoader;

/**
 * Hoitaa graafisen käyttöliittymän ajantasallapitämiseen liittyviä 
 * toimenpiteitä.
 * @author aleksi
 */
public class G_Updater {
    
    private static Group boardGroup;
    private static Rectangle lockCanvas, selectedPiece;
    private static ImageView[][] images;
    private static GameRound round;
    
    private static boolean dialogActive;
    private static boolean moveActive;
    
    private static boolean roundEnded, endedInVictory;
   
    
    
    /**
     * Laittaa luokan käyttövalmiiksi.
     * @param group alusta, jolle lauta piirretään
     * @param gameround piirrettävä pelierä
     * @param lockCanvaspara lukitsemiseen käytettävä lakana
     */
    public static void setup(Group group, GameRound gameround, Rectangle lockCanvaspara) {
        boardGroup = group;
        round = gameround;
        lockCanvas = lockCanvaspara;
        roundEnded = false;
        
        BoardDrawCoordinates.scaleColorPieceSize();  
        makeSelectedPieceAndAddIt();
        makeImageViewTable();
        assignImagesToImageViews();
        addImageViewsToGroup();
        
    }
    
    public static void endRound(boolean victory) {
        roundEnded = true;
        endedInVictory = victory;
    }
    
    private static void makeSelectedPieceAndAddIt() {
        selectedPiece = new Rectangle(ScalabilityLogic.getColorPieceImageSize(),
                                      ScalabilityLogic.getColorPieceImageSize(),
                                      Color.PINK);
        boardGroup.getChildren().add(selectedPiece);
        selectedPiece.setVisible(false);
    }
    
    public static void tryPlacingSelectedPieceAt(int y, int x) {
        if (selectedPiece.isVisible()) {
            selectedPieceVisibleUpdater(y,x);
        } else {
            placeSelectedPiece(y,x);
        }
    }
    private static void selectedPieceVisibleUpdater(int y, int x) {
        if (   BoardDrawCoordinates.hasWaypoints() 
            || selectedPieceIsAt(y, x)) {
            selectedPiece.setVisible(false);
        }
    }
    private static boolean selectedPieceIsAt(int y, int x) {
        return BoardDrawCoordinates.fromDrawIntToBoardReference((int)selectedPiece.getLayoutY()) == y
                && BoardDrawCoordinates.fromDrawIntToBoardReference((int)selectedPiece.getLayoutX()) == x;
    }
    private static void placeSelectedPiece(int y, int x) {
        selectedPiece.setLayoutY(BoardDrawCoordinates.fromBoardReferenceToDrawInt(y));
        selectedPiece.setLayoutX(BoardDrawCoordinates.fromBoardReferenceToDrawInt(x));
        selectedPiece.setVisible(true);
    }
    
    
    private static void makeImageViewTable() {
        images = new ImageView[round.getBoardSize()][round.getBoardSize()];
        
        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < images.length; j++) {
                images[i][j] = new ImageView();
                images[i][j].setFitWidth(ScalabilityLogic.getColorPieceImageSize());
                images[i][j].setPreserveRatio(true);
            }
        }
    }
    
    /**
     * Setteri sille, onko animaatiot kesken.
     * @param move onko animaatio kesken
     */
    public static void setMoveActive(boolean move) {
        moveActive = move;
    }
    
    /**
     * Palauttaa väliajoin toistettavat toimenpiteet.
     * @return toistettavat toimenpiteet
     */
    public static EventHandler<ActionEvent> timedUpdate() {
        EventHandler<ActionEvent> update = new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent t) {
              if ( BoardDrawCoordinates.hasWaypoints() ) {//moveActive) {
                  lockCanvas.toFront();
                  moveActive = BoardDrawCoordinates.updateDrawCoords();
                  PlayerPanel.updatePanels();
              } else
              if (DialogLayer.timedUpdate()) {
                  
              } else 
              if ( roundEnded ){
                  GUI.setRoot( endedInVictory ? 2 : 3);
              } else
              if (!round.isHumanTurn()) {
                  lockCanvas.toFront();
                  round.makeMove(0, 0);
              } else {
                  lockCanvas.toBack();
              }
              
              
              
                  
          }
        };
        return update;
    }
    
    /**
     * Vaihtaa laudalta haetun palaa vastaavan kuvan piirtokoordinaatit.
     * @param refY laudan y-koordinaatti
     * @param refX laudan x-koordinaatti
     * @param locY kuvan y-piirtokoordinaatti
     * @param locX kuvan x-piirtokoordinaatti
     */
    public static void setImageViewLocation(int refY, int refX, int locY, int locX) {
        images[refY][refX].setLayoutX(locX);
        images[refY][refX].setLayoutY(locY);
    }
    
    public static ImageView[][] getImages() {
        return images;
    }
    
    
    /**
     * Päivittää kuvalaudan kuvat vastaamaan pelierän data-laudan sisältämiä.
     */
    public static void assignImagesToImageViews() {
        
        if (ImageLoader.loadImages()) {
            for (int i = 0; i < round.getBoardSize(); i++) {
                for (int j = 0; j < round.getBoardSize(); j++) {
                int type = round.getTypeAt(i, j);
                images[i][j].setImage(ImageLoader.getImage(type));
                }
            }
        }
    }
    
    
    /**
     * Päivittää muutettujen palasten kuvat.
     * @param list muutettujen palasten koordinaattien lista
     * @param type tyyppi, joksi muutetaan
     */
    public static void assignImagesToReplaced(List<Coordinate> list, int type) {
        for (Coordinate c : list) {
            images[c.getY()][c.getX()].setImage(ImageLoader.getImage(type));
        }
    }
    
    
    /**
     * Päivittää kuvalaudan kuvat vastaamaan animaatioita.
     * @param list lista tämän hetken liikkuvista kuvista waypointteina
     */
    public static void assignImagesToImageViews(List<Waypoint> list) {
        for (Waypoint w : list) {
            int y = w.getBoardRef().getY();
            int x = w.getBoardRef().getX();
            //setImageViewLocationToBoard(y,x);
            int type = w.getType();
            images[y][x].setImage(ImageLoader.getImage(type));
        }
    }
    
    /**
     * Asettaa kuvan lautakoordinaatteja vastaaviin piirtokoordinaatteihin.
     * @param y y-lautakoordinaatti
     * @param x x-lautakoordinaatti
     */
    public static void setImageViewLocationToBoard(int y, int x) {
        setImageViewLocation(y,x,
                            BoardDrawCoordinates.fromBoardReferenceToDrawInt(y),
                            BoardDrawCoordinates.fromBoardReferenceToDrawInt(x));
        
    }

    private static void addImageViewsToGroup() {
        for (int i = 0; i < round.getBoardSize(); i++) {
            for (int j = 0; j < round.getBoardSize(); j++) {
                setImageViewLocationToBoard(i,j);
                boardGroup.getChildren().addAll(images[i][j]);
            }
        }
        
    }
    
    public static void useAbility(int i) {
        round.useAbility(i);
    }
    
}
