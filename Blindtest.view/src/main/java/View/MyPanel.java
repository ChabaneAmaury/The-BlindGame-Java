/*
 * @author Amaury Chabane
 */
package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * The Class MyPanel.
 *
 * @author Amaury Chabane
 */
public class MyPanel extends JPanel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7093327424627297559L;

    /** The view frame. */
    private ViewFrame viewFrame = null;

    /** The dimensions. */
    private Dimension dimensions = null;

    /** The font name. */
    private String fontName = "Arial";

    /**
     * Instantiates a new my panel.
     *
     * @param viewFrame
     *                      the view frame
     */
    public MyPanel(ViewFrame viewFrame) {
        this.setViewFrame(viewFrame);
        this.setDimensions(this.getViewFrame().getSize());
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
     * Scale image dimensions.
     *
     * @param image
     *                      the image
     * @param maxWidth
     *                      the max width
     * @param maxHeight
     *                      the max height
     * @return the dimension
     */
    public Dimension scaleImageDimensions(Image image, double maxWidth, double maxHeight) {

        double widthRatio = maxWidth / image.getWidth(null);
        double heightRatio = maxHeight / image.getHeight(null);
        double ratio = Math.min(widthRatio, heightRatio);

        int rectW = (int) (image.getWidth(null) * ratio);
        int rectH = (int) (image.getHeight(null) * ratio);

        return new Dimension(rectW, rectH);
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
     * Draw gradient paint.
     *
     * @param graphics
     *                     the graphics
     */
    public void drawGradientPaint(Graphics2D graphics) {
        Paint paint = new GradientPaint(this.getWidth() / 2, 0, Color.white, this.getWidth() / 2, this.getHeight(),
                Color.gray);
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
        Font basicFont = new Font(this.getFontName(), Font.BOLD, (int) (this.getHeight() / 28.8));
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
     * Gets the view frame.
     *
     * @return the view frame
     */
    public ViewFrame getViewFrame() {
        return this.viewFrame;
    }

    /**
     * Sets the view frame.
     *
     * @param viewFrame
     *                      the new view frame
     */
    public void setViewFrame(ViewFrame viewFrame) {
        this.viewFrame = viewFrame;
    }

    /**
     * Gets the dimensions.
     *
     * @return the dimensions
     */
    public Dimension getDimensions() {
        return this.dimensions;
    }

    /**
     * Sets the dimensions.
     *
     * @param dimensions
     *                       the new dimensions
     */
    public void setDimensions(Dimension dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * Gets the font name.
     *
     * @return the font name
     */
    public String getFontName() {
        return this.fontName;
    }

    /**
     * Sets the font name.
     *
     * @param fontName
     *                     the new font name
     */
    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

}
