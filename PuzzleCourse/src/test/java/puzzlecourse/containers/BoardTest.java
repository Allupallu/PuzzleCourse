package puzzlecourse.containers;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.Timeout;

/**
 *
 * @author aleksi
 */
public class BoardTest {
    
    private Board board;
    private int size;
    
    public BoardTest() {
    }
    
    @Rule
    public Timeout globalTimeout= new Timeout(20);
    
    
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        board = new Board();
        board.newBoard();
        //newBoardForTestsIsFast();
        size = board.getSize();
    }
    
    @After
    public void tearDown() {
    }
    
    
    @Test
    public void noNegativeY1Switch() {
        boolean test = board.switchPieces(-1, 0, 0, 0);
        
        assertEquals(false,test);
    }
    @Test
    public void noNegativeY2Switch() {
        boolean test = board.switchPieces(0, 0, -1, 0);
        
        assertEquals(false,test);
    }
    @Test
    public void noNegativeX1Switch() {
        boolean test = board.switchPieces(0, -1, 0, 0);
        
        assertEquals(false,test);
    }
    @Test
    public void noNegativeX2Switch() {
        boolean test = board.switchPieces(0, 0, 0, -1);
        
        assertEquals(false,test);
    }
    @Test
    public void noTooLargeY1Switch() {
        boolean test = board.switchPieces(size, size-1, size-1, size-1);
        
        assertEquals(false,test);  
    }
    @Test
    public void noTooLargeY2Switch() {
        boolean test = board.switchPieces(size-1, size-1, size, size-1);
        
        assertEquals(false,test);  
    }
    @Test
    public void noTooLargeX1Switch() {
        boolean test = board.switchPieces(size-1, size, size-1, size-1);
        
        assertEquals(false,test);  
    }
    @Test
    public void noTooLargeX2Switch() {
        boolean test = board.switchPieces(size-1, size-1, size-1, size);
        
        assertEquals(false,test);  
    }
    @Test
    public void noDiagonalSwitch1() {
        boolean test = board.switchPieces(0, 0, 1, 1);
        
        assertEquals(false, test);
    }
    @Test
    public void noDiagonalSwitch2() {
        boolean test = board.switchPieces(1, 1, 0, 0);
        
        assertEquals(false, test);
    }
    @Test
    public void noDiagonalSwitch3() {
        boolean test = board.switchPieces(1, 0, 0, 1);
        
        assertEquals(false, test);
    }
    @Test
    public void noDiagonalSwitch4() {
        boolean test = board.switchPieces(0, 1, 1, 0);
        
        assertEquals(false, test);
    }
    @Test
    public void onlyAdjacentSwitch1() {
        boolean test = board.switchPieces(2, 0, 0, 0);
        
        assertEquals(false, test);
    }
    @Test
    public void onlyAdjacentSwitch2() {
        boolean test = board.switchPieces(0, 2, 0, 0);
        
        assertEquals(false, test);
    }
    @Test
    public void onlyAdjacentSwitch3() {
        boolean test = board.switchPieces(0, 0, 2, 0);
        
        assertEquals(false, test);
    }
    @Test
    public void onlyAdjacentSwitch4() {
        boolean test = board.switchPieces(0, 0, 0, 2);
        
        assertEquals(false, test);
    }
    @Test
    public void onlyAdjacentSwitch5() {
        boolean test = board.switchPieces(0, 0, size-1, size-1);
        
        assertEquals(false, test);
    }
    @Test
    public void noSwitchWithSelf() {
        boolean test = board.switchPieces(0, 0, 0, 0);
        
        assertEquals(false, test);
    }
    
    @Test
    public void AdjacentSwitch1() {
        boolean test = board.switchPieces(0, 0, 1, 0);
        
        assertEquals(true, test);
    }
    @Test
    public void AdjacentSwitch2() {
        boolean test = board.switchPieces(0, 0, 0, 1);
        
        assertEquals(true, test);
    }
    @Test
    public void AdjacentSwitch3() {
        boolean test = board.switchPieces(1, 0, 0, 0);
        
        assertEquals(true, test);
    }
    @Test
    public void AdjacentSwitch4() {
        boolean test = board.switchPieces(0, 1, 0, 0);
        
        assertEquals(true, test);
    }
    @Test
    public void AdjacentSwitch5() {
        boolean test = board.switchPieces(size-1, size-1, size-2, size-1);
        
        assertEquals(true, test);
    }
    @Test
    public void AdjacentSwitch6() {
        boolean test = board.switchPieces(size-1, size-1, size-1, size-2);
        
        assertEquals(true, test);
    }
    
    @Test
    public void noHorizontalThreesAfterNew() {
        boolean test = false;
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 3; j++) {
                if (board.getPiece(i, j).getType() == board.getPiece(i, j+1).getType()
                 && board.getPiece(i, j+1).getType() == board.getPiece(i, j+2).getType()) {
                    test = true;
                    i = size;
                    j = size;
                }
            }
        }
        assertEquals(false, test);
    }
    @Test
    public void noVerticalThreesAfterNew() {
        boolean test = false;
        
        for (int i = 0; i < size - 3; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getPiece(i, j).getType() == board.getPiece(i+1, j).getType()
                 && board.getPiece(i+1, j).getType() == board.getPiece(i+2, j).getType()) {
                    test = true;
                    i = size;
                    j = size;
                }
            }
        }
        assertEquals(false, test);
    }
    
    @Test
    public void findMoveTrueSizeSmall() {
        assertEquals(true, board.findMoves(true).size() < 5);
    }
    
    @Test
    public void moveListNotEmpty() {
        assertEquals(false, board.findMoves(false).isEmpty());
    }
    
    
    @Test // Tosi huonolla mäihällä kestää liian pitkään.
    public void findMoveWithValueNotZero() {
        int value = 0;
        while (value == 0) {
            List<Move> list = board.findMoves(false);
            for (Move m : list) {
                value = Math.max(m.getValue(), value);
            }
            if (value == 0) {
                board.newBoard();
            }
        }
        assertEquals(true, value > 0);
    }
    
    @Test
    public void noThreesOnFoundMove() {
        Move move = board.findMoves(true).get(0);
        assertEquals(true, board.findThreePiecesAt(move.getY(), move.getX()).isEmpty() &&
                            board.findThreePiecesAt(move.getY2(), move.getX2()).isEmpty());
        
    }
    
    @Test
    public void foundProperMove() {
        Move move = board.findMoves(true).get(0);
        board.switchPieces(move.getY(), move.getX(),
                           move.getY2(), move.getX2());
        assertEquals(false, board.findThreePiecesAt(move.getY(), move.getX()).isEmpty() &&
                            board.findThreePiecesAt(move.getY2(), move.getX2()).isEmpty());
        
    }
    
    
}