/*
 * @author Amaury Chabane
 */
package View;

import java.awt.event.MouseEvent;

/**
 * The Class MouseInputMenu.
 *
 * @author Amaury Chabane
 */
public class MouseInputMenu extends MouseInput {

    /** The play X. */
    private int playX;
    
    /** The btn Y. */
    private int btnY;
    
    /** The btn W. */
    private int btnW;
    
    /** The btn H. */
    private int btnH;
    
    /** The options X. */
    private int optionsX;
    
    /** The next X. */
    private int nextX;
    
    /** The previous X. */
    private int previousX;
    
    /** The refresh X. */
    private int refreshX;
    
    /** The quit X. */
    private int quitX;

    /**
     * Instantiates a new mouse input menu.
     *
     * @param menuPanel the menu panel
     */
    public MouseInputMenu(MenuPanel menuPanel) {
        super(menuPanel);

        this.playX = (int) (this.panel.getWidth() - (this.panel.getWidth() / 51.2) - (this.panel.getWidth() / 10));
        this.btnY = (int) (this.panel.getHeight() - (this.panel.getWidth() / 51.2)
                - (this.panel.getHeight() / 10.2857143));
        this.btnW = this.panel.getWidth() / 12;
        this.btnH = this.panel.getHeight() / 14;
        this.optionsX = (int) (this.panel.getWidth() - (this.panel.getWidth() / 51.2) - (this.panel.getWidth() / 10)
                - (this.panel.getWidth() / 51.2) - this.btnW);
        this.nextX = (int) (this.panel.getWidth() - (this.panel.getWidth() / 51.2) - (this.panel.getWidth() / 10)
                - ((this.panel.getWidth() / 51.2) * 2) - (this.btnW * 2)) + (this.btnW / 2);
        this.previousX = (int) (this.panel.getWidth() - (this.panel.getWidth() / 51.2) - (this.panel.getWidth() / 10)
                - ((this.panel.getWidth() / 51.2) * 3) - (this.btnW * 2));
        this.refreshX = (int) (this.panel.getWidth() - (this.panel.getWidth() / 51.2) - (this.panel.getWidth() / 10)
                - ((this.panel.getWidth() / 51.2) * 4) - (this.btnW * 3));
        this.quitX = (int) (this.panel.getWidth() / 51.2);
    }

    /**
     * Mouse pressed.
     *
     * @param e the e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if ((my >= this.btnY) && (my <= (this.btnY + this.btnH))) {
            if ((mx >= this.previousX) && (mx <= (this.previousX + (this.btnW / 2)))) {
                if (((MenuPanel) this.panel).getShowIndex() > 0) {
                    ((MenuPanel) this.panel).setShowIndex(((MenuPanel) this.panel).getShowIndex() - 6);
                    this.panel.repaint();
                }
            } else if ((mx >= this.nextX) && (mx <= (this.nextX + (this.btnW / 2)))) {
                if (this.viewframe.getController().getModel().getThemes()
                        .size() > ((((MenuPanel) this.panel).getShowIndex() + 6))) {
                    ((MenuPanel) this.panel).setShowIndex(((MenuPanel) this.panel).getShowIndex() + 6);
                    this.panel.repaint();
                }
            } else if ((mx >= this.optionsX) && (mx <= (this.optionsX + this.btnW))) {
                this.viewframe.setContentPane(new OptionsPanel(this.viewframe));
                this.viewframe.revalidate();
            } else if ((mx >= this.playX) && (mx <= (this.playX + this.btnW))) {
                this.viewframe.getController().startGame();
            } else if ((mx >= this.refreshX) && (mx <= (this.refreshX + this.btnW))) {
                this.viewframe.getModel().loadFolders();
                this.viewframe.getModel().fillThemesList();
                this.viewframe.getModel().loadTypes();
                this.panel.repaint();
            } else if ((mx >= this.quitX) && (mx <= (this.quitX + this.btnW))) {
                System.exit(0);

            }
        } else {
            for (int i = ((MenuPanel) this.panel).getShowIndex(); i < (((MenuPanel) this.panel).getShowIndex()
                    + 3); i++) {
                if (i < this.viewframe.getModel().getThemes().size()) {
                    int tY = (int) ((((((i - ((MenuPanel) this.panel).getShowIndex()) + 1)
                            * (this.panel.getWidth() - MenuPanel.LOGO.getWidth(null))) / 51.2)
                            + (((((this.panel.getWidth() - MenuPanel.LOGO.getWidth(null)) / 11) * 160) / 120)
                                    * (i - ((MenuPanel) this.panel).getShowIndex()))))
                            + MenuPanel.LOGO.getHeight(null);
                    int tX = (int) ((this.panel.getWidth() - MenuPanel.LOGO.getWidth(null)) / 51.2)
                            + MenuPanel.LOGO.getWidth(null);
                    int tW = (this.panel.getWidth() - MenuPanel.LOGO.getWidth(null)) / 11;
                    int tH = (tW * 160) / 120;
                    if ((my >= tY) && (my <= (tY + tH))) {
                        if ((mx >= tX) && (mx <= (tX + tW))) {
                            this.viewframe.setContentPane(
                                    new ThemePropPanel(this.viewframe, i, ((MenuPanel) this.panel).getShowIndex()));
                            this.viewframe.revalidate();
                            break;
                        }
                    }
                }
            }
            for (int i = ((MenuPanel) this.panel).getShowIndex() + 3; i < (((MenuPanel) this.panel).getShowIndex()
                    + 6); i++) {
                if (i < this.viewframe.getModel().getThemes().size()) {
                    int tY = ((int) (((((i - 3 - ((MenuPanel) this.panel).getShowIndex()) + 1)
                            * (this.panel.getWidth() - MenuPanel.LOGO.getWidth(null))) / 51.2))
                            + (((((this.panel.getWidth() - MenuPanel.LOGO.getWidth(null)) / 11) * 160) / 120)
                                    * (i - 3 - ((MenuPanel) this.panel).getShowIndex())))
                            + MenuPanel.LOGO.getHeight(null);
                    int tX = (int) ((this.panel.getWidth() - MenuPanel.LOGO.getWidth(null)) / 51.2)
                            + ((this.panel.getWidth() - MenuPanel.LOGO.getWidth(null)) / 2)
                            + MenuPanel.LOGO.getWidth(null);
                    int tW = (this.panel.getWidth() - MenuPanel.LOGO.getWidth(null)) / 11;
                    int tH = (tW * 160) / 120;
                    if ((my >= tY) && (my <= (tY + tH))) {
                        if ((mx >= tX) && (mx <= (tX + tW))) {
                            this.viewframe.setContentPane(
                                    new ThemePropPanel(this.viewframe, i, ((MenuPanel) this.panel).getShowIndex()));
                            this.viewframe.revalidate();
                            break;
                        }
                    }
                }
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
        this.panel.repaint();
    }
}
