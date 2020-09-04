/*
 * @author Amaury Chabane
 */
package Contract;

/**
 * The Interface IView.
 *
 * @author Amaury Chabane
 */
public interface IView {

    /**
     * Sets the controller.
     *
     * @param controller the new controller
     */
    void setController(IControllerMain controller);

    /**
     * Gets the view frame.
     *
     * @return the view frame
     */
    Object getViewFrame();

}
