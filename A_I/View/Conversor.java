package View;
/**
 *
 * @author Harpo Joan i Juan José Marí
 * 
 * Classe que converteix un array d'enters a elements de la clase CellDrawable, preparada per pintar
 */
public class Conversor {

    public Conversor() {
    }

    public static CellDrawable[][] convertir(int[][] t) {        
        int n = t.length;        
        
        CellDrawable tauler[][] = new CellDrawable[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tauler[i][j] = new IntegerCellDrawable(t[i][j]);
            }
        }
        return tauler;
    }
}
