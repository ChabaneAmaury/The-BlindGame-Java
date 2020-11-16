/*
 * @author Amaury Chabane
 */
package View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Contract.IEntity;

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
        ViewFrame viewframe = this.getThemePropPanel().getViewFrame();
        IEntity theme = this.getThemePropPanel().getTheme();

        int mx = e.getX();
        int my = e.getY();

        int menuX = (int) (this.getThemePropPanel().getWidth() / 51.2);
        int btnY = (int) (this.getThemePropPanel().getHeight() - (this.getThemePropPanel().getWidth() / 51.2)
                - (this.getThemePropPanel().getHeight() / 10.2857143));
        int btnW = this.getThemePropPanel().getWidth() / 12;
        int btnH = this.getThemePropPanel().getHeight() / 14;
        int playX = (int) (this.getThemePropPanel().getWidth() / 51.2) + menuX + btnW;
        if ((my >= btnY) && (my <= (btnY + btnH))) {
            if ((mx >= playX)
                    && (mx <= ((theme.isHasError()) ? playX + btnW + (this.getThemePropPanel().getWidth() / 50)
                            : playX + btnW))) {
                if (theme.isHasError()) {
                    String youtubeUrl = this.getThemePropPanel().getYtUrlField().getText();
                    String filepath = theme.getFolder().getAbsolutePath();
                    viewframe.getController().downloadYtVideoToMP3(filepath, youtubeUrl);

                    theme.setFile(theme.FindFileByExtension(theme.getFolder(), theme.getFileExtensions()));
                    theme.setHasError(false);
                    this.getThemePropPanel().remove(this.getThemePropPanel().getYtUrlField());
                } else {
                    try {
                        viewframe.stopMusic();
                        viewframe.playMusic(theme.getFile(),
                                Integer.parseInt(this.getThemePropPanel().getTimeCodeField().getText()),
                                this.getThemePropPanel());
                    } catch (Exception e1) {
                    }
                }

            } else if ((mx >= menuX) && (mx <= (menuX + btnW))) {
                theme.setPropertyValue("title", this.getThemePropPanel().getTitleField().getText());
                theme.setTitle(this.getThemePropPanel().getTitleField().getText());

                theme.setPropertyValue("composer", this.getThemePropPanel().getComposerField().getText());
                theme.setComposer(this.getThemePropPanel().getComposerField().getText());

                theme.setPropertyValue("type", this.getThemePropPanel().getTypeField().getText());
                theme.setType(this.getThemePropPanel().getTypeField().getText());

                theme.setPropertyValue("timecode", this.getThemePropPanel().getTimeCodeField().getText());
                theme.setTimecode(Integer.parseInt(this.getThemePropPanel().getTimeCodeField().getText()));

                theme.setPropertyValue("release", this.getThemePropPanel().getReleaseField().getText());
                theme.setReleaseDate(this.getThemePropPanel().getReleaseField().getText());

                theme.setPropertyValue("infos", this.getThemePropPanel().getInfosField().getText());
                theme.setInfos(this.getThemePropPanel().getInfosField().getText());

                viewframe.getModel().loadTypes();
                viewframe.stopMusic();
                viewframe.setContentPane(new MenuPanel(viewframe, this.getThemePropPanel().getOriginalShowIndex()));
                viewframe.revalidate();
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
