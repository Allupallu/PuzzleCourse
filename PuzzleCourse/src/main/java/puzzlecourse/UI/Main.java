package puzzlecourse.UI;

import puzzlecourse.logic.GameRound;

/**
 * Pääluokka.
 * @author aleksi
 */
public class Main {
    
    public static void main(String[] args) {
        
        GameRound round = new GameRound();
        round.newBoard();
        TerminalUI.start(round);
        
    }
    
}
