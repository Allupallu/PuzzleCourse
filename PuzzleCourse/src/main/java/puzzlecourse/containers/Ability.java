package puzzlecourse.containers;

/**
 * Luokka käytettävien kykyjen säilömiseen.
 * @author aleksi
 */
public class Ability {
    
    private final int[] requirements;
    
    private final AbilityEffects effect;
    private final String abilityName;
    
    private final int onType;
    private final int toType;
    
    /**
     * Luo kyvyn resurssivaatimuksineen ja efekteineen.
     * @param abilityName Kyvyn nimi.
     * @param type0req ColorPiece typeltä 0 vaatimus.
     * @param type1req ColorPiece typeltä 1 vaatimus.
     * @param type2req ColorPiece typeltä 2 vaatimus.
     * @param type3req ColorPiece typeltä 3 vaatimus.
     * @param type4req ColorPiece typeltä 4 vaatimus.
     * @param type5req ColorPiece typeltä 5 vaatimus.
     * @param effect mitä kyky tekee
     * @param onType mille kyky tekee
     * @param toType miksi kyky tekee, jos moinen kyky
     */
    public Ability(String abilityName,
                   int type0req,
                   int type1req,
                   int type2req,
                   int type3req,
                   int type4req,
                   int type5req,
                   AbilityEffects effect,
                   int onType,
                   int toType) {
        this.abilityName = abilityName;
        requirements = new int[6];
        requirements[0] = type0req;
        requirements[1] = type1req;
        requirements[2] = type2req;
        requirements[3] = type3req;
        requirements[4] = type4req;
        requirements[5] = type5req;
        this.effect = effect;
        this.onType = onType;
        this.toType = toType;
    }
    public String getAbilityName() {
        return abilityName;
    }
    public int getRequirement(int type) {
        return requirements[type];
    }
    public AbilityEffects getEffect() {
        return effect;
    }
    public int getOnType() {
        return onType;
    }
    public int getToType() {
        return toType;
    }
    
    
}
