package puzzlecourse.logic;

import java.util.LinkedList;
import java.util.List;
import puzzlecourse.UI.BoardDrawCoordinates;
import puzzlecourse.UI.DialogLayer;
import puzzlecourse.UI.G_Updater;
import puzzlecourse.containers.Ability;
import puzzlecourse.containers.Board;
import puzzlecourse.containers.Coordinate;
import puzzlecourse.containers.Move;
import puzzlecourse.containers.Player;

/**
 * Pelierä. Säilöö ja ylläpitää erän laudan, pelaajat, moiset.
 * @author aleksi
 */
public class GameRound {
    
    private final Board board;
    private final List<Player> players;
    private int currentPlayer;
    private boolean opponentIntroduced;
    
    public GameRound() {
        board = new Board();
        players = new LinkedList<>();
        players.add(TextFileLoader.getPlayerFromFile(board, "player"));
        players.add(TextFileLoader.getPlayerFromFile(board, "opponent"));
        setEndConditions();
    }
    
    private void setEndConditions() {
        players.get(0).setVictoryConditionForType(2, 50);
        players.get(1).setVictoryConditionForType(0, 50);
    }
    
    private void checkEndConditions() {
        for (Player p : players) {
            if (p.isVictoryConditionMet()) {
                DialogLayer.addDialog(TextFileLoader.getDialogFromFile(this, p == getPlayer(0) ? "victory" : "defeat"));
                G_Updater.endRound(p == getPlayer(0));
            }
        }
    }
    
    /**
     * Uusi pelilauta. Korvaa kaikki palaset laudalla.
     */
    public void newBoard() {
        board.newBoard();
        newBoardDialog();
        BoardDrawCoordinates.addNewBoardFall(board);
    }
    private void newBoardDialog() {
        if (opponentIntroduced) {   
            DialogLayer.addDialog(TextFileLoader.getDialogFromFile(this, "newBoard"));
        }
     }
    
    /**
     * Esittelee vastustajan dialogilla.
     */
    public void introduceOpponent() {
        DialogLayer.addDialog(TextFileLoader.getDialogFromFile(this, "introduction"));
        opponentIntroduced = true;
    }
    
    public int getBoardSize() {
        return board.getSize();
    }
    
    public Player getPlayer(int i) {
        return players.get(i);
    }
    
    public boolean isHumanTurn() {
        return players.get(currentPlayer).isHuman();
    }
    
    public int getTypeAt(int y, int x) {
        return board.getPiece(y, x).getType();
    }
    
    /**
     * Etsii ja palauttaa listan kaikista kelpaavista liikkeistä.
     * @return lista liikkeistä
     */
    public List<Move> findMoves() {
        return board.findMoves(false);
    }
    
    /**
     * Koittaa liittää annetut lautakoordinaatit osaksi tämänhetkisen pelaajan 
     * liikettä.
     * @param y laudan y-koordinaatti
     * @param x laudan x-koordinaatti
     * @return 
     */
    public boolean makeMove(int y, int x) {
        boolean move = players.get(currentPlayer).makeMove(y,x);
        if (move) {
            if (switchPieces(players.get(currentPlayer).getCurrentMove())) {
                nextPlayer();
                //System.out.println("Hyvä vaihto.");
            } else {
                if (players.get(currentPlayer).isHuman()) {
                   players.get(currentPlayer).getCurrentMove().reverseBadCoord();
                }
                //System.out.println("Huono vaihto.");
            }
        }
        return move;
    }
    
    /**
     * Tällä käytetään pelaajan napeista aktivoitavia kykyjä.
     * @param abilitySlot monesko kyky
     * @return onnistuiko kyvyn käyttö
     */
    public boolean useAbility(int abilitySlot) {
        Player player = getPlayer(currentPlayer);
        if (player.getAbilityAvailability() &&
            player.hasResourcesForAbility(abilitySlot)) {
            
            
            Ability usedAbility = player.getAbility(abilitySlot);
        
            List<Coordinate> affectedCoordinates = board.getTypeCoordinates(usedAbility.targetType());
        
            // Itse käyttöosa
            switch(usedAbility.getEffect()) {
                case REPLACE: replaceTypes(affectedCoordinates, usedAbility.changesToType());
                              break;
                case DESTROY: destroyLoop(affectedCoordinates);
                              break;
            }
            
            player.setAbilityAvailable(false);
            player.payAbilitysRequirements(abilitySlot);
            checkEndConditions();
            return true;
        }
        return false;
    }
    
    private void replaceTypes(List<Coordinate> list, int toType) {
        for (Coordinate c : list) {
            board.getPiece(c.getY(), c.getX()).changeType(toType);
        }
        G_Updater.assignImagesToReplaced(list, toType);
        List<Coordinate> threes = findThreesFromReplacedCoordinates(list);
        if (!threes.isEmpty())
            destroyLoop(threes);
        
    }
    private List<Coordinate> findThreesFromReplacedCoordinates(List<Coordinate> list) {
        List<Coordinate> threes = new LinkedList<>();
        for (Coordinate c : list) {
            threes.addAll(threesAt(c.getY(), c.getX()));
        }
        return threes;
    }
    
    private void nextPlayer() {
        checkEndConditions();
        getPlayer(currentPlayer).setAbilityAvailable(false);
        currentPlayer = (currentPlayer+1) % players.size();
        getPlayer(currentPlayer).setAbilityAvailable(true);
        
    }
    
    
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
        destroyLoop(list);
        return true;
    }

    /**
     * switchPieces-metodin Move-parametrinen versio.
     * @see puzzlecourse.logic.GameRound#switchPieces(int, int, int, int) 
     * @param move yritettävä vaihto
     * @return onnistuiko vaihto
     */
    public boolean switchPieces(Move move) {
        return switchPieces(move.getY(),move.getX(),
                            move.getY2(),move.getX2());
    }
    
    
    private void checkBoardAfterSwitch() {
        if (board.findMoves(true).isEmpty()) {
            newBoard();
        }
    }
    
    private List<Coordinate> threesAt(int y, int x) {
        List<Coordinate> list = new LinkedList<>();
        list.addAll(board.findThreePiecesAt(y, x));
        return list;
    }
    private void destroyLoop(List<Coordinate> list) {
        while (!list.isEmpty()) {
            list = destroy(list);
        }
        checkBoardAfterSwitch();
    }
    
    private List<Coordinate> destroy(List<Coordinate> list) {
        int[] pieceTypeTable = new int[6];
        for (Coordinate c : list) {
            pieceTypeTable[getTypeAt(c.getY(),c.getX())]++;
        }
        getPlayer(currentPlayer).addToCollected(pieceTypeTable);
        return board.destroyAt(list);
        
    }
    
    
}
