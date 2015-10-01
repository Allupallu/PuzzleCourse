package puzzlecourse.containers;

import puzzlecourse.logic.GameRound;

/**
 * Pelin pelaaja; ihminen tai ei.
 * @author aleksi
 */
public class Player {
    
    private String name;
    private String imageID;
    
    protected String[] dialogOptions;
    
    private final boolean humanPlayer;
    
    protected Move currentMove;
    
    public Player(boolean human, String name, String imageID) {
        humanPlayer = human;
        this.name = name;
        this.imageID = imageID;
        dialogOptions = new String[] {"..."};
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
    
    public Dialog getDialogOption(int i) {
        if (i < dialogOptions.length)
            return new Dialog(this, dialogOptions[i], -1);
        else
            return new Dialog(this, dialogOptions[0], -1);
    }
    
    /**
     * Liittää annetut koordinaatit osaksi pelaajan vuoron liikettä.
     * @param y laudan y-koordinaatti
     * @param x laudan x-koordinaatti
     * @return yritetäänkö siirtoa pelaajan liikkeellä
     */
    public boolean makeMove(int y, int x) {
        if (currentMove == null || currentMove.getComplete()) {
            currentMove = new Move(y,x);
            System.out.println("Vaihto: " + y + ", " + x);
            return false;
        } else if (currentMove.addCoord(y,x)) {
          currentMove = null;
            System.out.println("Vaihto kumottu.");
          return false;
        }
        return true;
    }
    
    public Move getCurrentMove() {
        return currentMove;
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
