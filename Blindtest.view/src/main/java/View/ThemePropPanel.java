/*
 *
 */
package View;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.File;

import Contract.IEntity;

public class ThemePropPanel extends MyPanel {

    private int themeIndex = 0;

    /**
     *
     */
    private static final long serialVersionUID = -359666814951181068L;

    public ThemePropPanel(ViewFrame viewFrame, int themeIndex) {
        super(viewFrame);
        MouseInputThemeProp mouseInput = new MouseInputThemeProp(this);
        this.themeIndex = themeIndex;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
    }

    public void drawTheme(Graphics2D graphics, IEntity theme) {
        int metaYStart = (int) (this.getWidth() / 51.2);
        int titleHeight = (int) (this.getHeight() / 28.8) + graphics.getFont().getSize();
        int composerHeight = (int) (this.getHeight() / 14.4) + titleHeight + graphics.getFont().getSize();
        int dateHeight = (int) (this.getHeight() / 14.4) + composerHeight + graphics.getFont().getSize();
        Font font = new Font("Cooper Black", Font.BOLD, (int) (this.getHeight() / 28.8));
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);

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

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        int quitX = (int) (this.getWidth() / 51.2);
        int btnY = (int) (this.getHeight() - (this.getWidth() / 51.2) - (this.getHeight() / 10.2857143));
        int btnW = this.getWidth() / 10;
        int btnH = this.getHeight() / 12;
        int menuX = (int) (this.getWidth() / 51.2) + quitX + btnW;
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.drawGradientPaint(graphics);
        IEntity theme = this.getViewFrame().getModel().getThemes().get(this.themeIndex);
        this.drawTheme(graphics, theme);

        this.drawButton(graphics, "Quit", quitX, btnY, btnW, btnH);
        this.drawButton(graphics, "Menu", menuX, btnY, btnW, btnH);
    }

}
