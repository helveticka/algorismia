package Pieces;

/**
 *
 * @author Harpo Joan i Juan José Marí
 */
public class Rook extends Piece {

    public Rook() {
        moves = new Moves(30);

        moves.addMove(-7, 0);
        moves.addMove(0, -7);
        moves.addMove(-6, 0);
        moves.addMove(0, -6);
        moves.addMove(-5, 0);
        moves.addMove(0, -5);
        moves.addMove(-4, 0);
        moves.addMove(0, -4);
        moves.addMove(-3, 0);
        moves.addMove(0, -3);
        moves.addMove(-2, 0);
        moves.addMove(0, -2);
        moves.addMove(-1, 0);
        moves.addMove(0, -1);
        moves.addMove(0, 0);
        moves.addMove(0, 0);
        moves.addMove(1, 0);
        moves.addMove(0, 1);
        moves.addMove(2, 0);
        moves.addMove(0, 2);
        moves.addMove(3, 0);
        moves.addMove(0, 3);
        moves.addMove(4, 0);
        moves.addMove(0, 4);
        moves.addMove(5, 0);
        moves.addMove(0, 5);
        moves.addMove(6, 0);
        moves.addMove(0, 6);
        moves.addMove(7, 0);
        moves.addMove(0, 7);
    }
}
