/*
 *
 */
package Contract;

import java.util.Observable;

/**
 * The Interface IControllerMain.
 *
 * @author Amaury Chabane
 */
public interface IControllerMain {

    /**
     * Gets the model.
     *
     * @return the model
     */
    IModel getModel();

    /**
     * Gets the view.
     *
     * @return the view
     */
    IView getView();

    /**
     * Gets the time left.
     *
     * @return the time left
     */
    int getTimeLeft();

    /**
     * Gets the observable.
     *
     * @return the observable
     */
    Observable getObservable();

    /**
     * Gets the theme.
     *
     * @return the theme
     */
    IEntity getTheme();

    /**
     * Sets the theme.
     *
     * @param theme
     *                  the new theme
     */
    void setTheme(IEntity theme);

    /**
     * Gets the theme index.
     *
     * @return the theme index
     */
    int getThemeIndex();

    /**
     * Start game.
     */
    void startGame();

    /**
     * Sets the allowed time.
     *
     * @param aLLOWED_TIME
     *                         the new allowed time
     */
    void setAllowedTime(int aLLOWED_TIME);

    /**
     * Removes the type.
     *
     * @param type
     *                 the type
     */
    void removeType(String type);

    /**
     * Adds the type.
     *
     * @param type
     *                 the type
     */
    void addType(String type);

    /**
     * Gets the timer.
     *
     * @return the timer
     */
    Thread getTimer();

}
