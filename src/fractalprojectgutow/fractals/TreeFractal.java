package fractalprojectgutow.fractals;

import fractalprojectgutow.fractals.extras.RandColor;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Joshua Gutow
 */
public class TreeFractal extends JPanel {

    // Actual angle between the new branches is double this.
    private double ANGLE_DIFF = Math.toRadians(30);
    private double LEN_FRACTION = .5;
    private double SMALLEST_BRANCH = 1;
    private final RandColor randColor;

    private int bestLen;
    private int bestYStart;

    public TreeFractal(RandColor c) {
        randColor = c;
    }

    /**
     * This function calculates the maximum width of the tree fractal if the
     * length is one. This can be then used to calculate the ideal width for the
     * tree fractal
     *
     * @return - Width of fractal where the initial length is 1
     */
    private double widthSum() {
        double len = 1;
        double sum = 0.0;
        double angle = Math.PI / 2;
        double min_delta = 0.0001;

        do {
            len *= LEN_FRACTION;
            if (Math.cos(angle + ANGLE_DIFF) > Math.cos(angle - ANGLE_DIFF)) {
                angle += ANGLE_DIFF;
                sum += len * Math.cos(angle);
            } else {
                angle -= ANGLE_DIFF;
                sum += len * Math.cos(angle);
            }
        } while (len * 100 > min_delta);

        // This sum only accounts for half of the tree
        return 2 * sum;
    }

    /**
     * This function calculates the maximum height of the tree fractal if the
     * length is one. This can be then used to calculate the ideal height for
     * the tree fractal
     *
     * @return - Height of fractal where the initial length is 1
     */
    private double heightSum() {
        double sum = 1.0; // To account for 0th branch upwards
        double len = 1.0;
        double angle = Math.PI / 2;
        double min_delta = 0.0001;

        do {
            len *= LEN_FRACTION;
            if (Math.sin(angle + ANGLE_DIFF) > Math.sin(angle - ANGLE_DIFF)) {
                angle += ANGLE_DIFF;
                sum += len * Math.sin(angle);
            } else {
                angle -= ANGLE_DIFF;
                sum += len * Math.sin(angle);
            }
        } while (len > min_delta);

        return sum;
    }

    /**
     * This gets the best length of the fractal given a current window size.
     *
     * @return - Length to be used with the window size.
     */
    private int bestLength(double wSum, double hSum) {
        double widthLen = this.getWidth() / wSum;
        double heightLen = this.getHeight() / hSum;
        return widthLen < heightLen ? (int) widthLen : (int) heightLen;
    }

    private int bestYStart(double wSum, double hSum) {
        double widthLen = this.getWidth() / wSum;
        double heightLen = this.getHeight() / hSum;

        if (widthLen < heightLen) {
            double h = hSum * widthLen;
            double padding = (this.getHeight() - h) / 2;
            return this.getHeight() - (int) padding;
        }

        return this.getHeight();
    }

    private void updateStartConditions() {
        double wSum = widthSum();
        double hSum = heightSum();

        bestLen = bestLength(wSum, hSum);
        bestYStart = bestYStart(wSum, hSum);

    }

    /**
     * This draws a fractal tree given the start coordinates of a tree. This
     * works by drawing the current line, and then checking if the length is
     * great enough to draw a new line. If it can draw new lines, it draws two
     * lines, each offset by the angle specified in the local field ANGLE_DIFF.
     *
     * @param g - Window to draw it upon
     * @param x - X start coordinate
     * @param y - Y start coordinate
     * @param len - Length of the current line
     * @param rad - Angle between the line and the positive X axis
     * @param color - use this color if the line is small enough
     */
    private void drawTree(Graphics g, int x, int y, int len, double rad, Color color) {
        int new_len = (int) (len * LEN_FRACTION);
        Color c = randColor.getNewColor();
        drawPolarLine(g, x, y, len, rad, c);

        if (new_len > SMALLEST_BRANCH) {
            // Tip of the branch of the old polar line.
            int x2 = (int) (x + len * Math.cos(rad));
            int y2 = (int) (y - len * Math.sin(rad));

            drawTree(g, x2, y2, new_len, rad + ANGLE_DIFF, c);
            drawTree(g, x2, y2, new_len, rad - ANGLE_DIFF, c);
        }

    }

    /**
     * Draws a line given the initial conditions using polar notation.
     *
     * @param g - Graphics window to draw the line upon
     * @param x - X start coordinate
     * @param y - Y start coordinate
     * @param len - Length of the line
     * @param rad - Angle between the line and the positive X axis
     * @param c - use this color.
     */
    private void drawPolarLine(Graphics g, int x, int y, int len, double rad, Color c) {
        int x2 = (int) (x + len * Math.cos(rad));
        int y2 = (int) (y - len * Math.sin(rad));
        g.setColor(c);
        g.drawLine(x, y, x2, y2);
    }

    @Override
    public void paintComponent(Graphics g) {
        // reset background
        super.paintComponent(g);
        randColor.resetColors();
        updateStartConditions();
        drawTree(g, this.getWidth() / 2, bestYStart, bestLen, Math.PI / 2, randColor.getNewColor());
    }

    /**
     * Sets the fraction length of the tree. The integer should be in the range
     * [0-99], inclusive.
     */
    public void setBranchLenMult(int frac) {
        LEN_FRACTION = frac / 100.;
        repaint();
    }

    /**
     * Set the minium length of a branch.
     */
    public void setBranchSize(double minLen) {
        SMALLEST_BRANCH = minLen;
        repaint();
    }

    /**
     *
     */
    public void setAngleDiff(double angle) {
        ANGLE_DIFF = Math.toRadians(angle);
        repaint();
    }

}
