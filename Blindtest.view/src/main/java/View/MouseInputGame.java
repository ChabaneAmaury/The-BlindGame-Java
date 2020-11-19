/*
 * @author Amaury Chabane
 */
package View;

import java.awt.event.MouseEvent;

/**
 * The Class MouseInputGame.
 *
 * @author Amaury Chabane
 */
public class MouseInputGame extends MouseInput {

    /**
     * Instantiates a new mouse input game.
     *
     * @param viewPanel the view panel
     */
    public MouseInputGame(ViewPanel viewPanel) {
        super(viewPanel);

    }

    /**
     * Mouse pressed.
     *
     * @param e the e
     */
    @SuppressWarnings("deprecation")
    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        /** The menu X. */
        int menuX = (int) (this.panel.getWidth() / 51.2);
        /** The btn Y. */
        int btnY = (int) (this.panel.getHeight() - (this.panel.getWidth() / 51.2)
                - (this.panel.getHeight() / 10.2857143));
        /** The btn W. */
        int btnW = this.panel.getWidth() / 12;
        /** The btn H. */
        int btnH = this.panel.getHeight() / 14;
        /** The pause X. */
        int pauseX = (int) (this.panel.getWidth() / 51.2) + menuX + btnW;

        if ((my >= btnY) && (my <= (btnY + btnH))) {
            if ((mx >= menuX) && (mx <= (menuX + btnW))) {
                this.viewframe.stopMusic();
                this.viewframe.getController().getTimer().stop();
                this.viewframe.setCurrentThemeIndex(9999);
                this.viewframe.setContentPane(new MenuPanel(this.viewframe, 0));
                this.viewframe.revalidate();
            } else if ((this.viewframe.getController().getTimeLeft() < 0) && (mx >= pauseX)
                    && (mx <= (pauseX + btnW))) {
                ((ViewPanel) this.panel).setPaused(!((ViewPanel) this.panel).isPaused());
            }
        }
    }

    /**
     * Mouse moved.
     *
     * @param e the e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        int btnY = (int) ((this.panel.getHeight() - (this.panel.getWidth() / 51.2)
                - (this.panel.getHeight() / 10.2857143)) - 200);
        if (e.getY() >= btnY) {
            this.panel.repaint(0, btnY, this.panel.getWidth(), this.panel.getHeight());
        }
    }
}
