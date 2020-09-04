/*
 * @author Amaury Chabane
 */
package View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * The Class MouseInputThemeProp.
 *
 * @author Amaury Chabane
 */
public class MouseInputThemeProp implements MouseListener, MouseMotionListener {

    /** The theme prop panel. */
    private ThemePropPanel themePropPanel = null;

    /**
     * Instantiates a new mouse input theme prop.
     *
     * @param themePropPanel
     *                           the theme prop panel
     */
    public MouseInputThemeProp(ThemePropPanel themePropPanel) {
        this.themePropPanel = themePropPanel;
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

        int menuX = (int) (this.getThemePropPanel().getWidth() / 51.2);
        int btnY = (int) (this.getThemePropPanel().getHeight() - (this.getThemePropPanel().getWidth() / 51.2)
                - (this.getThemePropPanel().getHeight() / 10.2857143));
        int btnW = this.getThemePropPanel().getWidth() / 12;
        int btnH = this.getThemePropPanel().getHeight() / 14;
        int playX = (int) (this.getThemePropPanel().getWidth() / 51.2) + menuX + btnW;
        if ((my >= btnY) && (my <= (btnY + btnH))) {
            if ((mx >= playX) && (mx <= (playX + btnW))) {
                try {
                    this.getThemePropPanel().getViewFrame().stopMusic();
                    this.getThemePropPanel().getViewFrame().playMusic(this.getThemePropPanel().getTheme().getFile(),
                            Integer.parseInt(this.getThemePropPanel().getTimeCodeField().getText()),
                            this.getThemePropPanel());
                } catch (Exception e1) {
                }
            } else if ((mx >= menuX) && (mx <= (menuX + btnW))) {
                this.getThemePropPanel().getTheme().setPropertyValue("title",
                        this.getThemePropPanel().getTitleField().getText());
                this.getThemePropPanel().getTheme().setTitle(this.getThemePropPanel().getTitleField().getText());

                this.getThemePropPanel().getTheme().setPropertyValue("composer",
                        this.getThemePropPanel().getComposerField().getText());
                this.getThemePropPanel().getTheme().setComposer(this.getThemePropPanel().getComposerField().getText());

                this.getThemePropPanel().getTheme().setPropertyValue("type",
                        this.getThemePropPanel().getTypeField().getText());
                this.getThemePropPanel().getTheme().setType(this.getThemePropPanel().getTypeField().getText());

                this.getThemePropPanel().getTheme().setPropertyValue("timecode",
                        this.getThemePropPanel().getTimeCodeField().getText());
                this.getThemePropPanel().getTheme()
                        .setTimecode(Integer.parseInt(this.getThemePropPanel().getTimeCodeField().getText()));

                this.getThemePropPanel().getTheme().setPropertyValue("release",
                        this.getThemePropPanel().getReleaseField().getText());
                this.getThemePropPanel().getTheme()
                        .setReleaseDate(this.getThemePropPanel().getReleaseField().getText());

                this.getThemePropPanel().getTheme().setPropertyValue("infos",
                        this.getThemePropPanel().getInfosField().getText());
                this.getThemePropPanel().getTheme().setInfos(this.getThemePropPanel().getInfosField().getText());

                this.getThemePropPanel().getViewFrame().getModel().loadFolders();
                this.getThemePropPanel().getViewFrame().getModel().fillThemesList();
                this.getThemePropPanel().getViewFrame().getModel().loadTypes();
                this.getThemePropPanel().getViewFrame().stopMusic();
                this.getThemePropPanel().getViewFrame().setContentPane(new MenuPanel(
                        this.getThemePropPanel().getViewFrame(), this.getThemePropPanel().getOriginalShowIndex()));
                this.getThemePropPanel().getViewFrame().revalidate();
            }
        }
    }

    /**
     * Gets the theme prop panel.
     *
     * @return the theme prop panel
     */
    private ThemePropPanel getThemePropPanel() {
        return this.themePropPanel;
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
        int btnY = (int) ((this.getThemePropPanel().getHeight() - (this.getThemePropPanel().getWidth() / 51.2)
                - (this.getThemePropPanel().getHeight() / 10.2857143)) - 200);
        if (e.getY() >= btnY) {
            this.getThemePropPanel().repaint(0, btnY, this.getThemePropPanel().getWidth(),
                    this.getThemePropPanel().getHeight());
        }
    }
}
