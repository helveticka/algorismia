package Pieces;

/**
 *
 * @author Harpo Joan i Juan José Marí
 */
public class Bishop extends Piece {

    public Bishop() {
        moves = new Moves(60);

        moves.addMove(-7, 7);
        moves.addMove(7, -7);
        moves.addMove(7, 7);
        moves.addMove(-7, -7);
        moves.addMove(-6, 6);
        moves.addMove(6, -6);
        moves.addMove(6, 6);
        moves.addMove(-6, -6);
        moves.addMove(-5, 5);
        moves.addMove(5, -5);
        moves.addMove(5, 5);
        moves.addMove(-5, -5);
        moves.addMove(-4, 4);
        moves.addMove(4, -4);
        moves.addMove(4, 4);
        moves.addMove(-4, -4);
        moves.addMove(-3, 3);
        moves.addMove(3, -3);
        moves.addMove(3, 3);
        moves.addMove(-3, -3);
        moves.addMove(-2, 2);
        moves.addMove(2, -2);
        moves.addMove(2, 2);
        moves.addMove(-2, -2);
        moves.addMove(-1, 1);
        moves.addMove(1, -1);
        moves.addMove(1, 1);
        moves.addMove(-1, -1);
        moves.addMove(0, 0);
        moves.addMove(0, 0);
        moves.addMove(0, 0);
        moves.addMove(0, 0);
        moves.addMove(1, -1);
        moves.addMove(-1, 1);
        moves.addMove(-1, -1);
        moves.addMove(1, 1);
        moves.addMove(2, -2);
        moves.addMove(-2, 2);
        moves.addMove(-2, -2);
        moves.addMove(2, 2);
        moves.addMove(3, -3);
        moves.addMove(-3, 3);
        moves.addMove(-3, -3);
        moves.addMove(3, 3);
        moves.addMove(4, -4);
        moves.addMove(-4, 4);
        moves.addMove(-4, -4);
        moves.addMove(4, 4);
        moves.addMove(5, -5);
        moves.addMove(-5, 5);
        moves.addMove(-5, -5);
        moves.addMove(5, 5);
        moves.addMove(6, -6);
        moves.addMove(-6, 6);
        moves.addMove(-6, -6);
        moves.addMove(6, 6);
        moves.addMove(7, -7);
        moves.addMove(-7, 7);
        moves.addMove(-7, -7);
        moves.addMove(7, 7);
    }
}
