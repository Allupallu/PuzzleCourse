package puzzlecourse.logic;

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
public class ImageLoaderTest {
    
    @Rule
    public Timeout globalTimeout= new Timeout(1000);
    
    
    public ImageLoaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ImageLoader.loadImages();      
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void loadImagesTrue() {
        assertEquals(true, ImageLoader.loadImages());
    }
    
    @Test
    public void image1Loads() {
        assertEquals(false, ImageLoader.getImage(0) == null);
    }
    
    @Test
    public void image2Loads() {
        assertEquals(false, ImageLoader.getImage(1) == null);
    }
    
    @Test
    public void loadPlayer() {
        assertEquals(false, ImageLoader.loadImage("player") == null);
    }
    @Test
    public void noLoadingVoid() {
        assertEquals(null, ImageLoader.loadImage(""));
    }
    
    @Test
    public void noNegativeGet() {
        assertEquals(null, ImageLoader.getImage(-1));
    }
    @Test
    public void noHundredGet() {
        assertEquals(null, ImageLoader.getImage(100));
    }
    
}
