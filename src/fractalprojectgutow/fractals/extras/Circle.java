package fractalprojectgutow.fractals.extras;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Math.sqrt;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Joshua Gutow
 */
public class Circle {

    private final int x;
    private final int y;
    private final int r;
    private final double k;

    public Circle(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
        k = 1.0 / r;
    }

    /**
     * Draws a circle centered on the points x & y with the given radius.
     *
     * @param g - Where to draw the circle.
     * @param c - Color to draw the circle with.
     */
    public void drawCircle(Graphics g, Color c) {
        g.setColor(c);
        g.drawOval(x - r, y - r, 2 * r, 2 * r);
    }

    /**
     * Returns a circle that is tangent with the given circles if the given
     * circles are mutually tangent at three point.
     *
     * @return Circle that is externally tangent to the given circles.
     */
    public static Circle soddyCircle(Circle a, Circle b, Circle c) {
        // Find the new radius
        double k_new = a.k + b.k + c.k + (2 * sqrt(a.k * b.k + b.k * c.k + a.k * c.k));
        int r = (int) (1 / k_new);

        // Find the new center.
        Complex ca = new Complex(a.x, a.y);
        Complex cb = new Complex(b.x, b.y);
        Complex cc = new Complex(c.x, c.y);

        // Complex descartes theorem. Really damn ugly. Does work however.
        // Maybe subtract the root to find the interior tangent circle?
        Complex root = ca.multiply(cb).multiply(a.k * b.k).add(cb.multiply(cc).multiply(b.k * c.k)).add(ca.multiply(cc).multiply(new Complex(a.k * c.k))).sqrt().multiply(2);
        Complex ans = ca.multiply(a.k).add(cb.multiply(b.k)).add(cc.multiply(c.k)).add(root).divide(k_new);

        return new Circle((int) ans.getReal(), (int) ans.getImaginary(), r);

    }

    public int getRadius() {
        return r;
    }

}
