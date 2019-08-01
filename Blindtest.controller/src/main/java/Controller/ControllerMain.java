/*
 *
 */
package Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import Contract.Difficulties;
import Contract.IControllerMain;
import Contract.IEntity;
import Contract.IModel;
import Contract.IView;

/**
 * The Class ControllerMain.
 *
 * @author Amaury Chabane
 */
public class ControllerMain extends Observable implements IControllerMain {

    /** The view. */
    private IView view;

    /** The model. */
    private IModel model;

    /** The allowed time. */
    private static int allowedTime = Difficulties.EASY;

    /** The time left. */
    private int timeLeft = ControllerMain.allowedTime;

    /** The theme. */
    private IEntity theme = null;

    /** The theme index. */
    private int themeIndex = 0;

    /** The not choosen types. */
    private ArrayList<String> notChoosenTypes = new ArrayList<>();

    /** The timer. */
    private Thread timer = null;

    /**
     * Instantiates a new controller main.
     *
     * @param view
     *                  the view
     * @param model
     *                  the model
     */
    public ControllerMain(IView view, IModel model) {
        this.setModel(model);
        this.setView(view);
    }

    /**
     * Removes the type.
     *
     * @param strType
     *                    the str type
     */
    @Override
    public void removeType(String strType) {
        for (String type : this.getModel().getTypes()) {
            if (type.toString().equals(strType.toUpperCase().replace(' ', '_'))) {
                this.getNotChoosenTypes().remove(type);
            }
        }
    }

    /**
     * Adds the type.
     *
     * @param strType
     *                    the str type
     */
    @Override
    public void addType(String strType) {
        for (String type : this.getModel().getTypes()) {
            if (type.toString().equals(strType.toUpperCase().replace(' ', '_'))) {
                this.getNotChoosenTypes().add(type);
            }
        }
    }

    /**
     * Start game.
     */
    @Override
    public void startGame() {
        ArrayList<IEntity> toRemove = new ArrayList<>();
        for (IEntity theme : this.getModel().getThemes()) {
            for (String type : this.notChoosenTypes) {
                if (theme.getType().equals(type)) {
                    toRemove.add(theme);
                }
            }
        }
        this.getModel().getThemes().removeAll(toRemove);
        Collections.shuffle(this.getModel().getThemes());

        this.timer = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < ControllerMain.this.getModel().getThemes().size(); i++) {
                    if (ControllerMain.this.getModel().getThemes().get(i).isHasError()) {
                        ControllerMain.this.getModel().getThemes()
                                .remove(ControllerMain.this.getModel().getThemes().get(i));
                    }
                }
                for (int i = 0; i < ControllerMain.this.getModel().getThemes().size(); i++) {
                    ControllerMain.this.setThemeIndex(i);
                    ControllerMain.this.setTheme(ControllerMain.this.getModel().getThemes().get(i));
                    ControllerMain.this.setTimeLeft(ControllerMain.allowedTime);
                    ControllerMain.this.setChanged();
                    ControllerMain.this.notifyObservers();
                    while (ControllerMain.this.getTimeLeft() >= -1) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        ControllerMain.this.setTimeLeft(ControllerMain.this.getTimeLeft() - 1);
                        ControllerMain.this.setChanged();
                        ControllerMain.this.notifyObservers();
                    }
                    while (ControllerMain.this.getTimeLeft() >= -6) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        ControllerMain.this.setTimeLeft(ControllerMain.this.getTimeLeft() - 1);
                        ControllerMain.this.setChanged();
                        ControllerMain.this.notifyObservers();
                    }
                }
            }
        };
        this.getTimer().setDaemon(true);
        this.getTimer().start();
    }

    /**
     * Gets the timer.
     *
     * @return the timer
     */
    @Override
    public Thread getTimer() {
        return this.timer;
    }

    /**
     * Sets the view.
     *
     * @param view
     *                 the new view
     */
    public void setView(IView view) {
        this.view = view;
    }

    /**
     * Sets the model.
     *
     * @param model
     *                  the new model
     */
    public void setModel(IModel model) {
        this.model = model;
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    @Override
    public IModel getModel() {
        // TODO Auto-generated method stub
        return this.model;
    }

    /**
     * Gets the view.
     *
     * @return the view
     */
    @Override
    public IView getView() {
        // TODO Auto-generated method stub
        return this.view;
    }

    /**
     * Gets the time left.
     *
     * @return the time left
     */
    @Override
    public int getTimeLeft() {
        // TODO Auto-generated method stub
        return this.timeLeft;
    }

    /**
     * Sets the time left.
     *
     * @param timeLeft
     *                     the new time left
     */
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     * Gets the observable.
     *
     * @return the observable
     */
    @Override
    public Observable getObservable() {
        // TODO Auto-generated method stub
        return this;
    }

    /**
     * Gets the theme.
     *
     * @return the theme
     */
    @Override
    public IEntity getTheme() {
        return this.theme;
    }

    /**
     * Sets the theme.
     *
     * @param theme
     *                  the new theme
     */
    @Override
    public void setTheme(IEntity theme) {
        this.theme = theme;
    }

    /**
     * Gets the theme index.
     *
     * @return the theme index
     */
    @Override
    public int getThemeIndex() {
        return this.themeIndex;
    }

    /**
     * Sets the theme index.
     *
     * @param themeIndex
     *                       the new theme index
     */
    public void setThemeIndex(int themeIndex) {
        this.themeIndex = themeIndex;
    }

    /**
     * Sets the allowed time.
     *
     * @param aLLOWED_TIME
     *                         the new allowed time
     */
    @Override
    public void setAllowedTime(int aLLOWED_TIME) {
        ControllerMain.allowedTime = aLLOWED_TIME;
    }

    @Override
    public ArrayList<String> getNotChoosenTypes() {
        return this.notChoosenTypes;
    }

    @Override
    public void setNotChoosenTypes(ArrayList<String> notChoosenTypes) {
        this.notChoosenTypes = notChoosenTypes;
    }
}