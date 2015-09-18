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
public class ImageLoaderTest {
    
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
    
}
