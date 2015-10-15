package puzzlecourse.containers;

import java.util.Objects;
import puzzlecourse.UI.BoardDrawCoordinates;

/**
 * Kuvaa tiettyyn koordinaattiin liikkumisen vaihetta värillisen
 * nappulan animaatiossa.
 * Nopeus ei riipu x/y-akselista, joten kulmat, joille liikkeen
 * aste % 45 != 0, liikkuvat hassusti.
 * @author aleksi
 */
public class Waypoint {
    
    private static final int DEFAULT_SPEED = 2;
    private static final int MAX_SPEED = 40;
    private static final int ACCELERATION = 3;
    
    private final Coordinate boardReference;
    private int speed;
    private int type;
    private final int goalY, goalX;
    private int currentY, currentX;
    
    /**
     * Luo annetuissa koordinaateissa sijaitsevalle nappulalle
     * liikeanimaation annettuihin koordinaatteihin. Tallentaa
     * myös animoitavan nappulan tyypin.
     * @param y liikutettavan nappulan referenssi y-koordinaatti laudalla
     * @param x liikutettavan nappulan referenssi x-koordinaatti laudalla
     * @param goalY liikkeen kohteen y-lautakoordinaatti
     * @param goalX liikkeen kohteen x-lautakoordinaatti
     * @param type nappulan tyyppi animaation aikana
     */
    public Waypoint(int y, int x, int goalY, int goalX, int type) {
        boardReference = new Coordinate(y,x);
        currentY = BoardDrawCoordinates.fromBoardReferenceToDrawInt(y);
        currentX = BoardDrawCoordinates.fromBoardReferenceToDrawInt(x);
        this.goalY = BoardDrawCoordinates.fromBoardReferenceToDrawInt(goalY);
        this.goalX = BoardDrawCoordinates.fromBoardReferenceToDrawInt(goalX);
        speed = DEFAULT_SPEED;
        this.type = type;
    }
    
    /**
     * Siirtää piirtokoordinaatit yhden lautakoordinaatin ylemmäksi.
     */
    public void addBoardFall() {
        currentY -= BoardDrawCoordinates.fromBoardReferenceToDrawInt(1);
    }
    
    public void setCurrentCoordinatesFromBoardRef(int y, int x) {
        currentY = BoardDrawCoordinates.fromBoardReferenceToDrawInt(y);
        currentX = BoardDrawCoordinates.fromBoardReferenceToDrawInt(x);
    }
    
    public Coordinate getBoardRef() {
        return boardReference;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    
    /**
     * Päivittää ja palauttaa liikkeen koordinaatit.
     * @return uudet piirtokoordinaatit
     */
    public Coordinate getNewCoord() {
        updateX();
        updateY();
        accelerate();
        return new Coordinate(currentY, currentX);
    }
    
    private void updateX() {
        if ( (addSpeed(currentX, goalX) - goalX) * ( currentX - goalX) < 0 ) {
             currentX = goalX;
        } else {
            currentX = addSpeed(currentX, goalX);
        }
    }
    private void updateY() {
        if ( (addSpeed(currentY, goalY) - goalY) * ( currentY - goalY) < 0 ) {
             currentY = goalY;
        } else {
            currentY = addSpeed(currentY, goalY);
        }
    }
    
    private int addSpeed(int current, int goal) {
        if (current < goal) {
            return current + speed;
        } else if (current > goal) {
            return current - speed;
        } else {
            return goal;
        }
    }
    
    private void accelerate() {
        if (speed + ACCELERATION < MAX_SPEED) {
            speed += ACCELERATION;
        } else {
            speed = MAX_SPEED;
        }
    }
    
    /**
     * Onko waypoint saavuttanut loppupisteen.
     * @return onko loppupiste saavutettu
     */
    public boolean isFinished() {
        return currentY == goalY && currentX == goalX;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.boardReference);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Waypoint other = (Waypoint) obj;
        if (!Objects.equals(this.boardReference, other.boardReference)) {
            return false;
        }
        return true;
    }
    
    
    
}
