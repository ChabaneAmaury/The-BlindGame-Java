/*
 *
 */
package View;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import Contract.Difficulties;
import Contract.Types;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class OptionsPanel.
 *
 * @author Amaury Chabane
 */
public class OptionsPanel extends JPanel implements ActionListener {

    /** The view frame. */
    private ViewFrame viewFrame = null;

    /** The checkboxes. */
    private List<JCheckBox> checkboxes = new ArrayList<>();

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8514600715581652162L;

    /**
     * Instantiates a new options panel.
     *
     * @param viewFrame
     *                      the view frame
     */
    public OptionsPanel(ViewFrame viewFrame) {
        MouseInputOptions mouseInput = new MouseInputOptions(this);
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
        this.setViewFrame(viewFrame);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.initOptions();
    }

    /**
     * Inits the options.
     */
    public void initOptions() {

        int separator = this.getViewFrame().getHeight() / 72;
        Font basicFont = new Font("Cooper Black", Font.BOLD, (int) (this.getViewFrame().getHeight() / 28.8));

        this.getCheckboxes().removeAll(this.checkboxes);

        for (Types type : Types.values()) {
            JCheckBox chckbx = new JCheckBox(
                    (type.toString().substring(0, 1) + type.toString().substring(1).toLowerCase()).replace('_', ' '),
                    true);
            chckbx.setOpaque(false);
            chckbx.setForeground(Color.WHITE);
            chckbx.setFont(basicFont);
            chckbx.setAlignmentX(CENTER_ALIGNMENT);
            this.add(chckbx);
            this.add(Box.createRigidArea(new Dimension(separator, separator)));
        }

        JButton easy = new JButton("Easy");
        easy.setOpaque(false);
        easy.setFont(basicFont);
        easy.setAlignmentX(CENTER_ALIGNMENT);
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionsPanel.this.getViewFrame().getController().setAllowedTime(Difficulties.EASY);
            }
        });
        JButton medium = new JButton("Medium");
        medium.setOpaque(false);
        medium.setFont(basicFont);
        medium.setAlignmentX(CENTER_ALIGNMENT);
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionsPanel.this.getViewFrame().getController().setAllowedTime(Difficulties.MEDIUM);
            }
        });
        JButton hard = new JButton("Hard");
        hard.setOpaque(false);
        hard.setFont(basicFont);
        hard.setAlignmentX(CENTER_ALIGNMENT);
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionsPanel.this.getViewFrame().getController().setAllowedTime(Difficulties.HARD);
            }
        });
        this.add(Box.createRigidArea(new Dimension(separator * 9, separator * 9)));
        this.add(easy);
        this.add(Box.createRigidArea(new Dimension(separator, separator)));
        this.add(medium);
        this.add(Box.createRigidArea(new Dimension(separator, separator)));
        this.add(hard);
        this.add(Box.createRigidArea(new Dimension(separator, separator)));

        for (Component comp : this.getComponents()) {
            if (comp instanceof JCheckBox) {
                this.getCheckboxes().add((JCheckBox) comp);
            }
        }
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
     * Paint component.
     *
     * @param g
     *              the g
     */
    @Override
    protected void paintComponent(final Graphics g) {
        int backX = (int) (this.getWidth() - (this.getWidth() / 51.2) - (this.getWidth() / 10));
        int btnY = (int) (this.getHeight() - (this.getWidth() / 51.2) - (this.getHeight() / 10.2857143));
        int btnW = this.getWidth() / 10;
        int btnH = this.getHeight() / 12;
        int quitX = (int) (this.getWidth() / 51.2);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.drawGradientPaint(graphics);
        this.drawButton(graphics, "Back", backX, btnY, btnW, btnH);
        this.drawButton(graphics, "Quit", quitX, btnY, btnW, btnH);
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
     * Gets the checkboxes.
     *
     * @return the checkboxes
     */
    public List<JCheckBox> getCheckboxes() {
        return this.checkboxes;
    }

    /**
     * Sets the checkboxes.
     *
     * @param checkboxes
     *                       the new checkboxes
     */
    public void setCheckboxes(List<JCheckBox> checkboxes) {
        this.checkboxes = checkboxes;
    }

    /**
     * Action performed.
     *
     * @param e
     *              the e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}
