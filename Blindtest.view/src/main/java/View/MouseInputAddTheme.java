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
        ViewFrame viewframe = this.getAddThemePanel().getViewFrame();

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
                    result = viewframe.getController().requestTMDbMovie("movie",
                            this.getAddThemePanel().getSearchField().getText());
                } else if (this.getAddThemePanel().getAddList().getSelectedItem() == "TV Show") {
                    result = viewframe.getController().requestTMDbMovie("tv",
                            this.getAddThemePanel().getSearchField().getText());
                }
                this.getAddThemePanel().setResultArr(result);
                this.getAddThemePanel().setType((String) this.getAddThemePanel().getAddList().getSelectedItem());
            }
        } else if ((my >= btnY) && (my <= (btnY + btnH))) {
            if ((mx >= backX) && (mx <= (backX + btnW))) {
                viewframe.setContentPane(new OptionsPanel(viewframe));
                viewframe.revalidate();
            }
        }

        else {
            if (this.getAddThemePanel().getResultArr() != null) {
                for (int i = 0; i < 3; i++) {
                    if (i < this.getAddThemePanel().getResultArr().length()) {

                        int tY = (int) (this.getAddThemePanel().getSearchField().getY()
                                + this.getAddThemePanel().getSearchField().getHeight()
                                + (((((i + 1) * this.getAddThemePanel().getWidth()) / 51.2)
                                        + ((((this.getAddThemePanel().getWidth() / 11) * 160) / 120) * i))));
                        int tX = (int) (this.getAddThemePanel().getWidth() / 51.2);
                        int tW = this.getAddThemePanel().getWidth() / 11;
                        int tH = (tW * 160) / 120;

                        if ((my >= tY) && (my <= (tY + tH))) {
                            if ((mx >= tX) && (mx <= (tX + tW))) {
                                viewframe.getController().createThemeFromSearch(
                                        this.getAddThemePanel().getResultArr().getJSONObject(i),
                                        this.getAddThemePanel().getType(), this.getAddThemePanel().getTitlesMap());
                                viewframe.getModel().fillThemesList();
                                viewframe.getModel().loadTypes();
                                viewframe.setContentPane(new MenuPanel(viewframe, 0));
                                viewframe.revalidate();
                            }
                        }

                    }
                }
                for (int i = 3; i < 6; i++) {
                    if (i < this.getAddThemePanel().getResultArr().length()) {

                        int tY = (int) (this.getAddThemePanel().getSearchField().getY()
                                + this.getAddThemePanel().getSearchField().getHeight()
                                + ((((i - 3) + 1) * this.getAddThemePanel().getWidth()) / 51.2)
                                + ((((this.getAddThemePanel().getWidth() / 11) * 160) / 120) * (i - 3)));
                        int tX = (int) (this.getAddThemePanel().getWidth() / 51.2)
                                + (this.getAddThemePanel().getWidth() / 2);
                        int tW = this.getAddThemePanel().getWidth() / 11;
                        int tH = (tW * 160) / 120;

                        if ((my >= tY) && (my <= (tY + tH))) {
                            if ((mx >= tX) && (mx <= (tX + tW))) {
                                viewframe.getController().createThemeFromSearch(
                                        this.getAddThemePanel().getResultArr().getJSONObject(i),
                                        this.getAddThemePanel().getType(), this.getAddThemePanel().getTitlesMap());
                                viewframe.getModel().fillThemesList();
                                viewframe.getModel().loadTypes();
                                viewframe.setContentPane(new MenuPanel(viewframe, 0));
                                viewframe.revalidate();
                            }
                        }
                    }
                }
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
