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

    /**
     * Instantiates a new mouse input menu.
     *
     * @param menuPanel the menu panel
     */
    public MouseInputMenu(MenuPanel menuPanel) {
        super(menuPanel);

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

        /** The play X. */
        int playX = (int) (this.panel.getWidth() - (this.panel.getWidth() / 51.2) - (this.panel.getWidth() / 10));
        /** The btn Y. */
        int btnY = (int) (this.panel.getHeight() - (this.panel.getWidth() / 51.2)
                - (this.panel.getHeight() / 10.2857143));
        /** The btn W. */
        int btnW = this.panel.getWidth() / 12;
        /** The btn H. */
        int btnH = this.panel.getHeight() / 14;
        /** The options X. */
        int optionsX = (int) (this.panel.getWidth() - (this.panel.getWidth() / 51.2) - (this.panel.getWidth() / 10)
                - (this.panel.getWidth() / 51.2) - btnW);
        /** The next X. */
        int nextX = (int) (this.panel.getWidth() - (this.panel.getWidth() / 51.2) - (this.panel.getWidth() / 10)
                - ((this.panel.getWidth() / 51.2) * 2) - (btnW * 2)) + (btnW / 2);
        /** The previous X. */
        int previousX = (int) (this.panel.getWidth() - (this.panel.getWidth() / 51.2) - (this.panel.getWidth() / 10)
                - ((this.panel.getWidth() / 51.2) * 3) - (btnW * 2));
        /** The refresh X. */
        int refreshX = (int) (this.panel.getWidth() - (this.panel.getWidth() / 51.2) - (this.panel.getWidth() / 10)
                - ((this.panel.getWidth() / 51.2) * 4) - (btnW * 3));
        /** The quit X. */
        int quitX = (int) (this.panel.getWidth() / 51.2);

        if ((my >= btnY) && (my <= (btnY + btnH))) {
            if ((mx >= previousX) && (mx <= (previousX + (btnW / 2)))) {
                if (((MenuPanel) this.panel).getShowIndex() > 0) {
                    ((MenuPanel) this.panel).setShowIndex(((MenuPanel) this.panel).getShowIndex() - 6);
                    this.panel.repaint();
                }
            } else if ((mx >= nextX) && (mx <= (nextX + (btnW / 2)))) {
                if (this.viewframe.getController().getModel().getThemes()
                        .size() > ((((MenuPanel) this.panel).getShowIndex() + 6))) {
                    ((MenuPanel) this.panel).setShowIndex(((MenuPanel) this.panel).getShowIndex() + 6);
                    this.panel.repaint();
                }
            } else if ((mx >= optionsX) && (mx <= (optionsX + btnW))) {
                this.viewframe.setContentPane(new OptionsPanel(this.viewframe));
                this.viewframe.revalidate();
            } else if ((mx >= playX) && (mx <= (playX + btnW))) {
                this.viewframe.getController().startGame();
            } else if ((mx >= refreshX) && (mx <= (refreshX + btnW))) {
                this.viewframe.getModel().loadFolders();
                this.viewframe.getModel().fillThemesList();
                this.viewframe.getModel().loadTypes();
                this.panel.repaint();
            } else if ((mx >= quitX) && (mx <= (quitX + btnW))) {
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
