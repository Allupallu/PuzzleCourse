package puzzlecourse.containers;

import java.util.Objects;

/**
 * Pelaajan liikkeitä säilövä otus.
 * @author aleksi
 */
public class Move {
    
   // private GameRound round;
    private Coordinate coords1;
    private Coordinate coords2;
    
    private boolean complete;
    private int value;
    
    public Move(int y, int x) {
        coords1 = new Coordinate(y,x);
    }
    
    public Move(int y, int x, int y2, int x2) {
        this(y, x);
        coords2 = new Coordinate(y2, x2);
        complete = true;
    }
    
    public int getY() {
        return coords1.getY();
    }
    public int getX() {
        return coords1.getX();
    }
    
    public int getY2() {
        return coords2.getY();
    }
    public int getX2() {
        return coords2.getX();
    }
    
    public void increaseValue() {
        value++;
    }
    public int getValue() {
        return value;
    }
    public boolean getComplete() {
        return complete;
    }
    
    /**
     * 
     * @param y y-lautakoordinaatti
     * @param x x-lautakoordinaatti
     * @return palauttaa true, jos lisäys peruuttaa liikkeen
     */
    public boolean addCoord(int y, int x) {
        coords2 = new Coordinate(y,x);
        complete = true;
        return coords1.getY() == y && coords1.getX() == x;
    }
    /**
     * Kun liike ei kelvannut.
     */
    public void reverseBadCoord() {
        complete = false;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.coords1);
        hash = 41 * hash + Objects.hashCode(this.coords2);
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
        final Move other = (Move) obj;
        
        if (Objects.equals(this.coords1, other.coords1) &&
            Objects.equals(this.coords2, other.coords2) ||
            Objects.equals(this.coords1, other.coords2) &&
            Objects.equals(this.coords2, other.coords1)) {
            return true;
        }
        return false;
    }
    
    // Debugaus-juttu
    @Override
    public String toString() {
        return "" + getY() + "," + getX() + ";" + getY2() + "," +getX2();
    }
    
    
}
