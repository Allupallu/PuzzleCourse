package puzzlecourse.UI;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import puzzlecourse.containers.Board;
import puzzlecourse.containers.Coordinate;
import puzzlecourse.containers.Waypoint;

/**
 * Pelilaudan liikeanimaatioiden ylläpitoa hoitava luokka.
 * @author aleksi
 */
public class BoardDrawCoordinates {
    
    private static int COLORPIECE_SIZE = 91;
    
    
    private static final Queue<List<Waypoint>> waypointsQueue = new LinkedList();
    private static final List<Waypoint> fallingGroup = new LinkedList();
    private static boolean imageTypesUpdated;
   
    public static boolean hasWaypoints() {
        return !waypointsQueue.isEmpty();
    }
    
    /**
     * Laskee lautakoordinaatin koon piirtokoordinaateissa näytölle sopivaksi.
     * Erikseen kutsuttava ihan vain vältelläkseni joitain erroreita testeissä,
     * kun skaalautuvuudessa käytän graafisia työkaluja :p
     */
    public static void scaleColorPieceSize() {
        COLORPIECE_SIZE = ScalabilityLogic.getColorPieceImageSize();
    }
    
    /**
     * Päivittää piirtokoordinaatit ja palauttaa, onko vielä luvassa 
     * muutoksia piirtokoordinaatteihin.
     * @return onko piirtokoordinaattien vaihtoja jonossa
     */
    public static boolean updateDrawCoords() {
        if (!hasWaypoints()) {
            return false;
        }
        List<Waypoint> finished = moveWaypointsAndReturnFinished();
        waypointsQueue.peek().removeAll(finished);
        if (waypointsQueue.peek().isEmpty()) {
            waypointsQueue.poll();
            imageTypesUpdated = false;
          //  G_Updater.assignImagesToImageViews(waypointsQueue.poll());
        }
        if (waypointsQueue.isEmpty()) {
           G_Updater.assignImagesToImageViews();
        }
        
        return true;
    }
    
    private static List<Waypoint>  moveWaypointsAndReturnFinished() {
        List<Waypoint> finished = new LinkedList();
        
        if (!imageTypesUpdated) {
            G_Updater.assignImagesToImageViews(waypointsQueue.peek());
            imageTypesUpdated = true;
        }
        
        for (Waypoint way : waypointsQueue.peek()) {
            Coordinate boardRef = way.getBoardRef();
            Coordinate newCoord = way.getNewCoord();
            G_Updater.setImageViewLocation(boardRef.getY(),
                                           boardRef.getX(),
                                           newCoord.getY(),
                                           newCoord.getX());
            if (way.isFinished()) {
                G_Updater.setImageViewLocationToBoard(boardRef.getY(),
                                                      boardRef.getX());
                finished.add(way);
            }
        }
        return finished;
    }
    
    
    /**
     * Lisää animaatio-Waypointit vaihdettaville paikoille.
     * @param y1 lauta y1-koordinaatti
     * @param x1 lauta x1-koordinaatti
     * @param type1
     * @param y2 lauta y2-koordinaatti
     * @param x2 lauta x2-koordinaatti
     * @param type2
     */
    public static void addSwitchWaypoint(int y1, int x1, int type1, int y2, int x2, int type2) {
        Waypoint way1 = new Waypoint(y1,x1,y1,x1, type1);
        Waypoint way2 = new Waypoint(y2,x2,y2,x2, type2);
        way1.setCurrentCoordinatesFromBoardRef(y2, x2);
        way2.setCurrentCoordinatesFromBoardRef(y1, x1);
        LinkedList<Waypoint> switched = new LinkedList();
        switched.add(way1);
        switched.add(way2);
        waypointsQueue.add(switched);
    }
    
    /**
     * Lisää putoavalle palikalle putoamista.
     * @param y laudan y-koordinaatti
     * @param x laydan x-koordinaatti
     * @param type liikeanimaatiossa piirrettävän palan tyyppi
     */
    public static void addFall(int y, int x, int type) {
        int sameWaypointIndex = fallingGroup.indexOf(new Waypoint(y,x,y,x, type));
            if ( sameWaypointIndex == -1 ) {
                Waypoint newWaypoint = new Waypoint(y,x,y,x, type);
                newWaypoint.addBoardFall();
                fallingGroup.add(newWaypoint);
            } else {
                Waypoint oldWaypoint = fallingGroup.get( sameWaypointIndex );
                oldWaypoint.setType(type);
                oldWaypoint.addBoardFall();
            }
        
    }
    
    /**
     * Lisää uudelle laudalle putoamisanimaation.
     * @param board pelilauta, jolle animaatio lisätään
     */
    public static void addNewBoardFall(Board board) {
        int boardSize = board.getSize();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                for (int k = 0; k < boardSize; k++) {
                    int type = board.getPiece(i, j).getType();
                    addFall(i,j,type);
                }
            }
        }
        addFallingGroup();
        G_Updater.setMoveActive(true);
    }
    
    /**
     * Siirtää putoavien joukon piirrettäviin.
     */
    public static void addFallingGroup() {
        if (!fallingGroup.isEmpty()) {
            List<Waypoint> tempFalling = new LinkedList(fallingGroup);
            waypointsQueue.add(tempFalling);
            fallingGroup.clear();
        }
    }
    
    /**
     * Palauttaa annettua lautakoordinaattia vastaavan piirtokoordinaatin.
     * @param ref lautakoordinaatti
     * @return piirtokoordinaatti
     */
    public static int fromBoardReferenceToDrawInt(int ref) {
        return ref * COLORPIECE_SIZE;
    }
    /**
     * Palauttaa annettua piirtokoordinaattia vastaavan lautakoordinaatin.
     * @param draw piirtokoordinaatti
     * @return lautakoordinaatti
     */
    public static int fromDrawIntToBoardReference(int draw) {
        return draw / COLORPIECE_SIZE;
    }
    
    
    
    
    
    
    
}
