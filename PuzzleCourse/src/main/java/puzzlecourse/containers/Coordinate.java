package puzzlecourse.containers;

/**
 * Koordinaattisäilö.
 * Helppolukuinen ratkaisu joihinkin palautteisiin.
 * @author aleksi
 */
public class Coordinate {
    
    private int x, y;
    
    /**
     * Luo koordinaatin.
     * @param y y-koordinaatti
     * @param x x-koordinaatti
     */
    public Coordinate( int y , int x ) {
        this.y = y;
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }
    
    /**
     * Erikoinen getteri koodintoiston muissa luokissa vähentämiseksi.
     * @param horizontal Halutaanko x-arvo?
     * @return x-koordinaatti jos horizontal, y-koordinaatti muuten.
     */
    public int getTrueXfalseY(boolean horizontal) {
        return horizontal ? x : y ;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate comparison = (Coordinate) o;
        return this.y == comparison.getY() && this.x == comparison.getX();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        return hash;
    }
    
}
