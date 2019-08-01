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
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

import javax.swing.JTextField;

import Contract.IEntity;

public class ThemePropPanel extends MyPanel {

    @SuppressWarnings("serial")
    public class RoundJTextField extends JTextField {
        private Shape shape;

        public RoundJTextField(int size) {
            super(size);
            this.setOpaque(false); // As suggested by @AVD in comment.
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(this.getBackground());
            g.fillRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 15, 15);
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(this.getForeground());
            g.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 15, 15);
        }

        @Override
        public boolean contains(int x, int y) {
            if ((this.shape == null) || !this.shape.getBounds().equals(this.getBounds())) {
                this.shape = new RoundRectangle2D.Float(0, 0, this.getWidth() - 1, this.getHeight() - 1, 15, 15);
            }
            return this.shape.contains(x, y);
        }
    }

    private JTextField titleField = null;
    private JTextField composerField = null;
    private JTextField typeField = null;
    private JTextField timeCodeField = null;
    private JTextField releaseField = null;
    private JTextField infosField = null;
    boolean activate = false;

    private IEntity theme = null;

    /**
     *
     */
    private static final long serialVersionUID = -359666814951181068L;

    public ThemePropPanel(ViewFrame viewFrame, int themeIndex) {
        super(viewFrame);
        MouseInputThemeProp mouseInput = new MouseInputThemeProp(this);
        this.setTheme(this.getViewFrame().getModel().getThemes().get(themeIndex));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);

        this.setLayout(null);
        int fieldHeightFactor = ((this.getViewFrame().getHeight() / 16) * 2);
        this.setTitleField(this.createTextField(this.getTheme().getTitle(),
                (int) (this.getViewFrame().getWidth() / 51.2), (int) (this.getViewFrame().getHeight() / 14.4),
                (int) (this.getViewFrame().getWidth() / 1.8), this.getViewFrame().getHeight() / 18));
        this.setComposerField(this.createTextField(this.getTheme().getComposer(),
                (int) (this.getViewFrame().getWidth() / 51.2), this.getTitleField().getBounds().y + fieldHeightFactor,
                (int) (this.getViewFrame().getWidth() / 1.8), this.getViewFrame().getHeight() / 18));
        this.setTypeField(this.createTextField(this.getTheme().getType(), (int) (this.getViewFrame().getWidth() / 51.2),
                this.getComposerField().getBounds().y + fieldHeightFactor, (int) (this.getViewFrame().getWidth() / 1.8),
                this.getViewFrame().getHeight() / 18));
        this.setTimeCodeField(this.createTextField(Integer.toString(this.getTheme().getTimecode()),
                (int) (this.getViewFrame().getWidth() / 51.2), this.getTypeField().getBounds().y + fieldHeightFactor,
                (int) (this.getViewFrame().getWidth() / 1.8), this.getViewFrame().getHeight() / 18));
        this.setReleaseField(
                this.createTextField(this.getTheme().getReleaseDate(), (int) (this.getViewFrame().getWidth() / 51.2),
                        this.getTimeCodeField().getBounds().y + fieldHeightFactor,
                        (int) (this.getViewFrame().getWidth() / 1.8), this.getViewFrame().getHeight() / 18));
        this.setInfosField(this.createTextField(this.getTheme().getInfos(),
                (int) (this.getViewFrame().getWidth() / 51.2), this.getReleaseField().getBounds().y + fieldHeightFactor,
                (int) (this.getViewFrame().getWidth() / 1.8), this.getViewFrame().getHeight() / 18));

    }

    public RoundJTextField createTextField(String string, int x, int y, int width, int height) {
        RoundJTextField textField = new RoundJTextField(0);
        textField.setFont(new Font("Cooper Black", Font.PLAIN, 25));
        textField.setText(string);
        textField.setForeground(Color.BLACK);
        textField.setBounds(x, y, width, height);
        this.add(textField);
        return textField;
    }

    public void drawTheme(Graphics2D graphics, IEntity theme) {
        int metaXStart = (int) (this.getWidth() / 51.2);
        int titleHeight = this.getTitleField().getBounds().y - graphics.getFont().getSize();
        int composerHeight = this.getComposerField().getBounds().y - graphics.getFont().getSize();
        int typeHeight = this.getTypeField().getBounds().y - graphics.getFont().getSize();
        int timecodeHeight = this.getTimeCodeField().getBounds().y - graphics.getFont().getSize();
        int dateHeight = this.getReleaseField().getBounds().y - graphics.getFont().getSize();
        int infosHeight = this.getInfosField().getBounds().y - graphics.getFont().getSize();
        Font font = new Font("Cooper Black", Font.BOLD, (int) (this.getHeight() / 28.8));
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);

        int border = this.getHeight() / 240;
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
        graphics.drawString("Title :", metaXStart, titleHeight);
        graphics.drawString("Composer :", metaXStart, composerHeight);
        graphics.drawString("Type :", metaXStart, typeHeight);
        graphics.drawString("Timecode :", metaXStart, timecodeHeight);
        graphics.drawString("Release Date :", metaXStart, dateHeight);
        graphics.drawString("Infos :", metaXStart, infosHeight);
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
        this.drawTheme(graphics, this.getTheme());

        this.drawButton(graphics, "Quit", quitX, btnY, btnW, btnH);
        this.drawButton(graphics, "Menu", menuX, btnY, btnW, btnH);
    }

    public IEntity getTheme() {
        return this.theme;
    }

    public void setTheme(IEntity theme) {
        this.theme = theme;
    }

    public JTextField getTitleField() {
        return this.titleField;
    }

    public void setTitleField(JTextField titleField) {
        this.titleField = titleField;
    }

    public JTextField getComposerField() {
        return this.composerField;
    }

    public void setComposerField(JTextField composerField) {
        this.composerField = composerField;
    }

    public JTextField getTypeField() {
        return this.typeField;
    }

    public void setTypeField(JTextField typeField) {
        this.typeField = typeField;
    }

    public JTextField getTimeCodeField() {
        return this.timeCodeField;
    }

    public void setTimeCodeField(JTextField timeCodeField) {
        this.timeCodeField = timeCodeField;
    }

    public JTextField getReleaseField() {
        return this.releaseField;
    }

    public void setReleaseField(JTextField releaseField) {
        this.releaseField = releaseField;
    }

    public JTextField getInfosField() {
        return this.infosField;
    }

    public void setInfosField(JTextField infosField) {
        this.infosField = infosField;
    }

}
