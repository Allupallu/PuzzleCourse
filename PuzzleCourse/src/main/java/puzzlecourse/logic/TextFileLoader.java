package puzzlecourse.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import puzzlecourse.containers.Ability;
import puzzlecourse.containers.AbilityEffects;
import puzzlecourse.containers.Board;
import puzzlecourse.containers.Dialog;
import puzzlecourse.containers.Opponent;
import puzzlecourse.containers.Player;

/**
 * Lataa ja käsittelee tekstitiedostot.
 * @author aleksi
 */
public class TextFileLoader {
    
    private static final String FILE_PATH = "/text/";
    
    private static Scanner scanner;
    
    
    /**
     * Rakentaa dialogin tekstitiedostosta. Tiedostossa kukin lausahdus
     * omalle riville, alkaa "X: ", missä X on 0 jos pelaaja ja muuta, jos
     * vastustaja.
     * @param round erä, johon dialogi liittyy
     * @param filename dialogitiedoston nimi sans .txt
     * @return 
     */
    public static List<Dialog> getDialogFromFile(GameRound round, String filename) {
        if (!setScannerForFile(filename)) return new LinkedList<>();
        
        LinkedList<Dialog> list = new LinkedList<>();
        while (scanner.hasNextLine()) {
            list.add(buildDialogLine(round, scanner.nextLine()));
        }
        return list;
    }
    
    private static boolean setScannerForFile(String filename) {
        try {
            scanner = new Scanner(TextFileLoader.class.getResourceAsStream(FILE_PATH + filename + ".txt"));
            return true;
        } catch( Exception e) {
            System.out.println("Everything went wrong!");
            return false;
        }
    }
    
    private static Dialog buildDialogLine(GameRound round, String goesIn) {
        Player whoseLine;
        
        if (goesIn.charAt(0) == '0') whoseLine = round.getPlayer(0);
        else whoseLine = round.getPlayer(1);
        
        String comesOut;
        if (goesIn.length() > 2) {
            comesOut = goesIn.substring(2);
        } else comesOut = "Tiedostossa on lyhyt rivi.";
        
        return  new Dialog(whoseLine, comesOut, -1);
    }
    
    public static Player getPlayerFromFile(Board board, String filename) {
        if (!setScannerForFile(filename)) return new Player(true, "Error", "error");
        
        List<String> lines = new LinkedList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        
        return makePlayerFromStrings(board, lines);
    }
    
    private static Player makePlayerFromStrings(Board board, List<String> list) {
        boolean human = getHumanityFromList(list);
        String name = getNameFromList(list);
        String imageID = getImageIDFromList(list);
        Player player;
        
        if (human) player = new Player(true, name, imageID);
        else player = new Opponent(false, name, imageID, board);
        
        player.setAbility(0, getAbilityFromList(list, 0));
        player.setAbility(1, getAbilityFromList(list, 1));
        return player;
    }
    private static boolean getHumanityFromList(List<String> list) {
        return findLineFor(list, "Human: ").contains("yes");
    }
    private static String getNameFromList(List<String> list) {
        return findLineFor(list, "Name: ");
    }
    private static String getImageIDFromList(List<String> list) {
        return findLineFor(list, "ImageID: ");
    }
    
    private static Ability getAbilityFromList(List<String> list, int i) {
        String input = findLineFor(list, "Ability"+ (i+1) +": ");
        if (input.equals(findLineFor(list, "ERRORERROR"))) {
            return new Ability("Whatever",10,10,10,10,10,10,
                               AbilityEffects.DESTROY,0,0);
        }
        
        String name = input.substring(0, input.indexOf(';'));
        int[] reqs = parseAbilityReqs(input.substring(input.indexOf('(')));
        AbilityEffects effect = findEffect (input);
        int onType = parseTwoDigitInt('0', input.charAt(input.length()-2));
        int toType = parseTwoDigitInt('0', input.charAt(input.length()-1));
        
        return new Ability(name, reqs[0],reqs[1],reqs[2],
                                 reqs[3],reqs[4],reqs[5],
                                 effect, onType, toType);
    }
    private static int[] parseAbilityReqs(String input) {
        int[] table = new int[6];
        int j = 1;
        for (int i = 0; i < table.length; i++) {
            
            table[i] = parseTwoDigitInt(input.charAt(j),
                                        input.charAt(j+1));
            j += 3;
            
        }
        return table;
    }
    private static int parseTwoDigitInt(char tens, char digits) {
        return (tens-48)*10 + (digits-48);
    }
    private static AbilityEffects findEffect(String input) {
        if (input.contains("REPLACE")) return AbilityEffects.REPLACE;
        else return AbilityEffects.DESTROY;
    }
    
    private static String findLineFor(List<String> list, String id) {
        for (String s : list) {
            if (s.startsWith(id)) {
                return s.substring(id.length());
            }
        }
        return "NotFoundInFile";
    }
    
    
}
