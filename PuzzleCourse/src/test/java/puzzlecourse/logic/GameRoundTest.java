package puzzlecourse.logic;

import java.util.Calendar;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.Timeout;
import puzzlecourse.UI.BoardDrawCoordinates;
import puzzlecourse.containers.Move;

/**
 *
 * @author aleksi
 */
public class GameRoundTest {
    
    private GameRound round;
    private int size;
    @Rule
    public Timeout globalTimeout= new Timeout(4000);
    
    public GameRoundTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        round = new GameRound();
        round.newBoard();
        size = round.getBoardSize();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void newBoardDoesSomething() {
        boolean test = false;
        
        try {
            round.getTypeAt(0, 0);
            test = true;
        } catch(Exception e) {
            
        }
        
        assertEquals(true, test);
    }
    
    @Test
    public void newBoardAddsFall() {
        assertEquals(true, BoardDrawCoordinates.hasWaypoints());
    }
    
    @Test
    public void twoPlayers() {
        assertEquals(false, round.getPlayer(1) == null);
    }
    
    @Test
    public void boardSizeNotSillySmall() {
        assertEquals(true, size > 1);
    }
    
    @Test
    public void typesAreNotJustZeroes() {
        assertEquals(true, round.getTypeAt(0, 0) != 0
                         ||round.getTypeAt(0, 1) != 0
                         ||round.getTypeAt(0, 2) != 0);
    }
    @Test
    public void typesAreNotJustOnes() {
        assertEquals(true, round.getTypeAt(0, 0) != 1
                         ||round.getTypeAt(0, 1) != 1
                         ||round.getTypeAt(0, 2) != 1);
    }
    @Test
    public void typesAreNotJustOnes2() {
        assertEquals(true, round.getTypeAt(1, 0) != 1
                         ||round.getTypeAt(1, 1) != 1
                         ||round.getTypeAt(1, 2) != 1);
    }
    
    @Test
    public void movesNotNull() {
        assertEquals(false, round.findMoves() == null);
    }
    @Test
    public void movesNotEmpty() {
        assertEquals(false, round.findMoves().isEmpty());   
    }
    
    @Test
    public void onlyListMovesAllowed() {
        List<Move> list = round.findMoves();
        for (int i = 0; i < round.getBoardSize(); i++) {
            Move move = new Move(0, i, 1, i);
            if (!list.contains(move)) {
                assertEquals(false, round.switchPieces(move));
                i = round.getBoardSize();
            }
        }
    }
    
    @Test
    public void movesWork() {
        List<Move> list = round.findMoves();
        
        assertEquals(round.switchPieces(list.get(0)), true);
    }
    @Test
    public void movesWork2() {
        List<Move> list = round.findMoves();
        
        assertEquals(round.switchPieces(list.get(Math.min(0, 1))),
                                        true);
    }
    @Test
    public void movesWork3() {
        List<Move> list = round.findMoves();
        
        assertEquals(round.switchPieces(list.get(Math.min(0, 2))),
                                        true);
    }
    
    @Test
    public void makeMoveWorksWithMove() {
        Move move = round.findMoves().get(0);
        round.makeMove(move.getY(), move.getX());
        assertEquals(true, round.makeMove(move.getY2(), move.getX2()));   
    }
    @Test
    public void makeMoveWorksWithMoveAfterMoveCancel() {
        round.makeMove(0, 0);
        round.makeMove(0, 0);
        Move move = round.findMoves().get(0);
        round.makeMove(move.getY(), move.getX());
        assertEquals(true, round.makeMove(move.getY2(), move.getX2()));   
    }
    @Test
    public void makeMoveDoesntWorkIfEarlierNotCancelled() {
        Move move = round.findMoves().get(0);
        int y = Math.max(size-1, move.getY());
        round.makeMove(y, 0);
        boolean test = round.makeMove(move.getY(), move.getX());
        assertEquals(true, round.makeMove(move.getY2(), move.getX2())
                            == test);
        // makeMoven ei pitäisi palauttaa true kahdesti peräkkäin
    }
    
    @Test
    public void startWithHumanTurn() {
        assertEquals(true, round.isHumanTurn());
    }
    
    @Test
    public void makeMoveWorksWithMoveChangesPlayer() {
        Move move = round.findMoves().get(0);
        round.makeMove(move.getY(), move.getX());
        round.makeMove(move.getY2(), move.getX2());
        assertEquals(false, round.isHumanTurn());
    }
    @Test (timeout=4000)
    public void opponentReturnsTheTurn() {
        Move move = round.findMoves().get(0);
        round.makeMove(move.getY(), move.getX());
        round.makeMove(move.getY2(), move.getX2());
        while (round.makeMove(0, 0) == false) {
            
        }
        assertEquals(true, round.isHumanTurn());
    }
    
    @Test
    public void noFarSwitch() {
        assertEquals(false, round.switchPieces(0, 0, size-1, size-1));
    }
    
    @Test (timeout=3000) // Kestää pitkään, kun tekee paljon liikkeitä
    public void cannotExhaustMovesInOneSecond() {
        boolean moves = true;
        
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, 1);
        while (Calendar.getInstance().compareTo(c) < 0) {
            try {
                Move move = round.findMoves().get(0);
                round.switchPieces(move);
            } catch(Exception e) {
                moves = false;
                break;
            }
        }
        assertEquals(moves, true);
    }
    
}
