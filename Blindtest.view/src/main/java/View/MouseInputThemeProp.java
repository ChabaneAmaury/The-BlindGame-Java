/*
 * @author Amaury Chabane
 */
package View;

import java.awt.event.MouseEvent;
import Contract.IEntity;

/**
 * The Class MouseInputThemeProp.
 *
 * @author Amaury Chabane
 */
public class MouseInputThemeProp extends MouseInput {

    /** The menu X. */
    private int menuX;
    
    /** The btn Y. */
    private int btnY;
    
    /** The btn W. */
    private int btnW;
    
    /** The btn H. */
    private int btnH;
    
    /** The play X. */
    private int playX;

    /**
     * Instantiates a new mouse input theme prop.
     *
     * @param themePropPanel the theme prop panel
     */
    public MouseInputThemeProp(ThemePropPanel themePropPanel) {
        super(themePropPanel);

        this.menuX = (int) (this.panel.getWidth() / 51.2);
        this.btnY = (int) (this.panel.getHeight() - (this.panel.getWidth() / 51.2)
                - (this.panel.getHeight() / 10.2857143));
        this.btnW = this.panel.getWidth() / 12;
        this.btnH = this.panel.getHeight() / 14;
        this.playX = (int) (this.panel.getWidth() / 51.2) + this.menuX + this.btnW;
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

        if ((my >= this.btnY) && (my <= (this.btnY + this.btnH))) {
            if ((mx >= this.playX)
                    && (mx <= ((theme.isHasError()) ? this.playX + this.btnW + (this.panel.getWidth() / 50)
                            : this.playX + this.btnW))) {
                if (theme.isHasError()) {
                    String youtubeUrl = ((ThemePropPanel) this.panel).getYtUrlField().getText();
                    String filepath = theme.getFolder().getAbsolutePath();
                    this.viewframe.getController().downloadYtVideoToMP3(filepath, youtubeUrl);

                    theme.setFile(theme.FindFileByExtension(theme.getFolder(), theme.getFileExtensions()));
                    theme.setHasError(false);
                    this.panel.remove(((ThemePropPanel) this.panel).getYtUrlField());
                } else {
                    try {
                        this.viewframe.stopMusic();
                        this.viewframe.playMusic(theme.getFile(),
                                Integer.parseInt(((ThemePropPanel) this.panel).getTimeCodeField().getText()),
                                this.panel);
                    } catch (Exception e1) {
                    }
                }

            } else if ((mx >= this.menuX) && (mx <= (this.menuX + this.btnW))) {
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
