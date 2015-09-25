package puzzlecourse.logic;

import java.util.LinkedList;
import java.util.List;
import puzzlecourse.UI.BoardDrawCoordinates;
import puzzlecourse.containers.Board;
import puzzlecourse.containers.Coordinate;
import puzzlecourse.containers.Player;

/**
 * Pelierä. Säilöö ja ylläpitää erän laudan, pelaajat, moiset.
 * @author aleksi
 */
public class GameRound {
    
    private final Board board;
    private final List<Player> players;
    private int currentPlayer;
    
    public GameRound() {
        board = new Board();
        players = new LinkedList<>();
        players.add(new Player(true, "The Student", "player"));
        players.add(new Player(false, "The Opponent", "opponent"));
    }
    
    /**
     * Uusi pelilauta. Korvaa kaikki palaset laudalla.
     */
    public void newBoard() {
        board.newBoard();
    }
    
    public int getBoardSize() {
        return board.getSize();
    }
    
    public Player getPlayer(int i) {
        return players.get(i);
    }
    
    public int getTypeAt(int y, int x) {
        return board.getPiece(y, x).getType();
    }
    
    /**
     * Koittaa liittää annetut lautakoordinaatit osaksi tämänhetkisen pelaajan 
     * liikettä.
     * @param y laudan y-koordinaatti
     * @param x laudan x-koordinaatti
     * @return 
     */
    public boolean makeMove(int y, int x) {
        boolean move = players.get(currentPlayer).makeMove(this,y,x);
        if (move) {
            //nextPlayer();  <- Tämä pitää korjata!
        }
        return move;
    }
    
    
    /*
    private void nextPlayer() {
        if (currentPlayer < players.size() - 1) {
            currentPlayer++;
        } else {
            currentPlayer = 0;
        }
    }
    */
    
    /**
     * Yrittää vaihtaa kahden laudan palan paikat ja onnistuneessa
     * vaihdossa ilmoittaa uudesta siirtoanimaatiosta ja käynnistää
     * muut laudan muutokset.
     * @param y1 ensimmäisen palan y-lautakoordinaatti
     * @param x1 ensimmäisen palan x-lautakoordinaatti
     * @param y2 toisen palan y-lautakoordinaatti
     * @param x2 toisen palan x-lautakoordinaatti
     * @return onnistuiko vaihto
     */
    public boolean switchPieces(int y1, int x1, int y2, int x2) {
        if (!board.switchPieces(y1, x1, y2, x2)) {
            return false;
        }
        // The pieces have been switched once. Quality check.
        List<Coordinate> list = threesAt(y1,x1);
        list.addAll(threesAt(y2,x2));
        if (list.isEmpty()) { //Switch back if the move wasn't valid.
            board.switchPieces(y1, x1, y2, x2);
            return false;
        }
        BoardDrawCoordinates.addSwitchWaypoint(y1, x1, getTypeAt(y1,x1), y2, x2, getTypeAt(y2,x2)); // <- Animaatiolle
        while (!list.isEmpty()) {
            list = destroy(list);
        }
        return true;
    }
    
    private List<Coordinate> threesAt(int y, int x) {
        List<Coordinate> list = new LinkedList<>();
        list.addAll(board.findThreePiecesAt(y, x));
        return list;
    }
    private List<Coordinate> destroy(List<Coordinate> list) {
        
        return board.destroyAt(list);
        
    }
    
    
}
