package Controler;


import View.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;
import Tauler.Tauler;
import View.Conversor;
import java.awt.HeadlessException;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author Harpo Joan i Juan José Marí
 * 
 * Classe que du a terme el control de la finestra principal
 */
public class Controler  implements ActionListener, ChangeListener{
    private final MainWindow view;
    private final Tauler model;

    public Controler(MainWindow view, Tauler model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String accio = e.getActionCommand();
            switch (accio) {
                case "compute" -> {
                    //Obtenim la posició inicial
                    int filaInicial = view.getValorSpFila();
                    int columnaInicial = view.getValorSpColumna();
                    //Construim un objecte backtracker
                    BackTracker backtracker = new BackTracker(view,view.getValorCbPiece());
                    //Cream un nou fil per poder actualitzar la finestra en temps real
                    
                    Thread fil = new Thread(() -> {
                        //Desactivam els botons per a que no es puguin manipular durant l'execució
                        view.desactivarBotones();
                        //Si hi ha solució, accedim a la solucio que l'objecte backtracker ha generat                        
                        if (backtracker.result(model.getMatrix(), filaInicial, columnaInicial)) {
                            
                            view.colocaPeces(Conversor.convertir(model.getMatrix()));
                        } else {
                            JOptionPane.showMessageDialog(null, "No s'ha trobat solució", "Paràmetre invalid", JOptionPane.ERROR_MESSAGE);
                        }
                        //Reactivam els botons
                        view.activarBotones();
                    });
                    fil.start();
                }

                default -> throw new badSelection();
            }
           
        } catch (badSelection ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage()); 
        }catch (HeadlessException Ex){
            
        }
    }    

    @Override
    public void stateChanged(ChangeEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    public class badSelection extends Exception {
    }
}


