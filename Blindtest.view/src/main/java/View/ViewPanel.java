/*
 *
 */
package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.File;
import java.util.List;

import Contract.IEntity;

/**
 * The Class ViewPanel.
 *
 * @author Amaury Chabane
 */
class ViewPanel extends MyPanel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -998294702363713521L;

    /** The view frame. */
    private ViewFrame viewFrame;

    /**
     * Instantiates a new view panel.
     *
     * @param viewFrame
     *                      the view frame
     */
    public ViewPanel(final ViewFrame viewFrame) {
        MouseInputGame mouseInput = new MouseInputGame(this);
        this.setViewFrame(viewFrame);
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
    }

    /**
     * Gets the view frame.
     *
     * @return the view frame
     */
    public ViewFrame getViewFrame() {
        return this.viewFrame;
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
        int metaYStart = (int) (this.getWidth() / 51.2);
        int titleHeight = (int) (this.getHeight() / 28.8) + graphics.getFont().getSize();
        int composerHeight = (int) (this.getHeight() / 14.4) + titleHeight + graphics.getFont().getSize();
        int dateHeight = (int) (this.getHeight() / 14.4) + composerHeight + graphics.getFont().getSize();
        Font font = new Font("Cooper Black", Font.BOLD, (int) (this.getHeight() / 28.8));
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        graphics.drawString("Title : " + theme.getTitle(), metaYStart, titleHeight);
        graphics.drawString("Composer : " + theme.getComposer(), metaYStart, composerHeight);
        graphics.drawString("Release date : " + theme.getReleaseDate(), metaYStart, dateHeight);

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
        graphics.fill(new Rectangle(rectX - border, rectY, rectW + (border * 2), rectH + (border * 2)));
        if (new File(theme.getCover()).exists()) {
            graphics.setColor(Color.WHITE);
            graphics.drawImage(theme.getCoverImage(), rectX, (int) (this.getWidth() / 51.2), rectW, rectH, null);
        } else {
            graphics.setBackground(Color.GRAY);
            graphics.clearRect(rectX, (int) (this.getWidth() / 51.2), rectW, rectH);
            graphics.setColor(Color.WHITE);
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
        Font font = new Font("Cooper Black", Font.BOLD, (int) (this.getHeight() / 7.2));
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
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
        int quitX = (int) (this.getWidth() / 51.2);
        int btnY = (int) (this.getHeight() - (this.getWidth() / 51.2) - (this.getHeight() / 10.2857143));
        int btnW = this.getWidth() / 10;
        int btnH = this.getHeight() / 12;
        int menuX = (int) (this.getWidth() / 51.2) + quitX + btnW;

        IEntity theme = this.getViewFrame().getController().getTheme();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.drawGradientPaint(graphics);
        if (this.getViewFrame().getController().getTimeLeft() < 0) {
            this.drawTheme(graphics, theme);
        } else {
            this.drawCounter(graphics);
        }
        this.drawButton(graphics, "Quit", quitX, btnY, btnW, btnH);
        this.drawButton(graphics, "Menu", menuX, btnY, btnW, btnH);
    }

    /**
     * Sets the view frame.
     *
     * @param viewFrame
     *                      the new view frame
     */
    void setViewFrame(final ViewFrame viewFrame) {
        this.viewFrame = viewFrame;
    }
}
