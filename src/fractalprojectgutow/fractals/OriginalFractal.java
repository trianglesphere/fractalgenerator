package fractalprojectgutow.fractals;

import fractalprojectgutow.fractals.extras.RandColor;
import fractalprojectgutow.fractals.extras.Circle;
import static fractalprojectgutow.fractals.extras.Circle.soddyCircle;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Joshua Gutow
 *
 */
public class OriginalFractal extends JPanel {

    private final static int MIN_RADIUS = 1;
    private final RandColor randColor;

    public OriginalFractal(RandColor c) {
        randColor = c;
    }

    /**
     * Draws an Apollian gasket fractal. It is given three circles, and draws
     * the soddy circle, and then tries to repeat the operation.
     *
     * @param g - Window to draw upon
     * @param a - First circle
     * @param b - Second circle
     * @param c - Third circle
     */
    private void drawFractal(Graphics g, Circle a, Circle b, Circle c) {
        Circle d = soddyCircle(a, b, c);
        if (d.getRadius() > MIN_RADIUS) {
            d.drawCircle(g, randColor.getNewColor());
            drawFractal(g, a, b, d);
            drawFractal(g, a, d, c);
            drawFractal(g, d, b, c);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        // reset background
        super.paintComponent(g);
        randColor.resetColors();
        Circle[] c = initialCircles();
        drawFractal(g, c[0], c[1], c[2]);
    }

    private Circle[] initialCircles() {
        Circle[] arr = new Circle[3];

        int x; // Not a coord, but a var
        int r; // Radius of the circles
        int center; // Center x coord of the fractal
        int h_offset; // How much to move the fractal down the page.

        if (this.getWidth() / 2 < (int) (this.getHeight() / Math.sqrt(3))) {
            x = this.getWidth() / 2;
            h_offset = ((int) (this.getHeight() - (x * Math.sqrt(3)))) / 2;
        } else {
            x = (int) (this.getHeight() / Math.sqrt(3));
            h_offset = 0;
        }
        r = 2 * x;
        center = this.getWidth() / 2;

        arr[0] = new Circle(center - r, 0 + h_offset, r);
        arr[1] = new Circle(center + r, 0 + h_offset, r);
        arr[2] = new Circle(center, (int) (2 * x * Math.sqrt(3)) + h_offset, r);

        return arr;
    }

}
