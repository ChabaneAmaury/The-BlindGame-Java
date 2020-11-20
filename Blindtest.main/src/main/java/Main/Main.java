/*
 * @author Amaury Chabane
 */
package Main;

import Contract.IModel;
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
     * @param args
     *                 the arguments
     */
    public static void main(String[] args) {

        IModel IModel = new Model();
        View view = new View(IModel);
        ControllerMain controller = new ControllerMain(view, IModel);
        view.setController(controller);
    }

}
