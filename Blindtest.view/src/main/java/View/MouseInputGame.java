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

    /** The menu X. */
    private int menuX;
    
    /** The btn Y. */
    private int btnY;
    
    /** The btn W. */
    private int btnW;
    
    /** The btn H. */
    private int btnH;
    
    /** The pause X. */
    private int pauseX;

    /**
     * Instantiates a new mouse input game.
     *
     * @param viewPanel the view panel
     */
    public MouseInputGame(ViewPanel viewPanel) {
        super(viewPanel);

        this.menuX = (int) (this.panel.getWidth() / 51.2);
        this.btnY = (int) (this.panel.getHeight() - (this.panel.getWidth() / 51.2)
                - (this.panel.getHeight() / 10.2857143));
        this.btnW = this.panel.getWidth() / 12;
        this.btnH = this.panel.getHeight() / 14;
        this.pauseX = (int) (this.panel.getWidth() / 51.2) + this.menuX + this.btnW;
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

        if ((my >= this.btnY) && (my <= (this.btnY + this.btnH))) {
            if ((mx >= this.menuX) && (mx <= (this.menuX + this.btnW))) {
                this.viewframe.stopMusic();
                this.viewframe.getController().getTimer().stop();
                this.viewframe.setCurrentThemeIndex(9999);
                this.viewframe.setContentPane(new MenuPanel(this.viewframe, 0));
                this.viewframe.revalidate();
            } else if ((this.viewframe.getController().getTimeLeft() < 0) && (mx >= this.pauseX)
                    && (mx <= (this.pauseX + this.btnW))) {
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
