package puzzlecourse.UI;

import java.util.Scanner;
import puzzlecourse.logic.GameRound;

/**
 * Väliaikinen tekstipohjainen käyttöliittymä.
 * @author aleksi
 */
public class TerminalUI {
    
    /**
     * Tulostaa pelilaudan tuonne tekstiulostuloon.
     * @param round Erä, jonka laudan se tulostaa.
     */
    public static void printBoard(GameRound round) {
        System.out.println("Lauta:");
        for (int i = 0; i < round.getBoardSize(); i++) {
            for (int j = 0; j < round.getBoardSize(); j++) {
                System.out.print(" "+ round.getTypeAt(i, j) +" ");
            } 
            System.out.println("");
        }
    }
    
    /**
     * Käynnistää tekstikäyttöliittymän laudalla. 
     * @param round lauta
     */
    public static void start(GameRound round) {
        
        System.out.println("Type 'q' to quit. 'a,b,c,d' to switch stuff, "
                + "where the letters are numbers in [0,8]. \n "
                + "The game doesn't check that further moves are possible after"
                + " a move yet, so you can play yourself into an impasse. \n"
                + "Type 'n' for a new board.");
        
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        String input;
        while(run) {
            printBoard(round);
            System.out.print("> ");
            input = scanner.nextLine() + "Banana split."; //hauskempi lyhyiden syöttöjen korjaus
            if (input.charAt(0)=='q') {
                run = false;
            } else if (input.charAt(0)=='n') {
                round.newBoard();
            }
            else {
                int a = input.charAt(0) -48;
                int b = input.charAt(2) -48;
                int c = input.charAt(4) -48;
                int d = input.charAt(6) -48;
                if (!round.switchPieces(a, b, c, d)) {
                    System.out.println("Illegal move.");
                }
            }
            
        }
    }
    
    public static void main(String[] args) {
        
        GameRound round = new GameRound();
        round.newBoard();
        
        TerminalUI.start(round);
        
    }
    
}
