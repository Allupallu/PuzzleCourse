package puzzlecourse.containers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import puzzlecourse.logic.TextFileLoader;

/**
 *
 * @author aleksi
 */
public class AbilityTest {
    
    private Ability ability;
    
    public AbilityTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ability = new Ability("Testi",6,5,4,3,2,1, AbilityEffects.REPLACE, 4, 5);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getName() {
        assertEquals("Testi",ability.getAbilityName());
    }
    @Test
    public void getReq() {
        assertEquals(4, ability.getRequirement(2));
    }
    @Test
    public void target() {
        assertEquals(4, ability.targetType());
    }
    @Test
    public void toType() {
        assertEquals(5, ability.changesToType());
    }
    @Test
    public void getEffect() {
        assertEquals(AbilityEffects.REPLACE, ability.getEffect());
    }
    
}
