/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalprojectgutow.fractals.extras;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Joshua Gutow
 *
 * This class allows access to the same random group of colors.
 */
public class RandColor {

    private final int MAX_COLORS = 400;
    private Color[] colors = new Color[MAX_COLORS];
    private final Color[] randColors = new Color[MAX_COLORS];
    private final Color[] changedColors = new Color[MAX_COLORS];
    private int pos = 0;
    private final Random rand;

    /**
     * This creates a new RandColor object that is used to get a random color
     * while being able to get the same set of random colors.
     *
     */
    public RandColor(String generator) {
        rand = new Random();
        genChangedColors();
        genRandColors();

        colors = randColors;
    }

    /**
     * This sets the mode to rand if passed true. Sets the mode to changed if
     * passed false.
     */
    public void setRand(boolean bool) {
        if (bool) {
            colors = randColors;
        } else {
            colors = changedColors;
        }
    }

    /**
     * Resets the index of the next color so that the following colors pulled
     * out will always be the same from any given instance.
     */
    public void resetColors() {
        pos = 0;
    }

    /**
     * This gets the next color. It can be called an infinite amount of times.
     * If it is called more MAX_COLORS, defaulted to 400, it will just loop
     * around to the start of the array.
     *
     * @return - New random color. Related to the previous color by the mode.
     */
    public Color getNewColor() {
        if (pos == colors.length - 1) {
            pos = 0;
        }
        return colors[pos++];
    }

    private void genRandColors() {
        for (int i = 0; i < randColors.length; i++) {
            randColors[i] = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        }
    }

    private void genChangedColors() {
        changedColors[0] = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        for (int i = 1; i < changedColors.length; i++) {
            changedColors[i] = changedColor(changedColors[i - 1]);
        }
    }

    /**
     * Generates a new color that is slightly different than the given color
     *
     * @param c - color to be based upon
     *
     * @return Color that is slightly different than the parameter
     */
    private Color changedColor(Color c) {
        float r = changeChannel(c.getRed() / 255f);
        float g = changeChannel(c.getGreen() / 255f);
        float b = changeChannel(c.getBlue() / 255f);

        return new Color(r, g, b);
    }

    /**
     * This changes the given float so that the resulting float is between 0.0
     * and 1.0 inclusive.
     *
     * @param prev - Channel to be slightly modified
     * @return float in range 0-1 inclusive.
     */
    private float changeChannel(float prev) {
        // Generate a float in the range (0,.2) inc.
        float delta = rand.nextInt(3) / 10f;
        // First, if possible, do random addition / subtraction,
        // If not sure about overflow, do the safest thing to do.
        if (prev > .2 && prev < .8) {
            if (rand.nextInt(2) == 1) {
                return prev + delta;
            } else {
                return prev - delta;
            }
        } else if (prev <= .2) {
            return prev + delta;
        } else if (prev >= .8) {
            return prev - delta;
        }
        // Should never happen, but its a safe default
        return prev;
    }

    /**
     * Re-randomizes the colors.
     */
    public void regenColors() {
        genChangedColors();
        genRandColors();
    }

}
