package puzzlecourse.containers;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author aleksi
 */
public class ColorPiece {
    
    private static final int TYPES = 6; 
    
    
    private int type;
    
    public ColorPiece() {
        changeType(-1);
    }
    
    public void changeType(int type) {
        if (type == -1) {
            type = (int) (Math.random() * TYPES);
        }
        this.type = type;
    }
    
    public int getType() {
        return type;
    }
    
}
