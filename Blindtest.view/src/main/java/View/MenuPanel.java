/*
 *
 */
package View;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.io.File;
import java.util.List;
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
    private final static String ERROR_ICON = "bin\\error_icon.png";

    /** The show index. */
    private int showIndex = 0;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6346376664434242649L;

    /**
     * Instantiates a new menu panel.
     *
     * @param viewFrame the view frame
     */
    public MenuPanel(ViewFrame viewFrame) {
        super(viewFrame);
        MouseInputMenu mouseInput = new MouseInputMenu(this);
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
    }

    /**
     * Draw theme.
     *
     * @param graphics the graphics
     * @param theme the theme
     * @param x the x
     * @param y the y
     */
    public void drawTheme(Graphics2D graphics, IEntity theme, double x, double y) {
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1)); // remove transparency
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
            graphics.drawImage(theme.getResizedCoverImage(), rectX, rectY, rectW, rectH, Color.WHITE, null);
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
     * @param g the g
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
                - ((this.getWidth() / 51.2) * 2) - (btnW * 2)) + (btnW / 2);
        int previousX = (int) (this.getWidth() - (this.getWidth() / 51.2) - (this.getWidth() / 10)
                - ((this.getWidth() / 51.2) * 3) - (btnW * 2));
        int refreshX = (int) (this.getWidth() - (this.getWidth() / 51.2) - (this.getWidth() / 10)
                - ((this.getWidth() / 51.2) * 4) - (btnW * 3));
        int quitX = (int) (this.getWidth() - (this.getWidth() / 51.2) - (this.getWidth() / 10)
                - ((this.getWidth() / 51.2) * 5) - (btnW * 4));
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.drawGradientPaint(graphics);
        this.drawButton(graphics, "Play", playX, btnY, btnW, btnH);
        this.drawButton(graphics, "Options", optionsX, btnY, btnW, btnH);
        this.drawButton(graphics, ">", nextX, btnY, btnW / 2, btnH);
        this.drawButton(graphics, "<", previousX, btnY, btnW / 2, btnH);
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
     * @param showIndex the new show index
     */
    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }
}
