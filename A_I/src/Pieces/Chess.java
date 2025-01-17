package Pieces;

import Tauler.Tauler;

/**
 *
 * @author Harpo Joan i Juan José Marí
 */
public class Chess {

    public static void main(String[] args) {
        new Chess().mPrin();
    }

    public void mPrin() {
        
        Tauler t = new Tauler(8);
        Piece k = new Knight();
        
        if (k.solveTour(t.getMatrix(), 4, 4)) {
            
            int[][] mat = t.getMatrix();
            
            for (int i = 0; i < t.getSize(); i++) {                
                for (int j = 0; j < t.getSize(); j++) {
                    
                    System.out.print(mat[i][j] + " ");
                }
                System.out.println("");
            }
        }else{
            System.out.println("No te solucio");
        }
    }
    
}
