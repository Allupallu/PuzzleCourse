package puzzlecourse.containers;

import puzzlecourse.logic.GameRound;

/**
 * Pelin pelaaja; ihminen tai ei.
 * @author aleksi
 */
public class Player {
    
    private int score;
    private String name;
    private String imageID;
    
    private final boolean humanPlayer;
    
    private Move currentMove;
    
    public Player(boolean human, String name, String imageID) {
        humanPlayer = human;
        this.name = name;
        this.imageID = imageID;
    }
    
    public String getName() {
        return name;
    }
    public String getImageID() {
        return imageID;
    }
    public boolean isHuman() {
        return humanPlayer;
    }
    
    /**
     * Liittää annetut koordinaatit osaksi pelaajan vuoron liikettä.
     * @param round erä, jota pelaaja sörkkii
     * @param y laudan y-koordinaatti
     * @param x laudan x-koordinaatti
     * @return 
     */
    public boolean makeMove(GameRound round, int y, int x) {
        if (currentMove == null) {
            currentMove = new Move(round, y,x);
            return true;
        } else {
            if (currentMove.addCoord(y, x)) {
                currentMove = null;
                return true;
            }
        }
        return false;
    }
    
    public int getCurrentMoveY() {
        if (currentMove == null) {
            return -1;
        }
        return currentMove.getY();
    }
    public int getCurrentMoveX() {
        if (currentMove == null) {
            return -1;
        }
        return currentMove.getX();
    }
    
    
}
