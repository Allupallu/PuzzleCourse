package puzzlecourse.containers;

import puzzlecourse.logic.GameRound;

/**
 * Pelaajan liikkeitä säilövä otus.
 * @author aleksi
 */
public class Move {
    
    private GameRound round;
    private Coordinate coords1;
    
    public Move(GameRound round, int y, int x) {
        this.round = round;
        coords1 = new Coordinate(y,x);
    }
    
    public int getY() {
        return coords1.getY();
    }
    public int getX() {
        return coords1.getX();
    }
    
    public boolean addCoord(int y, int x) {
        if (coords1.getY() == y && coords1.getX() == x) {
            return true;
        }
        return round.switchPieces(coords1.getY(), coords1.getX(), y, x);
    }
    
    
}
