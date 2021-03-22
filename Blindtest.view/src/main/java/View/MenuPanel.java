/*
 * @author Amaury Chabane
 */
package View;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import Contract.IEntity;

/**
 * The Class MenuPanel.
 *
 * @author Amaury Chabane
 */
public class MenuPanel extends MyPanel {

    // 25 = (this.getWidth() / 51.2)
    // 50 = (this.getWidth() / 25.6)
    // 75 = (this.getWidth() / 17.1)

    /** The Constant ERROR_ICON. */
    private static BufferedImage ERROR_ICON = null;
    public static Image LOGO = null;

    /** The show index. */
    private int showIndex = 0;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6346376664434242649L;

    /**
     * Instantiates a new menu panel.
     *
     * @param viewFrame
     *                      the view frame
     * @param showIndex
     *                      the show index
     */
    public MenuPanel(ViewFrame viewFrame, int showIndex) {
        super(viewFrame);
        try {
            ERROR_ICON = ImageIO.read(new File("bin\\error_icon.png"));
            LOGO = ImageIO.read(new File("bin\\logo.png")).getScaledInstance(200, 112, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        MouseInputMenu mouseInput = new MouseInputMenu(this);
        this.setShowIndex(showIndex);
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
     * @param x
     *                     the x
     * @param y
     *                     the y
     */
    public void drawTheme(Graphics2D graphics, IEntity theme, double x, double y) {
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1)); // remove transparency
        Font basicFont = new Font(this.getFontName(), Font.BOLD,
                (int) ((this.getHeight() - LOGO.getHeight(null)) / 28.8));
        graphics.setColor(Color.BLACK);
        graphics.setFont(basicFont);
        Font miniFont = new Font(this.getFontName(), Font.BOLD, (this.getHeight() - LOGO.getHeight(null)) / 90);
        int rectX = (int) x + LOGO.getWidth(null);
        int rectY = (int) y + LOGO.getHeight(null);
        int rectW = (this.getWidth() - LOGO.getWidth(null)) / 11;
        int rectH = (rectW * 160) / 120;
        int errorX = (int) (rectX + rectW + ((this.getWidth() - LOGO.getWidth(null)) / 51.2));
        int errorY = (rectY + (rectH / 2)) - (((this.getHeight() - LOGO.getHeight(null)) / 15) / 2);
        int errorW = (this.getWidth() - LOGO.getWidth(null)) / 25;
        int titleX = (int) (rectX + rectW + ((this.getWidth() - LOGO.getWidth(null)) / 12.8));
        int titleY = rectY + (rectH / 2) + (graphics.getFont().getSize() / 2);
        graphics.drawString(theme.getTitle(), titleX, titleY);
        if (new File(theme.getCover()).exists()) {
            graphics.setColor(Color.BLACK);
            Point mousePos = this.getMousePosition();
            if (mousePos != null) {
                if ((mousePos.getX() >= rectX) && (mousePos.getX() <= (rectX + rectW))) {
                    if ((mousePos.getY() >= rectY) && (mousePos.getY() <= (rectY + rectH))) {
                        float alpha = (float) 0.5; // draw half transparent
                        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                        graphics.setComposite(ac);
                    }
                }
            }
            Dimension imgDim = this.scaleImageDimensions(theme.getCoverImage(), rectW, rectH);
            if ((theme.getThumbnailCoverImage() == null)
                    || (theme.getThumbnailCoverImage().getWidth(null) != (int) imgDim.getWidth())
                    || (theme.getThumbnailCoverImage().getHeight(null) != (int) imgDim.getHeight())) {
                theme.setThumbnailCoverImage((int) imgDim.getWidth(), (int) imgDim.getHeight());
            }
            rectX = (int) ((rectX + (rectW / 2)) - (imgDim.getWidth() / 2));
            rectY = (int) ((rectY + (rectH / 2)) - (imgDim.getHeight() / 2));
            rectW = (int) imgDim.getWidth();
            rectH = (int) imgDim.getHeight();

            this.drawImageRoundedCorners(graphics, theme.getThumbnailCoverImage(), 15, rectX, rectY, rectW, rectH);
            // graphics.drawImage(theme.getThumbnailCoverImage(), rectX, rectY, rectW,
            // rectH, Color.WHITE, null);
        } else {
            graphics.setBackground(Color.GRAY);
            graphics.clearRect(rectX, rectY, rectW, rectH);
            graphics.setColor(Color.BLACK);
            graphics.setFont(miniFont);
            this.drawCenteredString(graphics, "Image Not Found", rectX, rectY, rectW, rectH);
        }
        if (theme.isHasError()) {
            graphics.drawImage(ERROR_ICON.getScaledInstance(errorW, errorW, Image.SCALE_SMOOTH), errorX, errorY, errorW,
                    errorW, null);
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
        int btnW = this.getWidth() / 12;
        int btnH = this.getHeight() / 14;
        int optionsX = (int) (this.getWidth() - ((this.getWidth() / 51.2) * 2) - (this.getWidth() / 10) - btnW);
        int nextX = (int) (this.getWidth() - ((this.getWidth() / 51.2) * 3) - (this.getWidth() / 10) - (btnW * 2))
                + (btnW / 2);
        int previousX = (int) (this.getWidth() - ((this.getWidth() / 51.2) * 4) - (this.getWidth() / 10) - (btnW * 2));
        int refreshX = (int) (this.getWidth() - ((this.getWidth() / 51.2) * 5) - (this.getWidth() / 10) - (btnW * 3));
        int quitX = (int) (this.getWidth() / 51.2);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        this.drawBackground(graphics);
        graphics.drawImage(LOGO, 0, 0, null);
        this.drawButton(graphics, "Play", playX, btnY, btnW, btnH);
        this.drawButton(graphics, "Options", optionsX, btnY, btnW, btnH);
        this.drawButton(graphics, ">", nextX, btnY, btnW / 2, btnH);
        this.drawButton(graphics, "<", previousX, btnY, btnW / 2, btnH);
        this.drawButton(graphics, "Refresh", refreshX, btnY, btnW, btnH);
        this.drawButton(graphics, "Quit", quitX, btnY, btnW, btnH);
        for (IEntity theme : this.getViewFrame().getModel().getThemes()) {
            if (theme.isHasError()) {
                int errorX = (int) (((this.getWidth() / 51.2) * 2) + btnW);
                graphics.drawImage(ERROR_ICON.getScaledInstance(btnH, btnH, Image.SCALE_SMOOTH), errorX, btnY, btnH,
                        btnH, null);
                graphics.setFont(new Font(this.getFontName(), Font.BOLD, this.getHeight() / 36));
                graphics.setColor(new Color(255, 0, 0));
                List<String> strings = StringUtils.wrap("One or more themes are missing audio file...",
                        graphics.getFontMetrics(), (int) (this.getWidth() / 8.53));
                int Ystart = btnY;
                for (String string : strings) {
                    graphics.drawString(string, (int) (errorX + (this.getWidth() / 51.2) + btnH), Ystart);
                    Ystart += graphics.getFont().getSize();
                }
                break;
            }
        }
        for (int i = this.getShowIndex(); i < (this.getShowIndex() + 3); i++) {
            if (i < this.getViewFrame().getModel().getThemes().size()) {
                this.drawTheme(graphics, this.getViewFrame().getModel().getThemes().get(i),
                        (this.getWidth() - LOGO.getWidth(null)) / 51.2,
                        (int) ((((((i - this.getShowIndex()) + 1) * (this.getWidth() - LOGO.getWidth(null))) / 51.2)
                                + (((((this.getWidth() - LOGO.getWidth(null)) / 11) * 160) / 120)
                                        * (i - this.getShowIndex())))));
            }
        }
        for (int i = this.getShowIndex() + 3; i < (this.getShowIndex() + 6); i++) {
            if (i < this.getViewFrame().getModel().getThemes().size()) {
                this.drawTheme(graphics, this.getViewFrame().getModel().getThemes().get(i),
                        ((this.getWidth() - LOGO.getWidth(null)) / 51.2)
                                + ((this.getWidth() - LOGO.getWidth(null)) / 2),
                        (int) (((((i - 3 - this.getShowIndex()) + 1) * (this.getWidth() - LOGO.getWidth(null))) / 51.2))
                                + (((((this.getWidth() - LOGO.getWidth(null)) / 11) * 160) / 120)
                                        * (i - 3 - this.getShowIndex())));
            }
        }
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
