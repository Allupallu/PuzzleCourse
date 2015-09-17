/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzlecourse.containers;

import puzzlecourse.logic.GameRound;

/**
 *
 * @author aleksi
 */
public class Player {
    
    private int score;
    
    private final boolean humanPlayer;
    
    private Move currentMove;
    
    public Player(boolean human) {
        humanPlayer = human;
    }
    
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
