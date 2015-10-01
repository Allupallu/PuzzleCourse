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

/**
 *
 * @author aleksi
 */
public class DialogTest {
    
    public DialogTest() {
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
    public void getTestText() {
        Dialog d = new Dialog(new Player(true,"Seppo", "player"),"testi", -1);
        assertEquals("t", d.getText());
    }
    @Test
    public void getTestText2() {
        Dialog d = new Dialog(new Player(true,"Seppo", "player"),"testi", -1);
        d.getText();
        assertEquals("te", d.getText());
    }
    @Test
    public void getTestText3() {
        Dialog d = new Dialog(new Player(true,"Seppo", "player"),"testi", -1);
        d.getText();
        d.getText();
        d.getText();
        assertEquals("test", d.getText());
    }
    @Test
    public void getTestText4() {
        Dialog d = new Dialog(new Player(true,"Seppo", "player"),"testi", -1);
        d.getText();
        d.getText();
        d.getText();
        d.getText();
        d.getText();
        assertEquals("testi", d.getText());
    }
    @Test
    public void timedFinishTestTrue() {
        Dialog d = new Dialog(new Player(true,"Seppo", "player"),"testi",0);
        assertEquals(true, d.dialogTimedFinish());
    }
    @Test
    public void timedFinishTestFalse() {
        Dialog d = new Dialog(new Player(true,"Seppo", "player"),"testi",-1);
        assertEquals(false, d.dialogTimedFinish());
    }
    @Test
    public void clickFinishTestTrue() {
        Dialog d = new Dialog(new Player(true,"Seppo", "player"),"t", -1);
        d.getText();
        assertEquals(true, d.dialogClickFinish());
    }
    @Test
    public void clickFinishTestFalse() {
        Dialog d = new Dialog(new Player(true,"Seppo", "player"),"testi", -1);
        assertEquals(false, d.dialogClickFinish());
    }
}
