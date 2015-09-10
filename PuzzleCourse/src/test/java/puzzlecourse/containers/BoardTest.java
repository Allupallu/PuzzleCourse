package puzzlecourse.containers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aleksi
 */
public class BoardTest {
    
    private Board board;
    private int size;
    
    public BoardTest() {
    }
    
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
}
