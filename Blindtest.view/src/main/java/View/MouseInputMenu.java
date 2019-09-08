/*
 *
 */
package View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * The Class MouseInputMenu.
 *
 * @author Amaury Chabane
 */
public class MouseInputMenu implements MouseListener, MouseMotionListener {

    /** The menu panel. */
    private MenuPanel menuPanel = null;

    /**
     * Instantiates a new mouse input menu.
     *
     * @param menuPanel2
     *                       the menu panel 2
     */
    public MouseInputMenu(MenuPanel menuPanel2) {
        this.menuPanel = menuPanel2;
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
        int playX = (int) (this.getMenuPanel().getWidth() - (this.getMenuPanel().getWidth() / 51.2)
                - (this.getMenuPanel().getWidth() / 10));
        int btnY = (int) (this.getMenuPanel().getHeight() - (this.getMenuPanel().getWidth() / 51.2)
                - (this.getMenuPanel().getHeight() / 10.2857143));
        int btnW = this.getMenuPanel().getWidth() / 10;
        int btnH = this.getMenuPanel().getHeight() / 12;
        int optionsX = (int) (this.getMenuPanel().getWidth() - (this.getMenuPanel().getWidth() / 51.2)
                - (this.getMenuPanel().getWidth() / 10) - (this.getMenuPanel().getWidth() / 51.2) - btnW);
        int nextX = (int) (this.getMenuPanel().getWidth() - (this.getMenuPanel().getWidth() / 51.2)
                - (this.getMenuPanel().getWidth() / 10) - ((this.getMenuPanel().getWidth() / 51.2) * 2) - (btnW * 2))
                + (btnW / 2);
        int previousX = (int) (this.getMenuPanel().getWidth() - (this.getMenuPanel().getWidth() / 51.2)
                - (this.getMenuPanel().getWidth() / 10) - ((this.getMenuPanel().getWidth() / 51.2) * 3) - (btnW * 2));
        int refreshX = (int) (this.getMenuPanel().getWidth() - (this.getMenuPanel().getWidth() / 51.2)
                - (this.getMenuPanel().getWidth() / 10) - ((this.getMenuPanel().getWidth() / 51.2) * 4) - (btnW * 3));
        int quitX = (int) (this.getMenuPanel().getWidth() / 51.2);
        if ((my >= btnY) && (my <= (btnY + btnH))) {
            if ((mx >= previousX) && (mx <= (previousX + (btnW / 2)))) {
                if (this.getMenuPanel().getShowIndex() > 0) {
                    this.getMenuPanel().setShowIndex(this.getMenuPanel().getShowIndex() - 6);
                    this.getMenuPanel().repaint();
                }
            } else if ((mx >= nextX) && (mx <= (nextX + (btnW / 2)))) {
                if (this.getMenuPanel().getViewFrame().getController().getModel().getThemes()
                        .size() > ((this.getMenuPanel().getShowIndex() + 6))) {
                    this.getMenuPanel().setShowIndex(this.getMenuPanel().getShowIndex() + 6);
                    this.getMenuPanel().repaint();
                }
            } else if ((mx >= optionsX) && (mx <= (optionsX + btnW))) {
                this.getMenuPanel().getViewFrame().setContentPane(new OptionsPanel(this.getMenuPanel().getViewFrame()));
                this.getMenuPanel().getViewFrame().revalidate();
            } else if ((mx >= playX) && (mx <= (playX + btnW))) {
                this.getMenuPanel().getViewFrame().getController().startGame();
            } else if ((mx >= refreshX) && (mx <= (refreshX + btnW))) {
                this.getMenuPanel().getViewFrame().getModel().loadFolders();
                this.getMenuPanel().getViewFrame().getModel().fillThemesList();
                this.getMenuPanel().repaint();
            } else if ((mx >= quitX) && (mx <= (quitX + btnW))) {
                System.exit(0);

            }
        } else {
            for (int i = this.getMenuPanel().getShowIndex(); i < (this.getMenuPanel().getShowIndex() + 3); i++) {
                if (i < this.getMenuPanel().getViewFrame().getModel().getThemes().size()) {
                    int tY = (int) (((((i - this.getMenuPanel().getShowIndex()) + 1) * this.getMenuPanel().getWidth())
                            / 51.2)
                            + ((((this.getMenuPanel().getWidth() / 11) * 160) / 120)
                                    * (i - this.getMenuPanel().getShowIndex())));
                    int tX = (int) (this.getMenuPanel().getWidth() / 51.2);
                    int tW = this.getMenuPanel().getWidth() / 11;
                    int tH = (tW * 160) / 120;
                    if ((my >= tY) && (my <= (tY + tH))) {
                        if ((mx >= tX) && (mx <= (tX + tW))) {
                            this.getMenuPanel().getViewFrame()
                                    .setContentPane(new ThemePropPanel(this.getMenuPanel().getViewFrame(), i));
                            this.getMenuPanel().getViewFrame().revalidate();
                            break;
                        }
                    }
                }
            }
            for (int i = this.getMenuPanel().getShowIndex() + 3; i < (this.getMenuPanel().getShowIndex() + 6); i++) {
                if (i < this.getMenuPanel().getViewFrame().getModel().getThemes().size()) {
                    int tY = ((int) (((((i - 3 - this.getMenuPanel().getShowIndex()) + 1)
                            * this.getMenuPanel().getWidth()) / 51.2))
                            + ((((this.getMenuPanel().getWidth() / 11) * 160) / 120)
                                    * (i - 3 - this.getMenuPanel().getShowIndex())));
                    int tX = (int) (this.getMenuPanel().getWidth() / 51.2) + (this.getMenuPanel().getWidth() / 2);
                    int tW = this.getMenuPanel().getWidth() / 11;
                    int tH = (tW * 160) / 120;
                    if ((my >= tY) && (my <= (tY + tH))) {
                        if ((mx >= tX) && (mx <= (tX + tW))) {
                            this.getMenuPanel().getViewFrame()
                                    .setContentPane(new ThemePropPanel(this.getMenuPanel().getViewFrame(), i));
                            this.getMenuPanel().getViewFrame().revalidate();
                            break;
                        }
                    }
                }
            }
        }

    }

    /**
     * Gets the menu panel.
     *
     * @return the menu panel
     */
    private MenuPanel getMenuPanel() {
        return this.menuPanel;
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

    }

    /**
     * Mouse moved.
     *
     * @param e
     *              the e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        this.getMenuPanel().repaint();
    }
}
