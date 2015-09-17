package puzzlecourse.logic;

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
public class GameRoundTest {
    
    private GameRound round;
    private int size;
    
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
    public void findAndTestMove() { // Onnesta kiinni yha. Paranna.
        boolean test = false;
        
        for (int i = 0 ; i < size; i++ ) {
            for (int j = 0; j < size; j++) {
                
                if (i < size -3) { // verticals
                    if (j < size -1 && sameType3Coords(i,j,i+1,j,i+2,j+1)) {
                        test = round.switchPieces(i+2, j, i+2, j+1);
                        i = size;
                        j = size;
                    } else
                        if (j > 0 && sameType3Coords(i,j,i+1,j,i+2,j-1)) {
                            test = round.switchPieces(i+2, j, i+2, j-1);
                            i = size;
                            j = size;
                        }
                }
            }
        }
        assertEquals(true, test);
    }
    private boolean sameType3Coords(int a, int b, int c, int d, int e, int f) {
        int type1 = round.getTypeAt(a, b);
        int type2 = round.getTypeAt(c, d);
        int type3 = round.getTypeAt(e, f);
        return type1 == type2 && type2 == type3;
    }
    
    
}