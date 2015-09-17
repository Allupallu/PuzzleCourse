package puzzlecourse.logic;

import java.util.LinkedList;
import java.util.List;
import puzzlecourse.containers.Board;
import puzzlecourse.containers.Coordinate;
import puzzlecourse.containers.Player;

/**
 *
 * @author aleksi
 */
public class GameRound {
    
    private Board board;
    private List<Player> players;
    private int currentPlayer;
    
    public GameRound() {
        board = new Board();
        players = new LinkedList<>();
        players.add(new Player(true));
    }
    
    public void newBoard() {
        board.newBoard();
    }
    
    public int getBoardSize() {
        return board.getSize();
    }
    
    public int getTypeAt(int y, int x) {
        return board.getPiece(y, x).getType();
    }
    
    public boolean makeMove(int y, int x) {
        boolean move = players.get(currentPlayer).makeMove(this,y,x);
        if (move) {
            nextPlayer();
        }
        return move;
    }
    
    private void nextPlayer() {
        if (currentPlayer < players.size() - 1) {
            currentPlayer++;
        } else {
            currentPlayer = 0;
        }
    }
    
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
        
        /*
        for (Coordinate c : list) {
            board.destroyAt();
            for (int i = c.getY(); i >= 0; i--) {
                destroy(threesAt(i, c.getX()));
            }
        }
        */
    }
    
    
}
