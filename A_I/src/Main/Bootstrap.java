package Main;

import Controler.Controler;
import Tauler.Tauler;
import View.MainWindow;

/**
 *
 * @author Harpo Joan i Juan José Marí
 */
public class Bootstrap implements Runnable {
    @Override
    public void run() {
        Tauler model = new Tauler(8);
        MainWindow window = new MainWindow();
        Controler eventHandler = new Controler(window, model);
        
        window.attachActionLister(eventHandler);
        window.setVisible(true);
    }
}
