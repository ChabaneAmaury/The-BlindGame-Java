/*
 *
 */
package View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * The Class MouseInputGame.
 *
 * @author Amaury Chabane
 */
public class MouseInputGame implements MouseListener, MouseMotionListener {

    /** The view panel. */
    private ViewPanel viewPanel = null;

    /**
     * Instantiates a new mouse input game.
     *
     * @param viewPanel
     *                      the view panel
     */
    public MouseInputGame(ViewPanel viewPanel) {
        this.viewPanel = viewPanel;
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
    @SuppressWarnings("deprecation")
    @Override
    public void mousePressed(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();

        int quitX = (int) (this.getViewPanel().getWidth() / 51.2);
        int btnY = (int) (this.getViewPanel().getHeight() - (this.getViewPanel().getWidth() / 51.2)
                - (this.getViewPanel().getHeight() / 10.2857143));
        int btnW = this.getViewPanel().getWidth() / 10;
        int btnH = this.getViewPanel().getHeight() / 12;
        int menuX = (int) (this.getViewPanel().getWidth() / 51.2) + quitX + btnW;
        if ((my >= btnY) && (my <= (btnY + btnH))) {
            if ((mx >= quitX) && (mx <= (quitX + btnW))) {
                System.exit(0);
            } else if ((mx >= menuX) && (mx <= (menuX + btnW))) {
                this.getViewPanel().getViewFrame().stopMusic();
                this.getViewPanel().getViewFrame().getController().getTimer().stop();
                this.getViewPanel().getViewFrame().setCurrentThemeIndex(9999);
                this.getViewPanel().getViewFrame().getModel().loadFolders();
                this.getViewPanel().getViewFrame().getModel().fillThemesList();
                this.getViewPanel().getViewFrame().setContentPane(new MenuPanel(this.getViewPanel().getViewFrame()));
                this.getViewPanel().getViewFrame().revalidate();
            }
        }
    }

    /**
     * Gets the view panel.
     *
     * @return the view panel
     */
    private ViewPanel getViewPanel() {
        return this.viewPanel;
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
        int btnY = (int) ((this.getViewPanel().getHeight() - (this.getViewPanel().getWidth() / 51.2)
                - (this.getViewPanel().getHeight() / 10.2857143)) - 200);
        if (e.getY() >= btnY) {
            this.getViewPanel().repaint(0, btnY, this.getViewPanel().getWidth(), this.getViewPanel().getHeight());
        }
    }
}
