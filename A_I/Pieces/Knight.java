package Pieces;

/**
 *
 * @author Harpo Joan i Juan José Marí
 */
public class Knight extends Piece {

    public Knight() {
        moves = new Moves(8);
        
        moves.addMove(2, 1);
        moves.addMove(2, -1);
        moves.addMove(1, 2);
        moves.addMove(1, -2);
        moves.addMove(-2, 1);
        moves.addMove(-2, -1);
        moves.addMove(-1, 2);
        moves.addMove(-1, -2);         

    }
}
