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
 * The Class MenuPanel.
 *
 * @author Amaury Chabane
 */
public class MenuPanel extends JPanel {

    // 25 = (this.getWidth() / 51.2)
    // 50 = (this.getWidth() / 25.6)
    // 75 = (this.getWidth() / 17.1)

    /** The Constant ERROR_ICON. */
    private final static String ERROR_ICON = "bin\\error_icon.png";

    /** The show index. */
    private int showIndex = 0;

    /** The view frame. */
    private ViewFrame viewFrame = null;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6346376664434242649L;

    /**
     * Instantiates a new menu panel.
     *
     * @param viewFrame
     *                      the view frame
     */
    public MenuPanel(ViewFrame viewFrame) {
        MouseInputMenu mouseInput = new MouseInputMenu(this);
        this.setViewFrame(viewFrame);
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
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
     * Draw theme.
     *
     * @param graphics
     *                     the graphics
     * @param theme
     *                     the theme
     * @param x
     *                     the x
     * @param y
     *                     the y
     */
    public void drawTheme(Graphics2D graphics, IEntity theme, double x, double y) {
        Font basicFont = new Font("Cooper Black", Font.BOLD, (int) (this.getHeight() / 28.8));
        graphics.setColor(Color.WHITE);
        graphics.setFont(basicFont);
        Font miniFont = new Font("Cooper Black", Font.BOLD, this.getHeight() / 90);
        int rectX = (int) x;
        int rectY = (int) y;
        int rectW = this.getWidth() / 11;
        int rectH = (rectW * 160) / 120;
        int errorX = (int) (rectX + rectW + (this.getWidth() / 51.2));
        int errorY = (rectY + (rectH / 2)) - ((this.getHeight() / 15) / 2);
        int errorW = this.getHeight() / 15;
        int titleX = (int) (rectX + rectW + (this.getWidth() / 12.8));
        int titleY = rectY + (rectH / 2) + (graphics.getFont().getSize() / 2);
        graphics.drawString(theme.getTitle(), titleX, titleY);
        if (new File(theme.getCover()).exists()) {
            graphics.setColor(Color.WHITE);
            graphics.drawImage(theme.getResizedCoverImage(), rectX, rectY, rectW, rectH, null);
        } else {
            graphics.setBackground(Color.GRAY);
            graphics.clearRect(rectX, rectY, rectW, rectH);
            graphics.setColor(Color.WHITE);
            graphics.setFont(miniFont);
            this.drawCenteredString(graphics, "Image Not Found", rectX, rectY, rectW, rectH);
        }
        if (theme.isHasError()) {
            graphics.drawImage(this.loadImage(ERROR_ICON), errorX, errorY, errorW, errorW, null);
        }
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
        int playX = (int) (this.getWidth() - (this.getWidth() / 51.2) - (this.getWidth() / 10));
        int btnY = (int) (this.getHeight() - (this.getWidth() / 51.2) - (this.getHeight() / 10.2857143));
        int btnW = this.getWidth() / 10;
        int btnH = this.getHeight() / 12;
        int optionsX = (int) (this.getWidth() - (this.getWidth() / 51.2) - (this.getWidth() / 10)
                - (this.getWidth() / 51.2) - btnW);
        int nextX = (int) (this.getWidth() - (this.getWidth() / 51.2) - (this.getWidth() / 10)
                - ((this.getWidth() / 51.2) * 2) - (btnW * 2));
        int previousX = (int) (this.getWidth() - (this.getWidth() / 51.2) - (this.getWidth() / 10)
                - ((this.getWidth() / 51.2) * 3) - (btnW * 3));
        int refreshX = (int) (this.getWidth() - (this.getWidth() / 51.2) - (this.getWidth() / 10)
                - ((this.getWidth() / 51.2) * 4) - (btnW * 4));
        int quitX = (int) (this.getWidth() - (this.getWidth() / 51.2) - (this.getWidth() / 10)
                - ((this.getWidth() / 51.2) * 5) - (btnW * 5));
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.drawGradientPaint(graphics);
        this.drawButton(graphics, "Play", playX, btnY, btnW, btnH);
        this.drawButton(graphics, "Options", optionsX, btnY, btnW, btnH);
        this.drawButton(graphics, "Next", nextX, btnY, btnW, btnH);
        this.drawButton(graphics, "Previous", previousX, btnY, btnW, btnH);
        this.drawButton(graphics, "Refresh", refreshX, btnY, btnW, btnH);
        this.drawButton(graphics, "Quit", quitX, btnY, btnW, btnH);
        for (IEntity theme : this.getViewFrame().getModel().getThemes()) {
            if (theme.isHasError()) {
                graphics.drawImage(this.loadImage(ERROR_ICON), (int) (this.getWidth() / 51.2), btnY, btnH, btnH, null);
                graphics.setFont(new Font("Cooper Black", Font.BOLD, 20));
                graphics.setColor(new Color(255, 115, 115));
                List<String> strings = StringUtils.wrap("One or more themes are missing audio file...",
                        graphics.getFontMetrics(), 150);
                int Ystart = btnY;
                for (String string : strings) {
                    graphics.drawString(string,
                            (int) ((int) (this.getWidth() / 51.2) + (this.getWidth() / 51.2) + btnH), Ystart);
                    Ystart += graphics.getFont().getSize();
                }
                break;
            }
        }
        for (int i = this.getShowIndex(); i < (this.getShowIndex() + 3); i++) {
            if (i < this.getViewFrame().getModel().getThemes().size()) {
                this.drawTheme(graphics, this.getViewFrame().getModel().getThemes().get(i), this.getWidth() / 51.2,
                        (int) (((((i - this.getShowIndex()) + 1) * this.getWidth()) / 51.2)
                                + ((((this.getWidth() / 11) * 160) / 120) * (i - this.getShowIndex()))));
            }
        }
        for (int i = this.getShowIndex() + 3; i < (this.getShowIndex() + 6); i++) {
            if (i < this.getViewFrame().getModel().getThemes().size()) {
                this.drawTheme(graphics, this.getViewFrame().getModel().getThemes().get(i),
                        (this.getWidth() / 51.2) + (this.getWidth() / 2),
                        (int) (((((i - 3 - this.getShowIndex()) + 1) * this.getWidth()) / 51.2))
                                + ((((this.getWidth() / 11) * 160) / 120) * (i - 3 - this.getShowIndex())));
            }
        }
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
     * Gets the show index.
     *
     * @return the show index
     */
    public int getShowIndex() {
        return this.showIndex;
    }

    /**
     * Sets the show index.
     *
     * @param showIndex
     *                      the new show index
     */
    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }
}
