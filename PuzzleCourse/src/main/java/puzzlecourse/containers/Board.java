package puzzlecourse.containers;

import java.util.List;
import java.util.LinkedList;

/**
 * Pelilauta, jolla pelin "nappulat" sijaitsevat.
 * Ylläpitää taulukkoa pelin ColorPieceistä
 * oikeellisuutta.
 * @author aleksi
 */
public class Board {
    
    private static final int SIZE = 8;
    
    
    private final ColorPiece[][] board;
    
    
    public Board() {
        board = new ColorPiece[SIZE][SIZE];
    }
    
    public int getSize() {
        return board.length;
    }
    
    public ColorPiece getPiece(int y, int x) {
        return board[y][x];
    }
    
    
    /**
     * Uusi pelilauta.
     * Täyttää laudan uusilla ColorPieceilla.
     */
    public void newBoard() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                board[i][j] = new ColorPiece();
            }
        }
        checkNewBoard();
    }
    
    private void checkNewBoard() {
        boolean newBoardIsCool = false;
        while (!newBoardIsCool) {
            newBoardIsCool = true;
            for (int i = 0; i < getSize(); i++) {
                for (int j= 0; j < getSize(); j++) {
                    Coordinate c = new Coordinate(i,j);
                    if (startsLineOfThree(c,false) || startsLineOfThree(c,true)) {
                        board[i][j].changeType(-1);
                        newBoardIsCool = false;
                    }
                }
            }
            if (newBoardIsCool && !checkForPossibleMoves()) {
                newBoard(); //uus on hiano, mutta ei mahdollisia liikkeitä
                System.out.println("Lauta oli huono ja meni vaihtoon.");
            }
        }
    }
    
    private boolean startsLineOfThree(Coordinate c, boolean horizontal) {
        if (!isWithinBoundsEndGap(c,horizontal)) {
            return false;
        }
        
        return sameType3(c.getY()                    ,
                         c.getX()                    ,
                         c.getY()+t1f0(!horizontal)  ,
                         c.getX()+t1f0(horizontal)   ,
                         c.getY()+t1f0(!horizontal)*2,
                         c.getX()+t1f0(horizontal) *2);
    }
    
    private boolean sameType3(int y1, int x1, int y2, int x2, int y3, int x3) {
        int type = board[y1][x1].getType();
        return checkForType(y2,x2,type) && checkForType(y3,x3,type);
    }
    
    /**
     * Saatuaan y- ja x-koordinaatit etsii, onko sen paikan
     * ColorPiece osa jotain kolmen samantyyppisen jonoa ja palauttaa
     * listan koordinaatteja. Voi olla kuudella tavalla: eka, toka tai kolmas
     * vaaka tai pystysuunnassa. Testaa kaikki 6 tapaa.
     * @param y koordinaatti pystyakselilla
     * @param x koordinaatti vaaka-akselilla
     * @return Lista ainakin kolmen jonoihin kuuluvien koordinaateista.
     */
    public List findThreePiecesAt(int y, int x) {
        List<Coordinate> inThrees = new LinkedList();
        for (int i = -2; i < 1; i++) {
            boolean horizontal = true;
            for (int k = 0 ; k < 2; k++) {
                Coordinate c = new Coordinate(y + t1f0(!horizontal)*i,
                                              x + t1f0(horizontal)*i);
                if (startsLineOfThree(c, horizontal)) {
                    addLineOfThreeToList(inThrees, c , horizontal);
                }
                horizontal = false;
            }
        }
        return inThrees;
    }
    
    private List addLineOfThreeToList(List<Coordinate> list, Coordinate c, boolean horizontal) {
        for (int i = 0 ; i < 3 ; i++) {
            Coordinate d = new Coordinate(c.getY()+t1f0(!horizontal)*i,
                                          c.getX()+t1f0(horizontal)*i);
            if (!list.contains(d)) {
                list.add(d);
            }
        }
        return list;
    }
    
    private int t1f0(boolean bool) {
        return bool ? 1 : 0;
    }
    
    /**
     * Poistaa koordinaattien napit paikalta, pudottaa ylemmät ja luo 
     * huipulle uudet.
     * @param coords
     */
    public void destroyAt(List<Coordinate> coords) {
        boolean[][] destroArray = new boolean[getSize()][getSize()];
        for (Coordinate c : coords) {
            destroArray[c.getY()][c.getX()] = true;
        }
        for (int i = getSize() - 1 ;  i > 0 ; ) {
            boolean rowClear = true;
            for (int j = 0; j < getSize(); j++) {
                if (destroArray[i][j]) {
                    dropColumnAt(destroArray, i, j);
                    destroArray[0][j] = false;
                    dropColumnAt(board, i, j);
                    board[0][j] = new ColorPiece();
                    rowClear = false;
                }
            }
            if (rowClear) i--;
        }
    }
    
    private void dropColumnAt(boolean[][] table, int y, int x) {
        for (int i = y; i > 0; i--) {
            table[i][x] = table[i-1][x];
        }
    }
    private void dropColumnAt(Object[][] table, int y, int x) {
        for (int i = y; i > 0; i--) {
            table[i][x] = table[i-1][x];
        }
    }
    
    
    private boolean checkForPossibleMoves() {
        for (int i = 0; i < getSize(); i++) {
                for (int j= 0; j < getSize(); j++) {
                    if (isLinePossibleAt(new Coordinate(i,j),false) ||
                        isLinePossibleAt(new Coordinate(i,j),true)) {
                        return true;
                    }
                }
            }
        return false;
    }
    
    
    // Puuttuu ns xxox mahdollisuus, katsoo vain
    // xxo
    // abx
    private boolean isLinePossibleAt(Coordinate c, boolean horizontal) {
        if (!isWithinBoundsEndGap(c,horizontal)) {
            return false;
        }
        
        int type = board[c.getY()][c.getX()].getType();
        int inRow = 0;
        boolean nearRow = false;
        for (int i= 0; i < 3; i++) {
            if (checkForType(c.getY()+t1f0(!horizontal)*i,
                             c.getX()+t1f0(horizontal)*i ,
                             type                        )) {
                inRow++;
            } else {
                nearRow = (checkForType(c.getY()-t1f0(horizontal)+t1f0(!horizontal)*i,
                                        c.getX()-t1f0(!horizontal)+t1f0(horizontal)*i,
                                        type                                         )
                        || checkForType(c.getY()+t1f0(horizontal)+t1f0(!horizontal)*i,
                                        c.getX()+t1f0(!horizontal)+t1f0(horizontal)*i,
                                        type                                         ));
            }
        }
        return (inRow == 2 && nearRow);
    }
    
    private boolean isWithinBounds(Coordinate c) {
        return checkSize(c.getY()) && checkSize(c.getX());
    }
    private boolean isWithinBoundsEndGap(Coordinate c, boolean horizontal) {
        return isWithinBounds(c) && c.getTrueXfalseY(horizontal) < getSize()-2;
    }
    
    private boolean checkForType(int y, int x, int type) {
        if (!isWithinBounds(new Coordinate(y,x))) {
            return false;
        }
        return board[y][x].getType() == type;
    }
    
    
    /**
     * Nappuloiden paikanvaihto-operaatio.
     * Vaihtaa kahden pysty- tai vaakasuunnassa vierekkäisen ColorPiecen
     * paikat laudalla.
     * @param y1 Ensimmäisen ColorPiecen y-koordinaatti
     * @param x1 Ensimmäisen ColorPiecen x-koordinaatti
     * @param y2 Toisen ColorPiecen y-koordinaatti
     * @param x2 Toisen ColorPiecen x-koordinaatti
     * @return Onnistuiko siirto
     */
    public boolean switchPieces(int y1, int x1, int y2, int x2) {
        if ( ( y1 != y2 && x1 != x2 ) || ( y1 == y2 && x1 == x2  ) ||
             !isWithinBounds(new Coordinate(y1,x1)) ||
             !isWithinBounds(new Coordinate(y2,x2)) ||
             Math.abs(y1-y2) > 1 || Math.abs(x1-x2) > 1) { // 
            return false;
        }
        ColorPiece temp = board[y1][x1];
        board[y1][x1] = board[y2][x2];
        board[y2][x2] = temp;
        return true;
    }
    
    private boolean checkSize(int a) {
        return !(a < 0 || a >= getSize());
    }
}