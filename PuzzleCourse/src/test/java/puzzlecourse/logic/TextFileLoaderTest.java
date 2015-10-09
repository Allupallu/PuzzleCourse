package puzzlecourse.logic;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import puzzlecourse.containers.AbilityEffects;
import puzzlecourse.containers.Board;
import puzzlecourse.containers.Dialog;
import puzzlecourse.containers.Player;

/**
 *
 * @author aleksi
 */
public class TextFileLoaderTest {
    
    public TextFileLoaderTest() {
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
    public void badDialogFileReturnsEmptyDialogList() {
        List<Dialog> d = TextFileLoader.getDialogFromFile(new GameRound(), "badfile");      
        assertEquals(true, d.isEmpty() );
    }
    
    @Test
    public void badPlayerFileReturnsErrorPlayer() {
        Player p = TextFileLoader.getPlayerFromFile(new Board(), "badfile");
        assertEquals("Error", p.getName());
    }
    
    @Test
    public void introductionDialogNotEmpty() {
        List<Dialog> d = TextFileLoader.getDialogFromFile(new GameRound(), "introduction");
        assertEquals(false, d.isEmpty());
    }
    
    @Test // Rajoittaa alkudialogin muotoa mutta RIVIKATTAVUUS BEIBI!
    public void introductionDialogStartsWithOpponent() {
        List<Dialog> d = TextFileLoader.getDialogFromFile(new GameRound(), "introduction");
        assertEquals(d.get(0).getPlayer().isHuman(), false);
    }
    @Test // Rajoittaa alkudialogin muotoa vielä lisää
    public void introductionDialogContinuesWithPlayer() {
        List<Dialog> d = TextFileLoader.getDialogFromFile(new GameRound(), "introduction");
        assertEquals(d.get(1).getPlayer().isHuman(), true);
    }
    
    @Test
    public void badPlayerAbility1IsWhatever() {
        Player p = TextFileLoader.getPlayerFromFile(new Board(), "badfile");     
        assertEquals("Whatever" , p.getAbility(0).getAbilityName() );
    }
    @Test
    public void badPlayerAbility2IsWhatever() {
        Player p = TextFileLoader.getPlayerFromFile(new Board(), "badfile");     
        assertEquals("Whatever" , p.getAbility(1).getAbilityName() );
    }
    @Test
    public void goodPlayerAbility1IsNotWhatever() {
        Player p = TextFileLoader.getPlayerFromFile(new Board(), "player");
        assertEquals(false, p.getAbility(0).getAbilityName().equals("Whatever"));
    }
    @Test
    public void goodPlayerAbility1RequirementsNotZero() {
        Player p = TextFileLoader.getPlayerFromFile(new Board(), "player");
        assertEquals(false, p.hasResourcesForAbility(0));     
    }
    @Test
    public void goodPlayerAbility1RequirementsNotWayHight() {
        Player p = TextFileLoader.getPlayerFromFile(new Board(), "player");
        p.addToCollected( new int[] {100,100,100,100,100,100});
        assertEquals(true, p.hasResourcesForAbility(0));     
    }
    @Test
    public void badPlayerAbilityEffectIsDestroy() {
        Player p = TextFileLoader.getPlayerFromFile(new Board(), "badfile");
        assertEquals(AbilityEffects.DESTROY, p.getAbility(0).getEffect());
    }
    
}
