/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzlecourse.containers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import puzzlecourse.logic.GameRound;

/**
 *
 * @author aleksi
 */
public class PlayerTest {
    
    public PlayerTest() {
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
    public void rightName() {
        Player player = new Player(true, "Seppo", "player");
        assertEquals("Seppo", player.getName());
    }
    @Test
    public void rightImageID() {
        Player player = new Player(true, "Seppo", "player");
        assertEquals("player", player.getImageID());
    }
    @Test
    public void isHuman() {
        Player player = new Player(true, "Seppo", "player");
        assertEquals(true, player.isHuman());
    }
    @Test
    public void aintHuman() {
        Player player = new Player(false, "Seppo", "opponent");
        assertEquals(false, player.isHuman());
    }
    @Test
    public void moveNotNull1() {
        Player player = new Player(true, "Seppo", "player");
        player.makeMove(new GameRound(), 0, 0);
        assertEquals(false, player.getCurrentMoveY() == -1);
    }
    @Test
    public void moveNotNull2() {
        Player player = new Player(true, "Seppo", "player");
        player.makeMove(new GameRound(), 0, 0);
        assertEquals(false, player.getCurrentMoveX() == -1);
    }
    @Test
    public void moveRightX() {
        Player player = new Player(true, "Seppo", "player");
        player.makeMove(new GameRound(), 5, 6);
        int x;
        try {
            x = player.getCurrentMoveX();
        } catch(Exception e) {
            x = -1;
        }
        
        assertEquals(true, player.getCurrentMoveX() == 6);
    }
    @Test
    public void moveRightY() {
        Player player = new Player(true, "Seppo", "player");
        player.makeMove(new GameRound(), 5, 6);
        int x;
        try {
            x = player.getCurrentMoveY();
        } catch(Exception e) {
            x = -1;
        }
        
        assertEquals(true, player.getCurrentMoveY() == 5);
    }
    @Test
    public void cancelMoveResultsInNullMove() {
        Player player = new Player(true, "Seppo", "player");
        GameRound round = new GameRound();
        player.makeMove(round, 5, 6);
        player.makeMove(round, 5, 6);
        
        assertEquals(player.getCurrentMoveX(), -1);
    }
    
}
