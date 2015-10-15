package puzzlecourse.containers;

/**
 * Värikäs palanen, joita on pelilaudalla monta.
 * @author aleksi
 */
public class ColorPiece {
    
    private static final int TYPES = 6;
    
    private int type;
    
    /**
     * Luo palasen ja valitsee sille semisattumanvaraisen värin/tyypin.
     */
    public ColorPiece() {
        changeType(-1);
    }
    
    /**
     * Vaihtaa tyypin annetun kokonaisluvun perusteella.
     * @param type tyyppi, johon muutetaan. random, jos -1
     */
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
