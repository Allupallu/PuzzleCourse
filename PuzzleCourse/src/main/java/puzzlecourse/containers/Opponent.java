package puzzlecourse.containers;

import java.util.List;

/**
 * Teköälyvastustajan luokka.
 * @author aleksi
 */
public class Opponent extends Player {

    private final Board board;
    private final int THINKING_TIME = 30;
    
    private int timeThought;
    private boolean introduced;
    
    public Opponent(boolean human, String name, String imageID, Board board) {
        super(human, name, imageID);
        this.board = board;
        setupDialog();
    }
    
    private void setupDialog() {
        dialogOptions = new String[2];
        dialogOptions[0] = "Hylätty, hylätty, hylätty! Kukaan teistä ei ole "
                         + "synnistä vapaa";
        dialogOptions[1] = "Jos jokin sanomani tai tekemäni on saanut teidät"
                         + " uskomaan, että olen pätevä, olen todella pahoillani.";
    }
    
    @Override // taidanp ehkä muuttaa tämän tiedostonlukijaksi myöhemmin
    public Dialog getDialogOption(int i) {
        if (introduced) {
            return new Dialog(this, dialogOptions[1], -1);
        } else {
            introduced = true;
            return new Dialog(this, dialogOptions[0], -1);
        }
    }
    
    @Override
    public boolean makeMove(int y, int x) {
        if (timeThought < THINKING_TIME) {
            timeThought++;
            return false;
        }
        timeThought = 0;
        List<Move> moves = board.findMoves(false);
        
        // Debug
        System.out.println("Available moves: "+moves);
        
        currentMove = findPreferredMove(moves);
        return true;
    }
    
    private Move findPreferredMove(List<Move> list) {
        Move preferred = list.get(0);
        for (Move move : list) {
            if (preferred.getValue() < move.getValue()) {
                preferred = move;
                System.out.println("Found a move of value "+preferred.getValue());
            }
        }
        if (preferred.getValue() == 0) {
            preferred = list.get((int)(Math.random() * list.size()) );
        }
        System.out.println("Opponent is making the move: "+ preferred);
        return preferred;
    }
    
    
}
