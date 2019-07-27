/*
 *
 */
package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import Contract.IEntity;

/**
 * The Class ViewPanel.
 *
 * @author Amaury Chabane
 */
class ViewPanel extends JPanel {

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
     * Load image.
     *
     * @param path
     *                 the path
     * @return the image
     */

    public Image loadImage(String path) {
        Image img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Draw centered string.
     *
     * @param page
     *                   the page
     * @param s
     *                   the s
     * @param x
     *                   the x
     * @param y
     *                   the y
     * @param width
     *                   the width
     * @param height
     *                   the height
     */
    public void drawCenteredString(Graphics page, String s, int x, int y, int width, int height) {
        // Find the size of string s in the font of the Graphics context "page"
        FontMetrics fm = page.getFontMetrics(page.getFont());
        Rectangle2D rect = fm.getStringBounds(s, page);
        int textHeight = (int) (rect.getHeight());
        int textWidth = (int) (rect.getWidth());

        // Center text horizontally and vertically within provided rectangular bounds
        int textX = x + ((width - textWidth) / 2);
        int textY = y + ((height - textHeight) / 2) + fm.getAscent();
        page.drawString(s, textX, textY);
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
     * Draw gradient paint.
     *
     * @param graphics
     *                     the graphics
     */
    public void drawGradientPaint(Graphics2D graphics) {
        Paint paint = new GradientPaint(this.getWidth() / 2, 0, Color.blue.darker().darker(), this.getWidth() / 2,
                this.getHeight(), Color.black);
        graphics.setPaint(paint);
        graphics.fill(new Rectangle(this.getWidth(), this.getHeight()));
    }

    /**
     * Draw button.
     *
     * @param graphics
     *                     the graphics
     * @param text
     *                     the text
     * @param x
     *                     the x
     * @param y
     *                     the y
     * @param width
     *                     the width
     * @param height
     *                     the height
     */
    public void drawButton(Graphics2D graphics, String text, int x, int y, int width, int height) {
        Font basicFont = new Font("Cooper Black", Font.BOLD, (int) (this.getHeight() / 28.8));
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(x - (this.getWidth() / 128), y - (this.getHeight() / 72), width + (this.getWidth() / 64),
                height + (this.getHeight() / 36));
        Point mousePos = this.getMousePosition();
        graphics.setColor(Color.GRAY);
        if (mousePos != null) {
            if ((mousePos.getX() >= x) && (mousePos.getX() <= (x + width))) {
                if ((mousePos.getY() >= y) && (mousePos.getY() <= (y + height))) {
                    graphics.setColor(Color.WHITE);
                }
            }
        }
        graphics.fillRect(x, y, width, height);
        graphics.setColor(Color.BLACK);
        graphics.setFont(basicFont);
        this.drawCenteredString(graphics, text, x, y, width, height);
    }

    /**
     * Paint component.
     *
     * @param g
     *              the g
     */
    @Override
    protected void paintComponent(final Graphics g) {
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
