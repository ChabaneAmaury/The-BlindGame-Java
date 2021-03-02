/*
 * @author Amaury Chabane
 */
package View;

import Contract.TimeFormatter;

import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;

/**
 * The Class MouseInputOptions.
 *
 * @author Amaury Chabane
 */
public class MouseInputOptions extends MouseInput {

    /**
     * Instantiates a new mouse input options.
     *
     * @param optionsPanel the options panel
     */
    public MouseInputOptions(OptionsPanel optionsPanel) {
        super(optionsPanel);
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

        /** The back X. */
        int backX = (int) (this.panel.getWidth() - (this.panel.getWidth() / 51.2) - (this.panel.getWidth() / 10));
        /** The btn Y. */
        int btnY = (int) (this.panel.getHeight() - (this.panel.getWidth() / 51.2)
                - (this.panel.getHeight() / 10.2857143));
        /** The btn W. */
        int btnW = this.panel.getWidth() / 12;
        /** The btn H. */
        int btnH = this.panel.getHeight() / 14;
        /** The next X. */
        int nextX = (int) (this.panel.getWidth() - ((this.panel.getWidth() / 51.2) * 2) - (this.panel.getWidth() / 10)
                - btnW) + (btnW / 2);
        /** The previous X. */
        int previousX = (int) (this.panel.getWidth() - ((this.panel.getWidth() / 51.2) * 3)
                - (this.panel.getWidth() / 10) - btnW);

        /** The add X. */
        int addX = (int) (this.panel.getWidth() - ((this.panel.getWidth() / 51.2) * 4) - (this.panel.getWidth() / 10)
                - (btnW * 2));

        if ((my >= btnY) && (my <= (btnY + btnH))) {
            if ((mx >= nextX) && (mx <= (nextX + btnW))) {
                if (this.viewframe.getController().getModel().getTypes()
                        .size() > ((((OptionsPanel) this.panel).getShowIndex() + 16))) {
                    for (JCheckBox checkboxe : ((OptionsPanel) this.panel).getCheckboxes()) {
                        if (checkboxe.isSelected()) {
                            this.viewframe.getController().removeType(checkboxe.getText());
                        } else {
                            boolean checked = false;
                            System.out.println(TimeFormatter.getTimestamp() + this.viewframe.getController().getNotChoosenTypes());
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
            } else if ((mx >= previousX) && (mx <= (previousX + btnW))) {
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
            } else if ((mx >= backX) && (mx <= (backX + btnW))) {
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
            } else if ((mx >= addX) && (mx <= (addX + addX))) {
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
