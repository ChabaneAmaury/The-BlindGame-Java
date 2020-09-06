/*
 * @author Amaury Chabane
 */
package View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JCheckBox;

import org.json.JSONArray;

/**
 * The Class MouseInputThemeProp.
 *
 * @author Amaury Chabane
 */
public class MouseInputAddTheme implements MouseListener, MouseMotionListener {

    /** The theme prop panel. */
    private AddThemePanel addThemePanel = null;

    /**
     * Instantiates a new mouse input theme prop.
     *
     * @param themePropPanel
     *                           the theme prop panel
     */
    public MouseInputAddTheme(AddThemePanel addThemePanel) {
        this.addThemePanel = addThemePanel;
    }

    /**
     * Mouse clicked.
     *
     * @param e
     *              the e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Mouse pressed.
     *
     * @param e
     *              the e
     */
    @Override
    public void mousePressed(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();

        int btnY = (int) (this.getAddThemePanel().getHeight() - (this.getAddThemePanel().getWidth() / 51.2)
                - (this.getAddThemePanel().getHeight() / 10.2857143));
        int btnW = this.getAddThemePanel().getWidth() / 12;
        int btnH = this.getAddThemePanel().getHeight() / 14;
        int searchX = (int) (this.getAddThemePanel().getWidth() / 1.74545455);
        int searchY = (int) (this.getAddThemePanel().getHeight() / 16.5);
        int nextX = (int) (this.getAddThemePanel().getWidth() - ((this.getAddThemePanel().getWidth() / 51.2) * 2)
                - (this.getAddThemePanel().getWidth() / 10) - btnW) + (btnW / 2);
        int previousX = (int) (this.getAddThemePanel().getWidth() - ((this.getAddThemePanel().getWidth() / 51.2) * 3)
                - (this.getAddThemePanel().getWidth() / 10) - btnW);
        int backX = (int) (this.getAddThemePanel().getWidth() - (this.getAddThemePanel().getWidth() / 51.2)
                - (this.getAddThemePanel().getWidth() / 10));

        if ((my >= searchY) && (my <= (searchY + btnH))) {
            if ((mx >= searchX) && (mx <= (searchX + btnW))) {
                JSONArray result = null;
                if (this.getAddThemePanel().getAddList().getSelectedItem() == "Movie") {
                    result = this.getAddThemePanel().getViewFrame().getController().requestTMDbMovie("movie",
                            this.getAddThemePanel().getSearchField().getText());
                } else if (this.getAddThemePanel().getAddList().getSelectedItem() == "TV Show") {
                    result = this.getAddThemePanel().getViewFrame().getController().requestTMDbMovie("tv",
                            this.getAddThemePanel().getSearchField().getText());
                }
                this.getAddThemePanel().setResultArr(result);
            }
        }
        if ((my >= btnY) && (my <= (btnY + btnH))) {
            if ((mx >= backX) && (mx <= (backX + btnW))) {
                this.getAddThemePanel().getViewFrame()
                        .setContentPane(new OptionsPanel(this.getAddThemePanel().getViewFrame()));
                this.getAddThemePanel().getViewFrame().revalidate();
            }
        }
    }

    /**
     * Gets the theme prop panel.
     *
     * @return the theme prop panel
     */
    private AddThemePanel getAddThemePanel() {
        return this.addThemePanel;
    }

    /**
     * Mouse released.
     *
     * @param e
     *              the e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Mouse entered.
     *
     * @param e
     *              the e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Mouse exited.
     *
     * @param e
     *              the e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    /**
     * Mouse dragged.
     *
     * @param e
     *              the e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Mouse moved.
     *
     * @param e
     *              the e
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
