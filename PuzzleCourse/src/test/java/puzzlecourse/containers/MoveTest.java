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
public class MoveTest {
    
    public MoveTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void sameCoordsReturnTrue() {
        Move move = new Move( 1, 1);
        assertEquals(true, move.addCoord(1, 1));
    }
    @Test
    public void noDiagonalMove() {
        Move move = new Move( 1, 1);
        assertEquals(false, move.addCoord(2, 2));
    }
    @Test
    public void getRightX() {
        Move move = new Move( 1,2);
        assertEquals(2, move.getX());
    }
    @Test
    public void getRightY() {
        Move move = new Move( 2,1);
        assertEquals(2, move.getY());
    }
    
    
}
