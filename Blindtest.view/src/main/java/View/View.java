/*
 *
 */
package View;

import Contract.IControllerMain;
import Contract.IModel;
import Contract.IView;

/**
 * The Class View.
 *
 * @author Amaury Chabane
 */
public final class View implements IView, Runnable {

    /** The view frame. */
    private final ViewFrame viewFrame;

    /**
     * Instantiates a new view.
     *
     * @param model
     *                  the model
     */
    public View(final IModel model) {
        this.viewFrame = new ViewFrame(model, "The BlindGame");
    }

    /**
     * Gets the view frame.
     *
     * @return the view frame
     */
    @Override
    public ViewFrame getViewFrame() {
        return this.viewFrame;
    }

    /**
     * Run.
     */
    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        this.getViewFrame().setVisible(true);
    }

    /**
     * Sets the controller.
     *
     * @param controller
     *                       the new controller
     */
    @Override
    public void setController(final IControllerMain controller) {
        this.getViewFrame().setController(controller);
    }

    /**
     * Gets the controller.
     *
     * @return the controller
     */
    public Object getController() {
        return null;
    }
}
