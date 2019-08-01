/*
 *
 */
package View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 * The Class LoadPanel.
 *
 * @author Amaury Chabane
 */
public class LoadPanel extends MyPanel {

    /** The image. */
    private Image image = null;

    /** The rotate. */
    private Thread rotate = null;

    /** The rotate factor. */
    private double rotateFactor = 0;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4875013586052666221L;

    /**
     * Instantiates a new load panel.
     *
     * @param viewFrame the view frame
     */
    public LoadPanel(ViewFrame viewFrame) {
        super(viewFrame);
        this.setRotate(new Thread() {
            @Override
            public void run() {
                while (true) {
                    LoadPanel.this.setRotateFactor(LoadPanel.this.getRotateFactor() + 0.1);
                    LoadPanel.this.repaint();
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        this.setImage(this.loadImage("bin\\vinyl_icon.png"));
        this.getRotate().start();
    }

    /**
     * Paint component.
     *
     * @param g the g
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        int imgW = (int) (this.getHeight() / 2.4);
        int imgX = (this.getWidth() / 2) - (imgW / 2);
        int imgY = (this.getHeight() / 2) - (imgW / 2);
        Graphics2D graphics = (Graphics2D) g;
        this.drawGradientPaint(graphics);
        graphics.rotate(this.getRotateFactor(), this.getWidth() / 2, this.getHeight() / 2);
        graphics.drawImage(this.getImage(), imgX, imgY, imgW, imgW, null);
    }

    /**
     * Gets the rotate.
     *
     * @return the rotate
     */
    public Thread getRotate() {
        return this.rotate;
    }

    /**
     * Sets the rotate.
     *
     * @param rotate the new rotate
     */
    public void setRotate(Thread rotate) {
        this.rotate = rotate;
    }

    /**
     * Gets the rotate factor.
     *
     * @return the rotate factor
     */
    public double getRotateFactor() {
        return this.rotateFactor;
    }

    /**
     * Sets the rotate factor.
     *
     * @param d the new rotate factor
     */
    public void setRotateFactor(double d) {
        this.rotateFactor = d;
    }

    /**
     * Gets the image.
     *
     * @return the image
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Sets the image.
     *
     * @param image the new image
     */
    public void setImage(Image image) {
        this.image = image;
    }

}
