/*
 * @author Amaury Chabane
 */
package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

/**
 * The Class OptionsPanel.
 *
 * @author Amaury Chabane
 */
public class OptionsPanel extends MyPanel implements ActionListener {

    /** The show index. */
    private int showIndex = 0;

    /** The checkboxes. */
    private List<JCheckBox> checkboxes = new ArrayList<>();

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8514600715581652162L;

    /** The time field. */
    private JTextField timeField = null;

    /**
     * Instantiates a new options panel.
     *
     * @param viewFrame
     *                      the view frame
     */
    public OptionsPanel(ViewFrame viewFrame) {
        super(viewFrame);
        MouseInputOptions mouseInput = new MouseInputOptions(this);

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);

        this.setLayout(null);

        this.createCheckboxes();
        this.setTimeField(this.createTextField(String.valueOf(this.getViewFrame().getController().getAllowedTime()),
                this.getViewFrame().getWidth() / 3,
                (int) (this.getViewFrame().getHeight() - (this.getViewFrame().getWidth() / 51.2)
                        - (this.getViewFrame().getHeight() / 10.2857143)),
                this.getViewFrame().getWidth() / 12, this.getViewFrame().getHeight() / 14));
    }

    /**
     * Creates the checkboxes.
     */
    public void createCheckboxes() {
        this.getCheckboxes().clear();

        Font basicFont = new Font(this.getFontName(), Font.BOLD, this.getViewFrame().getHeight() / 15);

        for (int i = this.getShowIndex(); i < (this.getShowIndex() + 8); i++) {
            if (i < this.getViewFrame().getModel().getTypes().size()) {
                String type = this.getViewFrame().getModel().getTypes().get(i);
                JCheckBox chckbx = new JCheckBox(type.replace('_', ' '), true);
                for (String typeToCheck : this.getViewFrame().getController().getNotChoosenTypes()) {
                    if (typeToCheck.replace('_', ' ').equalsIgnoreCase(type.replace('_', ' '))) {
                        chckbx = new JCheckBox(type.replace('_', ' '), false);
                    }
                }

                chckbx.setBounds((int) (this.getViewFrame().getWidth() / 25.6),
                        ((this.getViewFrame().getHeight() / 36) + basicFont.getSize()) * (i - this.getShowIndex()),
                        basicFont.getSize() * type.length(), basicFont.getSize());
                chckbx.setOpaque(false);
                chckbx.setForeground(Color.BLACK);
                chckbx.setFont(basicFont);
                this.add(chckbx);
                this.getCheckboxes().add(chckbx);
            }
        }
        for (int i = this.getShowIndex() + 8; i < (this.getShowIndex() + 16); i++) {
            if (i < this.getViewFrame().getModel().getTypes().size()) {
                String type = this.getViewFrame().getModel().getTypes().get(i);
                JCheckBox chckbx = new JCheckBox(type.substring(1).replace('_', ' '), true);
                for (String typeToCheck : this.getViewFrame().getController().getNotChoosenTypes()) {
                    if (typeToCheck.replace('_', ' ').equalsIgnoreCase(type.replace('_', ' '))) {
                        chckbx = new JCheckBox(type.toString().replace('_', ' '), false);
                    }
                }
                chckbx.setBounds((int) ((this.getViewFrame().getWidth() / 2) + (this.getViewFrame().getWidth() / 25.6)),
                        ((this.getViewFrame().getHeight() / 36) + basicFont.getSize()) * (i - this.getShowIndex() - 8),
                        basicFont.getSize() * type.length(), basicFont.getSize());
                chckbx.setOpaque(false);
                chckbx.setForeground(Color.BLACK);
                chckbx.setFont(basicFont);
                this.add(chckbx);
                this.getCheckboxes().add(chckbx);
            }
        }
    }

    /**
     * Removes the checkboxes.
     */
    public void removeCheckboxes() {
        for (Component comp : this.getComponents()) {
            if (comp instanceof JCheckBox) {
                this.remove(comp);
            }
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
        int backX = (int) (this.getWidth() - (this.getWidth() / 51.2) - (this.getWidth() / 10));
        int btnY = (int) (this.getHeight() - (this.getWidth() / 51.2) - (this.getHeight() / 10.2857143));
        int btnW = this.getWidth() / 12;
        int btnH = this.getHeight() / 14;
        int nextX = (int) (this.getWidth() - ((this.getWidth() / 51.2) * 2) - (this.getWidth() / 10) - btnW)
                + (btnW / 2);
        int previousX = (int) (this.getWidth() - ((this.getWidth() / 51.2) * 3) - (this.getWidth() / 10) - btnW);
        int addX = (int) (this.getWidth() - ((this.getWidth() / 51.2) * 4) - (this.getWidth() / 10) - (btnW * 2));
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        this.drawGradientPaint(graphics);
        this.drawButton(graphics, "Back", backX, btnY, btnW, btnH);
        this.drawButton(graphics, ">", nextX, btnY, btnW / 2, btnH);
        this.drawButton(graphics, "<", previousX, btnY, btnW / 2, btnH);
        this.drawButton(graphics, "Add new", addX, btnY, btnW, btnH);
        graphics.setColor(Color.BLACK);
        graphics.setFont(graphics.getFont().deriveFont((float) (this.getHeight() / 22)));
        int metaXStart = (int) (this.getWidth() / 51.2);
        int timeHeight = this.getTimeField().getBounds().y + graphics.getFont().getSize();
        graphics.drawString("Time to guess (seconds):", metaXStart, timeHeight);
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

    /**
     * Gets the time field.
     *
     * @return the time field
     */
    public JTextField getTimeField() {
        return this.timeField;
    }

    /**
     * Sets the time field.
     *
     * @param timeField
     *                      the new time field
     */
    public void setTimeField(JTextField timeField) {
        this.timeField = timeField;
    }

}
