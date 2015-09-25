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
    public void canCancelMove() {
        round.makeMove(0, 0);
        assertEquals(true, round.makeMove(0, 0));
    }
    @Test
    public void noJumpingMoves() {
        round.makeMove(0, 0);
        assertEquals(false, round.makeMove(0, 2));
    }
    

    /*
    @Test
    public void findAndTestMove() { // Hirviötesti, koska sattumanvaraisuus ja laiska
        boolean test = false;
        
        // Seuraavan meinaan korvata, kunhan saan tulevaisuudessa
        // käytettäväksi suunnittelemani OpponentAI-homman valmiiksi.
        // Se joutuu kuitenkin etsimään sopivia liikkeitä public metodilla.
        
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
                    else
                        if (j < size -1 && sameType3Coords(i,j,i+1,j+1,i+2,j)) {
                            test = round.switchPieces(i+2, j, i+1, j+1);
                            i = size;
                            j = size;
                        }
                    else
                        if (j > 0 && sameType3Coords(i,j,i+1,j-1,i+2,j)) {
                            test = round.switchPieces(i+2, j, i+1, j-1);
                            i = size;
                            j = size;
                        }
                    else
                        if (j < size -1 && sameType3Coords(i,j+1,i+1,j,i+2,j)) {
                            test = round.switchPieces(i+2, j, i, j+1);
                            i = size;
                            j = size;
                        }
                    else
                        if (j > 0 && sameType3Coords(i,j-1,i+1,j,i+2,j)) {
                            test = round.switchPieces(i+2, j, i, j-1);
                            i = size;
                            j = size;
                        }
                    else
                        if (i > 0 && sameType3Coords(i-1,j,i+1,j,i+2,j)) {
                            test = round.switchPieces(i-1, j, i, j);
                            i = size;
                            j = size;
                        }
                    else
                        if (i > 0 && sameType3Coords(i-1,j,i,j,i+2,j)) {
                            test = round.switchPieces(i+1, j, i+2, j);
                            i = size;
                            j = size;
                        }
                }
            }
        }
        assertEquals(true, test);
    }
    */
    private boolean sameType3Coords(int a, int b, int c, int d, int e, int f) {
        int type1 = round.getTypeAt(a, b);
        int type2 = round.getTypeAt(c, d);
        int type3 = round.getTypeAt(e, f);
        return type1 == type2 && type2 == type3;
    }
    
    
    
}
