package Pieces;

/**
 *
 * @author Harpo Joan i Juan José Marí
 */
public class King extends Piece {

    public King() {
        moves = new Moves(8);
        
        moves.addMove(1, 0);
        moves.addMove(0, 1);
        moves.addMove(0, -1);
        moves.addMove(-1, 0);  
        moves.addMove(1, 1);
        moves.addMove(1, -1);
        moves.addMove(-1, 1);
        moves.addMove(-1, -1);
    }
}
