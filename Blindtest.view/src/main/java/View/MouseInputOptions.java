/*
 * @author Amaury Chabane
 */
package View;

import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;

/**
 * The Class MouseInputOptions.
 *
 * @author Amaury Chabane
 */
public class MouseInputOptions extends MouseInput {

    /** The back X. */
    private int backX;
    
    /** The btn Y. */
    private int btnY;
    
    /** The btn W. */
    private int btnW;
    
    /** The btn H. */
    private int btnH;
    
    /** The next X. */
    private int nextX;
    
    /** The previous X. */
    private int previousX;
    
    /** The add X. */
    private int addX;

    /**
     * Instantiates a new mouse input options.
     *
     * @param optionsPanel the options panel
     */
    public MouseInputOptions(OptionsPanel optionsPanel) {
        super(optionsPanel);

        this.backX = (int) (this.panel.getWidth() - (this.panel.getWidth() / 51.2) - (this.panel.getWidth() / 10));
        this.btnY = (int) (this.panel.getHeight() - (this.panel.getWidth() / 51.2)
                - (this.panel.getHeight() / 10.2857143));
        this.btnW = this.panel.getWidth() / 12;
        this.btnH = this.panel.getHeight() / 14;
        this.nextX = (int) (this.panel.getWidth() - ((this.panel.getWidth() / 51.2) * 2) - (this.panel.getWidth() / 10)
                - this.btnW) + (this.btnW / 2);
        this.previousX = (int) (this.panel.getWidth() - ((this.panel.getWidth() / 51.2) * 3)
                - (this.panel.getWidth() / 10) - this.btnW);

        this.addX = (int) (this.panel.getWidth() - ((this.panel.getWidth() / 51.2) * 4) - (this.panel.getWidth() / 10)
                - (this.btnW * 2));
    }

    /**
     * Mouse pressed.
     *
     * @param e the e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if ((my >= this.btnY) && (my <= (this.btnY + this.btnH))) {
            if ((mx >= this.nextX) && (mx <= (this.nextX + this.btnW))) {
                if (this.viewframe.getController().getModel().getTypes()
                        .size() > ((((OptionsPanel) this.panel).getShowIndex() + 16))) {
                    for (JCheckBox checkboxe : ((OptionsPanel) this.panel).getCheckboxes()) {
                        if (checkboxe.isSelected()) {
                            this.viewframe.getController().removeType(checkboxe.getText());
                        } else {
                            boolean checked = false;
                            System.out.println(this.viewframe.getController().getNotChoosenTypes());
                            for (String type : this.viewframe.getController().getNotChoosenTypes()) {
                                if (type.equalsIgnoreCase(checkboxe.getText())) {
                                    checked = true;
                                    break;
                                }
                            }
                            if (!checked) {
                                this.viewframe.getController().addType(checkboxe.getText());
                            }
                        }
                    }
                    ((OptionsPanel) this.panel).removeCheckboxes();
                    ((OptionsPanel) this.panel).setShowIndex(((OptionsPanel) this.panel).getShowIndex() + 16);
                    ((OptionsPanel) this.panel).createCheckboxes();
                    this.panel.repaint();
                }
            } else if ((mx >= this.previousX) && (mx <= (this.previousX + this.btnW))) {
                if (((OptionsPanel) this.panel).getShowIndex() > 0) {
                    for (JCheckBox checkboxe : ((OptionsPanel) this.panel).getCheckboxes()) {
                        if (checkboxe.isSelected()) {
                            this.viewframe.getController().removeType(checkboxe.getText());
                        } else {
                            boolean checked = false;
                            for (String type : this.viewframe.getController().getNotChoosenTypes()) {
                                if (type.equalsIgnoreCase(checkboxe.getText())) {
                                    checked = true;
                                    break;
                                }
                            }
                            if (!checked) {
                                this.viewframe.getController().addType(checkboxe.getText());
                            }
                        }
                    }
                    ((OptionsPanel) this.panel).removeCheckboxes();
                    ((OptionsPanel) this.panel).setShowIndex(((OptionsPanel) this.panel).getShowIndex() - 16);
                    ((OptionsPanel) this.panel).createCheckboxes();
                    this.panel.repaint();
                }
            } else if ((mx >= this.backX) && (mx <= (this.backX + this.btnW))) {
                try {
                    this.viewframe.getController()
                            .setAllowedTime(Integer.parseInt(((OptionsPanel) this.panel).getTimeField().getText()));
                } catch (NumberFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                for (JCheckBox checkboxe : ((OptionsPanel) this.panel).getCheckboxes()) {
                    if (checkboxe.isSelected()) {
                        this.viewframe.getController().removeType(checkboxe.getText());
                    } else {
                        boolean checked = false;
                        for (String type : this.viewframe.getController().getNotChoosenTypes()) {
                            if (type.equalsIgnoreCase(checkboxe.getText())) {
                                checked = true;
                                break;
                            }
                        }
                        if (!checked) {
                            this.viewframe.getController().addType(checkboxe.getText());
                        }
                    }
                }
                this.viewframe.setContentPane(new MenuPanel(this.viewframe, 0));
                this.viewframe.revalidate();
            } else if ((mx >= this.addX) && (mx <= (this.addX + this.addX))) {
                this.viewframe.setContentPane(new AddThemePanel(this.viewframe));
                this.viewframe.revalidate();
            }
        }

    }

    /**
     * Mouse moved.
     *
     * @param e the e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        int btnY = (int) ((this.panel.getHeight() - (this.panel.getWidth() / 51.2)
                - (this.panel.getHeight() / 10.2857143)) - 200);
        if (e.getY() >= btnY) {
            this.panel.repaint(0, btnY, this.panel.getWidth(), this.panel.getHeight());
        }
    }
}
