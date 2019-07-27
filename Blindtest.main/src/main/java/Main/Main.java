/*
 *
 */
package Main;

import Controller.ControllerMain;
import Model.Model;
import View.View;

/**
 * The Class Main.
 *
 * @author Amaury Chabane
 */
public class Main {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {

        Model model = new Model();
        model.loadFolders();
        View view = new View(model);
        ControllerMain controller1 = new ControllerMain(view, model);
        view.setController(controller1);
    }

}
