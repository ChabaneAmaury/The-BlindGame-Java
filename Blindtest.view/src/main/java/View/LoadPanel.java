/*
 *
 */
package View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class LoadPanel extends MyPanel {

    private Image image = null;

    private Thread rotate = null;

    private double rotateFactor = 0;

    /**
     *
     */
    private static final long serialVersionUID = 4875013586052666221L;

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

    public Thread getRotate() {
        return this.rotate;
    }

    public void setRotate(Thread rotate) {
        this.rotate = rotate;
    }

    public double getRotateFactor() {
        return this.rotateFactor;
    }

    public void setRotateFactor(double d) {
        this.rotateFactor = d;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
