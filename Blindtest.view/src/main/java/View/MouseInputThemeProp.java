/*
 * @author Amaury Chabane
 */
package View;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import Contract.IEntity;

/**
 * The Class MouseInputThemeProp.
 *
 * @author Amaury Chabane
 */
public class MouseInputThemeProp extends MouseInput {

    /**
     * Instantiates a new mouse input theme prop.
     *
     * @param themePropPanel the theme prop panel
     */
    public MouseInputThemeProp(ThemePropPanel themePropPanel) {
        super(themePropPanel);
    }

    /**
     * Mouse pressed.
     *
     * @param e the e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        IEntity theme = ((ThemePropPanel) this.panel).getTheme();

        int mx = e.getX();
        int my = e.getY();

        int menuX = (int) (this.panel.getWidth() / 51.2);
        int btnY = (int) (this.panel.getHeight() - (this.panel.getWidth() / 51.2)
                - (this.panel.getHeight() / 10.2857143));
        int btnW = this.panel.getWidth() / 12;
        int btnH = this.panel.getHeight() / 14;
        int openX = (int) (this.panel.getWidth() / 51.2) + menuX + btnW;
        int playX = (int) (this.panel.getWidth() / 51.2) + openX + btnW;
        int downloadX = (int) (this.panel.getWidth() / 51.2) + playX + btnW;

        if ((my >= btnY) && (my <= (btnY + btnH))) {
            if ((mx >= playX)
                    && (mx <= ((theme.isHasError()) ? playX + btnW + (this.panel.getWidth() / 50)
                            : playX + btnW))) {

                try {
                    this.viewframe.stopMusic();
                    this.viewframe.playMusic(theme.getFile(),
                            Integer.parseInt(((ThemePropPanel) this.panel).getTimeCodeField().getText()),
                            this.panel);
                } catch (Exception ignored) {
                }

            }else if ((mx >= downloadX) && (mx <= (downloadX + btnW))) {
                String youtubeUrl = ((ThemePropPanel) this.panel).getYtUrlField().getText();
                String filepath = theme.getFolder().getAbsolutePath();
                this.viewframe.getController().downloadYtVideoToMP3(filepath, youtubeUrl);
                theme.setFile(theme.FindFileByExtension(theme.getFolder(), theme.getFileExtensions()));
                theme.setHasError(false);
            }else if ((mx >= openX) && (mx <= (openX + btnW))) {
                try {
                    Desktop.getDesktop().open(((ThemePropPanel) this.panel).getTheme().getFolder());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else if ((mx >= menuX) && (mx <= (menuX + btnW))) {
                theme.setPropertyValue("title", ((ThemePropPanel) this.panel).getTitleField().getText());
                theme.setTitle(((ThemePropPanel) this.panel).getTitleField().getText());

                theme.setPropertyValue("composer", ((ThemePropPanel) this.panel).getComposerField().getText());
                theme.setComposer(((ThemePropPanel) this.panel).getComposerField().getText());

                theme.setPropertyValue("type", ((ThemePropPanel) this.panel).getTypeField().getText());
                theme.setType(((ThemePropPanel) this.panel).getTypeField().getText());

                theme.setPropertyValue("timecode", ((ThemePropPanel) this.panel).getTimeCodeField().getText());
                theme.setTimecode(Integer.parseInt(((ThemePropPanel) this.panel).getTimeCodeField().getText()));

                theme.setPropertyValue("release", ((ThemePropPanel) this.panel).getReleaseField().getText());
                theme.setReleaseDate(((ThemePropPanel) this.panel).getReleaseField().getText());

                theme.setPropertyValue("infos", ((ThemePropPanel) this.panel).getInfosField().getText());
                theme.setInfos(((ThemePropPanel) this.panel).getInfosField().getText());

                this.viewframe.getModel().loadTypes();
                this.viewframe.stopMusic();
                this.viewframe.setContentPane(
                        new MenuPanel(this.viewframe, ((ThemePropPanel) this.panel).getOriginalShowIndex()));
                this.viewframe.revalidate();
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
