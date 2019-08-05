/*
 *
 */
package View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JCheckBox;

/**
 * The Class MouseInputOptions.
 *
 * @author Amaury Chabane
 */
public class MouseInputOptions implements MouseListener, MouseMotionListener {

    /** The options panel. */
    private OptionsPanel optionsPanel = null;

    /**
     * Instantiates a new mouse input options.
     *
     * @param optionsPanel the options panel
     */
    public MouseInputOptions(OptionsPanel optionsPanel) {
        this.optionsPanel = optionsPanel;
    }

    /**
     * Mouse clicked.
     *
     * @param e the e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

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

        int backX = (int) (this.getOptionsPanel().getWidth() - (this.getOptionsPanel().getWidth() / 51.2)
                - (this.getOptionsPanel().getWidth() / 10));
        int btnY = (int) (this.getOptionsPanel().getHeight() - (this.getOptionsPanel().getWidth() / 51.2)
                - (this.getOptionsPanel().getHeight() / 10.2857143));
        int btnW = this.getOptionsPanel().getWidth() / 10;
        int btnH = this.getOptionsPanel().getHeight() / 12;
        int quitX = (int) (this.getOptionsPanel().getWidth() / 51.2);

        if ((my >= btnY) && (my <= (btnY + btnH))) {
            if ((mx >= quitX) && (mx <= (quitX + btnW))) {
                System.exit(0);
            } else if ((mx >= backX) && (mx <= (backX + btnW))) {
                this.getOptionsPanel().getViewFrame().getController().getNotChoosenTypes().clear();
                for (JCheckBox checkboxe : this.getOptionsPanel().getCheckboxes()) {
                    if (checkboxe.isSelected()) {
                        this.getOptionsPanel().getViewFrame().getController().removeType(checkboxe.getText());
                    } else {
                        this.getOptionsPanel().getViewFrame().getController().addType(checkboxe.getText());
                    }
                }
                this.getOptionsPanel().getViewFrame()
                        .setContentPane(new MenuPanel(this.getOptionsPanel().getViewFrame()));
                this.getOptionsPanel().getViewFrame().revalidate();
            }
        }

    }

    /**
     * Gets the options panel.
     *
     * @return the options panel
     */
    private OptionsPanel getOptionsPanel() {
        return this.optionsPanel;
    }

    /**
     * Mouse released.
     *
     * @param e the e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Mouse entered.
     *
     * @param e the e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Mouse exited.
     *
     * @param e the e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    /**
     * Mouse dragged.
     *
     * @param e the e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Mouse moved.
     *
     * @param e the e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        int btnY = (int) ((this.getOptionsPanel().getHeight() - (this.getOptionsPanel().getWidth() / 51.2)
                - (this.getOptionsPanel().getHeight() / 10.2857143)) - 200);
        if (e.getY() >= btnY) {
            this.getOptionsPanel().repaint(0, btnY, this.getOptionsPanel().getWidth(),
                    this.getOptionsPanel().getHeight());
        }
    }
}
