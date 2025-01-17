package Pieces;
/**
 *
 * @author Harpo Joan i Juan José Marí
 */
public class Moves {
    
    /*
    Atributs de clase per enmaggatzemar el moviment de la peça
    i un controlador de nombre de moviments enmagatzemats
    */
    private final int [][] moves;
    private int numMove;
    
    /**
     * Constructor per inicialitzar l'array amb un tamany
     * @param size 
     */
    public Moves(int size){
        moves = new int[size][2];
        numMove = 0;
    }
    
    /**
     * Mètode per afegir un moviment a l'array
     * @param x movient a l'eix X
     * @param y  moviment a l'eix Y
     */
    public void addMove(int x, int y){
        moves[numMove][0] = x;
        moves[numMove][1] = y;
        numMove++;
    }
    
    //GETTERS    
    public int getX(int i){
        return moves[i][0];
    }
    
    public int getY(int i){
        return moves[i][1];
    }
    
    public int length(){
        return numMove;
    }
}
