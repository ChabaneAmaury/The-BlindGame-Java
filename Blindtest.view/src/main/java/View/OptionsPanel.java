/*
 *
 */
package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;

import Contract.Difficulties;

/**
 * The Class OptionsPanel.
 *
 * @author Amaury Chabane
 */
public class OptionsPanel extends MyPanel implements ActionListener {

    /** The difficulty. */
    private String difficulty = "Easy";

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
        super(viewFrame);
        MouseInputOptions mouseInput = new MouseInputOptions(this);
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.initOptions();
    }

    /**
     * Inits the options.
     */
    public void initOptions() {

        // int separator = this.getViewFrame().getHeight() / 72;
        Font basicFont = new Font("Cooper Black", Font.BOLD, (int) (this.getViewFrame().getHeight() / 28.8));

        this.getCheckboxes().removeAll(this.checkboxes);

        for (String type : this.getViewFrame().getModel().getTypes()) {
            JCheckBox chckbx = new JCheckBox(
                    (type.toString().substring(0, 1) + type.toString().substring(1).toLowerCase()).replace('_', ' '),
                    true);
            chckbx.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            chckbx.setOpaque(false);
            chckbx.setForeground(Color.WHITE);
            chckbx.setFont(basicFont);
            chckbx.setAlignmentX(CENTER_ALIGNMENT);
            this.add(chckbx);
            // this.add(Box.createRigidArea(new Dimension(separator, separator)));
            for (String notChoosenType : this.getViewFrame().getController().getNotChoosenTypes()) {
                if (notChoosenType.equals(type)) {
                    chckbx.setSelected(false);
                }
            }
        }

        JButton easy = new JButton("Easy");
        easy.setOpaque(false);
        easy.setFont(basicFont);
        easy.setAlignmentX(CENTER_ALIGNMENT);
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionsPanel.this.getViewFrame().getController().setAllowedTime(Difficulties.EASY);
                OptionsPanel.this.setDifficulty("Easy");
                OptionsPanel.this.repaint();
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
                OptionsPanel.this.setDifficulty("Medium");
                OptionsPanel.this.repaint();
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
                OptionsPanel.this.setDifficulty("Hard");
                OptionsPanel.this.repaint();
            }
        });
        // this.add(Box.createRigidArea(new Dimension(separator * 9, separator * 9)));
        this.add(easy);
        // this.add(Box.createRigidArea(new Dimension(separator, separator)));
        this.add(medium);
        // this.add(Box.createRigidArea(new Dimension(separator, separator)));
        this.add(hard);
        // this.add(Box.createRigidArea(new Dimension(separator, separator)));

        for (Component comp : this.getComponents()) {
            if (comp instanceof JCheckBox) {
                this.getCheckboxes().add((JCheckBox) comp);
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
        int btnW = this.getWidth() / 10;
        int btnH = this.getHeight() / 12;
        int quitX = (int) (this.getWidth() / 51.2);
        int difficultyX = ((int) (this.getWidth() / 51.2) * 2) + btnW;
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.drawGradientPaint(graphics);
        this.drawButton(graphics, "Back", backX, btnY, btnW, btnH);
        this.drawButton(graphics, "Quit", quitX, btnY, btnW, btnH);
        graphics.setColor(Color.WHITE);
        this.drawCenteredString(graphics, this.getDifficulty(), difficultyX, btnY, btnW, btnH);
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
     * Gets the difficulty.
     *
     * @return the difficulty
     */
    public String getDifficulty() {
        return this.difficulty;
    }

    /**
     * Sets the difficulty.
     *
     * @param difficulty
     *                       the new difficulty
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

}
