package Pieces;

import java.util.Arrays;
import java.util.Stack;

/**
 * Classe pare per enmaggatzemar els metodes de les peçes
 * @author Harpo Joan i Juan José Marí
 */
public abstract class Piece {

    //Atribut de clase que heredaran els fills
    //on es guarden els posibles moviments de la figura
    protected Moves moves;

    public Piece() {

    }

    /**
     * Mètode que comprova la validesa d'un moviment
     *
     * @param t tauler
     * @param x fila
     * @param y columna
     * @return vertader si es una posició valida, false si no
     */
    private boolean isValid(int[][] t, int x, int y) {
        int n = t.length;
        return x >= 0 && x < n && y >= 0 && y < n && t[x][y] == 0;
    }

    /**
     * Mètode recursiu de resolució del problema del tour de les peçes
     *
     * @param t tauler
     * @param x fila
     * @param y columna
     * @param k comptador de moviments
     * @return vertader si s'ha conseguit completar el tour, false si no
     */
    private boolean pieceTour(int[][] t, int x, int y, int k) {
        //Bucle de iteracions maximes per a un mateix node
        for (int i = 0; i < moves.length(); i++) {

            //indexos de les noves posicions al tauler
            int newX = x + moves.getX(i);
            int newY = y + moves.getY(i);

            if (isValid(t, newX, newY)) {
                //actualitzam el tauler amb el comptador a la nova posició
                t[newX][newY] = k;
                //Cas Básic
                if (k == (t.length * t.length)) {
                    return true;
                } //Cridada recusiva
                if (pieceTour(t, newX, newY, k + 1)) {
                    return true;
                } //En cas de que no es pugui seguir, recuperam l'estat base
                t[newX][newY] = 0;
            }
        }
        return false;
    }

    /**
     * Mètode iteratiu de resolució del problema del tour de les peçes
     *
     * @param t tauler
     * @param x fila
     * @param y columna
     * @return vertader si s'ha conseguit completar el tour, false si no
     */
    private boolean iterativePieceTour(int[][] t, int x, int y) {
        Stack<int[]> stack = new Stack<>();
        int k = 1;
        stack.push(new int[]{x, y, k});

        while (!stack.isEmpty()) {
            // Treim la darrera posició de la pila
            int[] pos = stack.pop();
            x = pos[0];
            y = pos[1];
            k = pos[2];

            t[x][y] = k;
            // métode que reinicia els estats
           restartState(t,x,y,k);

            // Si s'ha resolt el problema retornam true
            if (t[x][y] == t.length * t.length) {
                return true;
            }
            // Se proven tots es moviments valids
            for (int i = 0; i < moves.length(); i++) {
                int newX = x + moves.getX(i);
                int newY = y + moves.getY(i);
                // Si la nova posició es valida, la empilam
                if (isValid(t, newX, newY)) {
                    stack.push(new int[]{newX, newY, k + 1});
                }
            }
        }
        // si no te solució retornam false
        return false;
    }
    /**
     * Mètode per reiniciar els estats empleat a iterativePieceTour
     * 
     * @param t tauler
     * @param x fila
     * @param y columna
     * @param k numero de iteració
     */
    private void restartState(int[][] t, int x, int y, int k) {
        
        for (int f = 0, c = 0; f < t.length ; c = ++c % t.length) {

            if (t[f][c] == k) {
                int[] c1 = {f, c};
                int[] c2 = {x, y};
                if (!Arrays.equals(c1, c2)) {
                    t[f][c] = 0;
                }
            }
            if (t[f][c] > k) {
                t[f][c] = 0;
            }
            if (c == 7) {
                f++;
            }
        }
    }

    /**
     * Mètode cridat pel main per solucionar el problema
     *
     * @param t tauler
     * @param x fila
     * @param y columna
     * @return true si s'ha aconseguit solucionar el tour, false si no
     */
    public boolean solveTour(int[][] t, int x, int y) {
        t[x][y] = 1;
        return iterativePieceTour(t, x, y);
    }
}
