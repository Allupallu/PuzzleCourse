package puzzlecourse.containers;

/**
 * Dialogi vitsailuun ja efekteissä säästöön.
 * @author aleksi
 */
public class Dialog {
    
    private final Player dialogPlayer;
    private final String dialogText;
    private final int dialogTextSize;
   
    private int lastsForXFrames;
    
    private int charsPrinted;
    
    
    /**
     * Luo dialogin pelaajalle.
     * @param player pelaaja, joka tekstin puhuu
     * @param dialogText teksti, jonka pelaaja "puhuu"
     * @param lastsForXFrames kesto ruudunpäivityksissä, -1 tarkoittaa ajastariippumatonta
     */
    public Dialog(Player player, String dialogText, int lastsForXFrames) {
        dialogPlayer = player;
        this.dialogText = dialogText;
        dialogTextSize = dialogText.length();
        this.lastsForXFrames = lastsForXFrames;
        charsPrinted = 0;
    }
    
    public Player getPlayer() {
        return dialogPlayer;
    }
    
    public String getText() {
        if (lastsForXFrames > 0) lastsForXFrames--;
        
        if (charsPrinted < dialogTextSize) {
            charsPrinted++;
            return dialogText.substring(0, charsPrinted);
        } else {
            return dialogText;
        }
    }
    
    public boolean dialogTimedFinish() {
        return lastsForXFrames == 0;
    }
    
    public boolean dialogClickFinish() {
        return charsPrinted == dialogTextSize;
    }
    
}
