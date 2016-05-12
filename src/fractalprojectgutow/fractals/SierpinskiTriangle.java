package fractalprojectgutow.fractals;

import fractalprojectgutow.fractals.extras.RandColor;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Joshua Gutow
 */
public class SierpinskiTriangle extends JPanel {

    // Specifies how large the groupings of a single color should be.
    private int MIN_COLOR_GROUP = 0;
    private int MIN_LEN = 20;
    private final RandColor randColor;

    public SierpinskiTriangle(RandColor c) {
        randColor = c;
    }

    /**
     * This is the main recursive function. If the length is too small, this
     * function draws a triangle, otherwise it splits up the triangle per the
     * rules of Sierpinski Triangle fractal.
     *
     * @param g - Window to draw the triangle on
     * @param x - X coordinate of lower left point
     * @param y - Y coordinate of lower left point
     * @param len - How long the base & height of the triangle are
     * @param c - use this color unless the triangle is small enough
     */
    private void DrawSierpinskiTriangle(Graphics g, int x, int y, int len, Color color) {
        Color c;
        if (len > MIN_LEN * MIN_COLOR_GROUP) {
            c = randColor.getNewColor();
        } else {
            c = color;
        }

        if (MIN_LEN > len) {
            fillTriangle(g, x, y, len, c);
        } else {

            DrawSierpinskiTriangle(g, x, y, len / 2, c);
            DrawSierpinskiTriangle(g, x + len / 2, y, len / 2, c);
            DrawSierpinskiTriangle(g, x + len / 4, y - len / 2, len / 2, c);
        }
    }

    /**
     * This draws a triangle starting from the bottom left of the triangle where
     * the base and height are equal to len, and the top vertex is directly
     * above the midpoint of the base.
     *
     * (x,y) is the bottom left corner of the triangle. (x + (len/2), y + len)
     * is the top. (x + len, y) is the bottom right corner of the triangle. In
     * addition, c is the color.
     */
    private void fillTriangle(Graphics g, int x, int y, int len, Color c) {
        int[] xArr = {x, x + len / 2, x + len};
        int[] yArr = {y, y - len, y};
        g.setColor(c);
        g.fillPolygon(xArr, yArr, 3);
    }

    /**
     * When called, this will paint the graphics window with Sierpinski
     * Triangles.
     *
     * @param g - Graphics window to draw on
     */
    @Override
    public void paintComponent(Graphics g) {
        // reset background
        super.paintComponent(g);
        // reset colors
        randColor.resetColors();

        int x;
        int len;
        int y;
        // Intelligent window resizing
        if (this.getHeight() < this.getWidth()) {
            x = (this.getWidth() - this.getHeight()) / 2;
            len = this.getHeight();
            y = len;
        } else {
            x = 0;
            len = this.getWidth();
            y = this.getHeight() - ((this.getHeight() - len) / 2);
        }

        DrawSierpinskiTriangle(g, x, y, len, randColor.getNewColor());
    }

    public void setSize(int minSize) {
        MIN_LEN = minSize;
        repaint();
    }

    public void setColorSize(int colorGroups) {
        MIN_COLOR_GROUP = colorGroups;
        repaint();
    }

}
