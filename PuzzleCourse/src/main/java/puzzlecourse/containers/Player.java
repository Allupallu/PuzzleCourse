package puzzlecourse.containers;


/**
 * Pelin pelaaja; ihminen tai ei.
 * @author aleksi
 */
public class Player {
    
    private String name;
    private String imageID;
    
    protected String[] dialogOptions;
    
    private final int[] collectedPieces;
    private final int[] victoryCondition;
    
    private final boolean humanPlayer;
    
    protected Move currentMove;
    
    private boolean turnsAbilityAvailable;
    
    private final Ability[] abilities;
    
    public Player(boolean human, String name, String imageID) {
        humanPlayer = human;
        this.name = name;
        this.imageID = imageID;
        dialogOptions = new String[] {"..."};
        turnsAbilityAvailable = true;
        
        collectedPieces = new int[6];
        victoryCondition = new int[6];
        
        abilities = new Ability[2];
    }
    
    public String getName() {
        return name;
    }
    public String getImageID() {
        return imageID;
    }
    public boolean isHuman() {
        return humanPlayer;
    }
    public int getCollected(int i) {
        return collectedPieces[i];
    }
    public boolean getAbilityAvailability() {
        return turnsAbilityAvailable;
    }
    public void setAbilityAvailable(boolean value) {
        turnsAbilityAvailable = value;
    }
    
    public void setAbility(int abilityNo, Ability ability) {
        abilities[abilityNo % abilities.length] = ability;
    }
    
    public void setVictoryConditionForType(int type, int required) {
        victoryCondition[type] = required;
    }
    
    public boolean isVictoryConditionMet() {
        for (int i = 0; i < victoryCondition.length; i++) {
            if (collectedPieces[i] < victoryCondition[i])
                return false;
        }
        return true;
    }
    
    /**
     * Palauttaa pelaajan käytettävän kyvyn tai sen puuttuessa
     * nopeasti kyhätyn whatever-kyvyn.
     * @param abilityNo kuinka mones pelaajan kyvyistä
     * @return kyky
     */
    public Ability getAbility(int abilityNo) {
        return abilities[abilityNo % abilities.length] != null ?
               abilities[abilityNo % abilities.length] :
               new Ability("Whatever",10,10,10,10,10,10,AbilityEffects.DESTROY, 0,0);
    }
    
    public boolean hasResourcesForAbility(int abilityNo) {
        for (int i = 0; i < collectedPieces.length; i++) {
            if (collectedPieces[i] < getAbility(abilityNo).getRequirement(i)) {
                return false;
            }
        }
        return true;
    }
    public void payAbilitysRequirements(int abilityNo) {
        for (int i = 0 ; i < collectedPieces.length; i++) {
            collectedPieces[i] -= getAbility(abilityNo).getRequirement(i);
        }
    }
    
    /**
     * Lisää kerättyihin palatyyppeihin uudet kerätyt.
     * @param collected tyyppien määrätaulukko
     */
    public void addToCollected(int[] collected) {
        for (int i = 0 ; i < collected.length; i++) {
            collectedPieces[i] += collected[i];
        }
    }
    
    /**
     * Liittää annetut koordinaatit osaksi pelaajan vuoron liikettä.
     * @param y laudan y-koordinaatti
     * @param x laudan x-koordinaatti
     * @return yritetäänkö siirtoa pelaajan liikkeellä
     */
    public boolean makeMove(int y, int x) {
        if (currentMove == null || currentMove.getComplete()) {
            currentMove = new Move(y,x);
            //System.out.println("Vaihto: " + y + ", " + x);
            return false;
        } else if (currentMove.addCoord(y,x)) {
          currentMove = null;
            //System.out.println("Vaihto kumottu.");
          return false;
        }
        return true;
    }
    
    public Move getCurrentMove() {
        return currentMove;
    }
    
    public int getCurrentMoveY() {
        if (currentMove == null) {
            return -1;
        }
        return currentMove.getY();
    }
    public int getCurrentMoveX() {
        if (currentMove == null) {
            return -1;
        }
        return currentMove.getX();
    }
    
    
}
