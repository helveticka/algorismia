package Controler;

import Pieces.*;
import View.MainWindow;
/**
 *
 * @author Harpo Joan i Juan José Marí
 * 
 * Clase encarregada de dur a terme el la seleccio de peça i cridada a la funció de backtacking
 */
public class BackTracker {
    //atributos de clase
    private Piece p;
    private final String Knight = "Knight";
    private final String Pawn = "Pawn";
    private final String Rook = "Rook";
    private final String Bishop = "Bishop";
    private final String King = "King";
    private final String Queen = "Queen";
    private final String piece ;    
    
    public BackTracker(MainWindow ventana, String s){
        piece = s;
    }
    /**
     * Métode que du a terme la cridada de resolució del tur de les peçes
     * @param t array de tauler
     * @param i fila
     * @param j columa
     * @return si s'ha trobat solució al problema
     */
    public boolean result(int[][] t, int i, int j){
        
        switch(piece){            
            case Knight -> p = new Knight();
            case Pawn -> p=new Pawn();
            case Rook -> p=new Rook();
            case Bishop -> p=new Bishop();
            case King -> p=new King();
            case Queen -> p=new Queen();
        }
        return p.solveTour(t,i,j);
    }   
}
