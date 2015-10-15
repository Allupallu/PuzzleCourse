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
    
    /**
     * Luo vastustaja-olion, eli pelaaja-olio tekoälyllä.
     * @param name vastustajan nimi
     * @param imageID vastustajan kuvan tiedostonimi sans .png
     * @param board lauta, johon tekoäly kajoaa
     */
    public Opponent(String name, String imageID, Board board) {
        super(false, name, imageID);
        this.board = board;
    }
    
    @Override
    public boolean makeMove(int y, int x) {
        if (timeThought < THINKING_TIME) {
            timeThought++;
            return false;
        }
        timeThought = 0;
        List<Move> moves = board.findMoves(false);
        
        currentMove = findPreferredMove(moves);
        return true;
    }
    
    private Move findPreferredMove(List<Move> list) {
        Move preferred = list.get(0);
        for (Move move : list) {
            if (preferred.getValue() < move.getValue()) {
                preferred = move;
                //System.out.println("Found a move of value "+preferred.getValue());
            }
        }
        if (preferred.getValue() == 0) {
            preferred = list.get((int)(Math.random() * list.size()) );
        }
        //System.out.println("Opponent is making the move: "+ preferred);
        return preferred;
    }
    
    
}
