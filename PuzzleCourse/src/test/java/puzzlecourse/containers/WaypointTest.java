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
public class WaypointTest {
    
    private Waypoint w;
    
    public WaypointTest() {
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
    public void boardRefGot() {
        Waypoint w = new Waypoint(4,5,6,7,2);
        assertEquals(new Coordinate(4,5), w.getBoardRef());
    }
    @Test
    public void typeGot() {
        Waypoint w = new Waypoint(5,4,3,2,4);
        assertEquals(4, w.getType());
    }
    
    @Test 
    public void accelerationAcceleratesX() {
        Waypoint w = new Waypoint(0,0,1,1,1);
        Coordinate c1 = w.getNewCoord();
        Coordinate c2 = w.getNewCoord();
        Coordinate c3 = w.getNewCoord();
        int xDif1 = Math.abs(c1.getX() - c2.getX());
        int xDif2 = Math.abs(c2.getX() - c3.getX());
        assertEquals(true, xDif1 < xDif2);
    }
    @Test 
    public void accelerationAcceleratesY() {
        Waypoint w = new Waypoint(0,0,1,1,1);
        Coordinate c1 = w.getNewCoord();
        Coordinate c2 = w.getNewCoord();
        Coordinate c3 = w.getNewCoord();
        int yDif1 = Math.abs(c1.getY() - c2.getY());
        int yDif2 = Math.abs(c2.getY() - c3.getY());
        assertEquals(true, yDif1 < yDif2);
    }
    
    @Test
    public void aintDoneTillItIsDone() {
        Waypoint w = new Waypoint(0,0,1,1,1);
        assertEquals(false, w.isFinished());
    }
    
    // Seuraavat sisältää oletuksen, että 20 askelta riittää 
    // liikkumaan yhden palikan, mutta ei kymmentä.
    @Test
    public void aintDoneTillItIsDoneRedux() {
        Waypoint w = new Waypoint(0,0,1,10,1);
        for (int i = 0; i < 20; i++) w.getNewCoord();
        assertEquals(false, w.isFinished());
    }
    @Test
    public void aintDoneTillItIsDoneRedux2() {
        Waypoint w = new Waypoint(0,0,10,1,1);
        for (int i = 0; i < 20; i++) w.getNewCoord();
        assertEquals(false, w.isFinished());
    }
    @Test 
    public void isDoneWhenItIsDone() {
        Waypoint w = new Waypoint(0,0,1,0,1);
        for (int i = 0; i < 20; i++) w.getNewCoord();
        assertEquals(true, w.isFinished());
    }

    @Test
    public void movesStraightDown() {
        Waypoint w = new Waypoint(0,0,1,0,1);
        Coordinate c1 = w.getNewCoord();
        Coordinate c2 = w.getNewCoord();
        assertEquals(true, c1.getY() < c2.getY() && c1.getX() == c2.getX());
    }
    @Test
    public void movesStraightUp() {
        Waypoint w = new Waypoint(1,0,0,0,1);
        Coordinate c1 = w.getNewCoord();
        Coordinate c2 = w.getNewCoord();
        assertEquals(true, c1.getY() > c2.getY() && c1.getX() == c2.getX());
    }
    @Test
    public void movesStraightLeft() {
        Waypoint w = new Waypoint(0,1,0,0,1);
        Coordinate c1 = w.getNewCoord();
        Coordinate c2 = w.getNewCoord();
        assertEquals(true, c1.getY() == c2.getY() && c1.getX() > c2.getX());
    }
    @Test
    public void movesStraightRight() {
        Waypoint w = new Waypoint(0,0,0,1,1);
        Coordinate c1 = w.getNewCoord();
        Coordinate c2 = w.getNewCoord();
        assertEquals(true, c1.getY() == c2.getY() && c1.getX() < c2.getX());
    
    }
    
    @Test
    public void addedFallRaisesHigher() {
        Waypoint w = new Waypoint(0,0,1,0,1);
        int firstY = w.getNewCoord().getY();
        w.addBoardFall();
        
        assertEquals(true, firstY > w.getNewCoord().getY());
    }
    
    
    // Testataan automaattisesti luotua equals-metodia rivikattavuuden nimissä.
    @Test
    public void waypointDoesNotEqualJustAnybody() {
        assertEquals(false, new Waypoint(0,0,0,0,1) == new Waypoint(1,1,2,2,2));
    }
    @Test
    public void allWhiteChrisIsTheSameOrHowILearnedToJustCallEveryWaypointByTheirBoardRef() {
        assertEquals(new Waypoint(4,5,6,7,2), new Waypoint(4,5,1,2,4));
    }
    
    
}
