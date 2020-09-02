/*
 * @author Amaury Chabane
 */
package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import Contract.IEntity;

/**
 * The Class ViewPanel.
 *
 * @author Amaury Chabane
 */
class ViewPanel extends MyPanel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -998294702363713521L;

    /** The is paused. */
    private boolean isPaused = false;

    /**
     * Instantiates a new view panel.
     *
     * @param viewFrame
     *                      the view frame
     */
    public ViewPanel(final ViewFrame viewFrame) {
        super(viewFrame);
        MouseInputGame mouseInput = new MouseInputGame(this);
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
    }

    /**
     * Draw theme.
     *
     * @param graphics
     *                     the graphics
     * @param theme
     *                     the theme
     */
    public void drawTheme(Graphics2D graphics, IEntity theme) {
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int metaXStart = (int) (this.getWidth() / 51.2);
        int titleHeight = (int) (this.getHeight() / 28.8) + graphics.getFont().getSize();
        int composerHeight = (int) (this.getHeight() / 14.4) + titleHeight + graphics.getFont().getSize();
        int dateHeight = (int) (this.getHeight() / 14.4) + composerHeight + graphics.getFont().getSize();
        Font font = new Font(this.getFontName(), Font.BOLD, (int) (this.getHeight() / 28.8));
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        graphics.drawString("Title : " + theme.getTitle(), metaXStart, titleHeight);
        graphics.drawString("Composer : " + theme.getComposer(), metaXStart, composerHeight);
        graphics.drawString("Release date : " + theme.getReleaseDate(), metaXStart, dateHeight);

        List<String> strings = StringUtils.wrap(theme.getInfos(), graphics.getFontMetrics(), (int) (this.getWidth()
                - (this.getWidth() / 17.1) - ((this.getHeight() - (this.getWidth() / 25.6)) * 0.75)));
        int Ystart = (int) (this.getHeight() / 14.4) + dateHeight + graphics.getFont().getSize();
        for (String string : strings) {
            graphics.drawString(string, (int) (this.getWidth() / 51.2), Ystart);
            Ystart += this.getHeight() / 24;
        }

        int border = 3;
        int rectX = (int) (this.getWidth() - (this.getWidth() / 51.2)
                - ((this.getHeight() - (this.getWidth() / 25.6)) * 0.75));
        int rectY = (int) ((this.getWidth() / 51.2) - border);
        int rectW = (int) ((this.getHeight() - (this.getWidth() / 25.6)) * 0.75);
        int rectH = (int) (this.getHeight() - (this.getWidth() / 25.6));
        if (new File(theme.getCover()).exists()) {
            graphics.setColor(Color.BLACK);
            Dimension imgDim = this.scaleImageDimensions(theme.getCoverImage(), rectW, rectH);
            if ((theme.getResizedCoverImage() == null)
                    || (theme.getResizedCoverImage().getWidth(null) != (int) imgDim.getWidth())
                    || (theme.getResizedCoverImage().getHeight(null) != (int) imgDim.getHeight())) {
                try {
                    theme.setResizedCoverImage(ImageIO.read(new File(theme.getCover()))
                            .getScaledInstance((int) imgDim.getWidth(), (int) imgDim.getHeight(), Image.SCALE_SMOOTH));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            rectX = (int) ((rectX + (rectW / 2)) - (imgDim.getWidth() / 2));
            rectY = (int) ((rectY + (rectH / 2)) - (imgDim.getHeight() / 2));
            rectW = (int) imgDim.getWidth();
            rectH = (int) imgDim.getHeight();

            graphics.fill(new Rectangle(rectX - border, rectY - border, rectW + (border * 2), rectH + (border * 2)));
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(theme.getResizedCoverImage(), rectX, rectY, rectW, rectH, null);
        } else {
            graphics.setBackground(Color.GRAY);
            graphics.clearRect(rectX, (int) (this.getWidth() / 51.2), rectW, rectH);
            graphics.setColor(Color.BLACK);
            this.drawCenteredString(graphics, "Image Not Found", rectX, (int) (this.getWidth() / 51.2), rectW, rectH);
        }
    }

    /**
     * Draw counter.
     *
     * @param graphics
     *                     the graphics
     */
    public void drawCounter(Graphics2D graphics) {
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font(this.getFontName(), Font.BOLD, (int) (this.getHeight() / 20.57)));
        graphics.drawString(
                (this.getViewFrame().getController().getThemeIndex() + 1) + "/"
                        + this.getViewFrame().getController().getTmpList().size(),
                (int) (this.getWidth() / 51.2), (int) (this.getWidth() / 51.2) + graphics.getFont().getSize());
        Font font = new Font(this.getFontName(), Font.BOLD, (int) (this.getHeight() / 7.2));
        graphics.setFont(font);
        String time = Integer.toString(this.getViewFrame().getController().getTimeLeft());
        this.drawCenteredString(graphics, time, 0, 0, this.getWidth(), this.getHeight());
    }

    /**
     * Paint component.
     *
     * @param g
     *              the g
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        int menuX = (int) (this.getWidth() / 51.2);
        int btnY = (int) (this.getHeight() - (this.getWidth() / 51.2) - (this.getHeight() / 10.2857143));
        int btnW = this.getWidth() / 12;
        int btnH = this.getHeight() / 14;
        int pauseX = (int) (this.getWidth() / 51.2) + menuX + btnW;

        IEntity theme = this.getViewFrame().getController().getTheme();
        Graphics2D graphics = (Graphics2D) g;
        this.drawGradientPaint(graphics);
        if (this.getViewFrame().getController().getTimeLeft() < 0) {
            this.drawTheme(graphics, theme);
            if (!this.isPaused()) {
                this.drawButton(graphics, "Pause", pauseX, btnY, btnW, btnH);
            } else {
                this.drawButton(graphics, "Continue", pauseX, btnY, btnW, btnH);
            }
        } else {
            this.drawCounter(graphics);
        }
        this.drawButton(graphics, "Menu", menuX, btnY, btnW, btnH);
    }

    /**
     * Checks if is paused.
     *
     * @return true, if is paused
     */
    public boolean isPaused() {
        return this.isPaused;
    }

    /**
     * Sets the paused.
     *
     * @param isPaused
     *                     the new paused
     */
    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }
}
