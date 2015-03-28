/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security.ui;

import java.awt.Color;

/**
 * A series of color manipulation routines that can be used while compiling /
 * pre-processing ZUSS CSS files. These methods are called directly by the CSS
 * compiler to produce derivative color values.
 *
 * Methods include: - darken - lighten - saturate - and desaturate - fade - to
 * another color - highlight - both on dark and light backgrounds - shadow -
 * both on dark and light backgrounds
 */
public class ColorUtils {

    //DARKEN
    /**
     * Darkens a given color by the specified percentage.
     *
     * @param sColor a string containing the HTML hex value of the rgb color
     * @param sPercent percentage to darken. Needs to be 0.0 - 1.0 or 2% - 100%
     * @return a new "#FFFFFF" HTML color string with the desired
     * characteristics.
     * @exception IllegalArgumentException if the specified percentage value is
     * unacceptable
     */
    public static String darken(String sColor, String sPercent) throws IllegalArgumentException {
        Color color = Color.decode(sColor);
        float percent = getValidPercent(sPercent, false);
        return ColorUtils.darken(color, percent).toString();
    }

    /**
     * Darkens a given color by the specified percentage.
     *
     * @param color the Color to darken.
     * @param percent percentage to darken. Needs to be 0.0 - 1.0 or 2 - 100
     * @return a new Color with the desired characteristics.
     * @exception IllegalArgumentException if the specified percentage value is
     * unacceptable
     */
    public static Color darken(Color color, float percent) throws IllegalArgumentException {
        float[] hsbVals = new float[3];
        hsbVals = ColorUtils.getHSB(color, hsbVals);
        float brightness = hsbVals[2] * (1.0f - percent);
        hsbVals[2] = getValidPercent(brightness, false);

        return ColorUtils.getColor(hsbVals);
    }

    //LIGHTEN
    /**
     * Lightens a given color by the specified percentage.
     *
     * @param sColor a string containing the HTML hex value of the rgb color
     * @param sPercent percentage to lighten. Needs to be 0.0 - 1.0 or 2% - 100%
     * @return a new "#FFFFFF" HTML color string with the desired
     * characteristics.
     * @exception IllegalArgumentException if the specified percentage value is
     * unacceptable
     */
    public static String lighten(String sColor, String sPercent) throws IllegalArgumentException {
        Color color = Color.decode(sColor);
        float percent = getValidPercent(sPercent, false);
        return ColorUtils.lighten(color, percent).toString();
    }

    /**
     * Lightens a given color by the specified percentage.
     *
     * @param color the Color to lighten.
     * @param percent percentage to lighten. Needs to be 0.0 - 1.0 or 2 - 100
     * @return a new Color with the desired characteristics.
     * @exception IllegalArgumentException if the specified percentage value is
     * unacceptable
     */
    public static Color lighten(Color color, float percent) throws IllegalArgumentException {
        float[] hsbVals = new float[3];
        hsbVals = ColorUtils.getHSB(color, hsbVals);
        float brightness = hsbVals[2] * (1.0f + percent);
        hsbVals[2] = getValidPercent(brightness, false);

        //See if we maxed out the brightness ( > 1.0 )
        //If so, then desaturate by the amount over the brightness max
        if (brightness > 1.0f) {
            hsbVals[1] = hsbVals[1] * (1.0f - (brightness - 1.0f));
        }

        return ColorUtils.getColor(hsbVals);
    }

    //DESATURATE
    /**
     * Saturate a given color by the specified percentage.
     *
     * @param sColor a string containing the HTML hex value of the rgb color
     * @param sPercent percentage to saturate or desaturate. Needs to be 0.0 -
     * 1.0 or 2% - 100%; Negative values permitted.
     * @return a new "#FFFFFF" HTML color string with the desired
     * characteristics.
     * @exception IllegalArgumentException if the specified percentage value is
     * unacceptable
     */
    public static String saturate(String sColor, String sPercent) throws IllegalArgumentException {
        Color color = Color.decode(sColor);
        float percent = getValidPercent(sPercent, false);
        return ColorUtils.saturate(color, percent).toString();
    }

    /**
     * Desaturates a given color by the specified percentage.
     *
     * @param color the Color to desaturate.
     * @param percent percentage to desaturate. Needs to be 0.0 - 1.0 or 2 - 100
     * @return a new Color with the desired characteristics.
     * @exception IllegalArgumentException if the specified percentage value is
     * unacceptable
     */
    public static Color saturate(Color color, float percent) throws IllegalArgumentException {
        float[] hsbVals = new float[3];
        hsbVals = ColorUtils.getHSB(color, hsbVals);
        float saturation = hsbVals[1] * (1.0f + percent);
        hsbVals[1] = getValidPercent(saturation, true);

        return ColorUtils.getColor(hsbVals);
    }

    //FADE
    /**
     * Fades from one color to another by the given percentage.
     *
     * @param sFromColor the Color to fade from.
     * @param sToColor the Color to fade to.
     * @param sPercent percentage to lighten. Needs to be 0.0 - 1.0 or 2% - 100%
     * @return a new Color with the desired characteristics.
     * @exception IllegalArgumentException if the specified percentage value is
     * unacceptable
     */
    public static String fade(String sFromColor, String sToColor, String sPercent) throws IllegalArgumentException {
        Color fromColor = Color.decode(sFromColor);
        Color toColor = Color.decode(sToColor);
        float percent = getValidPercent(sPercent, false);

        return ColorUtils.fade(fromColor, toColor, percent).toString();
    }

    /**
     * Fades from one color to another by the given percentage.
     *
     * @param from the Color to fade from.
     * @param to the Color to fade to.
     * @param percent percentage to lighten. Needs to be 0.0 - 1.0 or 2 - 100
     * @return a new Color with the desired characteristics.
     * @exception IllegalArgumentException if the specified percentage value is
     * unacceptable
     */
    public static Color fade(Color from, Color to, float percent) throws IllegalArgumentException {
        int r, g, b;

        int fromRed = from.getRed();
        int fromGreen = from.getGreen();
        int fromBlue = from.getBlue();

        int toRed = to.getRed();
        int toGreen = to.getGreen();
        int toBlue = to.getBlue();

        percent = getValidPercent(percent, false);

        //RED
        if (fromRed > toRed) {
            r = toRed + (int) ((fromRed - toRed) * (1 - percent));
        } else {
            r = toRed - (int) ((toRed - fromRed) * (1 - percent));
        }

        //GREEN
        if (fromGreen > toRed) {
            g = toGreen + (int) ((fromGreen - toGreen) * (1 - percent));
        } else {
            g = toGreen - (int) ((toGreen - fromGreen) * (1 - percent));
        }

        //BLUE
        if (fromBlue > toBlue) {
            b = toBlue + (int) ((fromBlue - toBlue) * (1 - percent));
        } else {
            b = toBlue - (int) ((toBlue - fromBlue) * (1 - percent));
        }

        return new Color(r, g, b);
    }

    /**
     * Used to calculate a highlight color from a given color.
     *
     * @param c The color to use in the calculation. If null, then it will
     * return null.
     * @return the newly calculated highlight color.
     */
    public static String highlight(String sColor, String sPercent) throws IllegalArgumentException {
        Color color = Color.decode(sColor);
        float percent = getValidPercent(sPercent, true);
        return ColorUtils.highlight(color, percent).toString();
    }

    public static Color highlight(Color c, float percent) {
        if (c == null) {
            return null;
        }

        //SATURATION
        c = saturate(c, percent);

        //LIGHTNESS
        double lightness = lightness(c);

        if (lightness >= 0.90) {
            return (ColorUtils.darken(c, 0.100f));
        } else if (lightness <= 0.20) {
            return (ColorUtils.lighten(c, 0.600f));
        } else {
            return (ColorUtils.lighten(c, 0.600f));
        }
    }

    /**
     * Used to calculate a shadow color from a given color.
     *
     * @param c The color to use in the calculation If null, then it will return
     * null.
     * @return the newly calculated shadow color.
     */
    public static String shadow(String sColor, String sPercent) throws IllegalArgumentException {
        Color color = Color.decode(sColor);
        float percent = getValidPercent(sPercent, true);
        return ColorUtils.shadow(color, percent).toString();
    }

    public static Color shadow(Color c, float percent) {
        if (c == null) {
            return null;
        }

        //SATURATION
        c = saturate(c, percent);

        //LIGHTNESS
        double lightness = lightness(c);

        if (lightness >= 0.90) {
            return (ColorUtils.darken(c, 0.250f));
        } else if (lightness <= 0.20) {
            return (ColorUtils.lighten(c, 0.200f));
        } else {
            return (ColorUtils.darken(c, 0.250f));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    //
    // INTERNAL HELPER METHODS
    //
    ////////////////////////////////////////////////////////////////////////////////////////////
    //Color from Hue/Saturation/Brightness values
    public static Color getColor(float hue, float saturation, float brightness) throws NumberFormatException {
        return new Color(java.awt.Color.HSBtoRGB(hue, saturation, brightness));
    }

    //Color from Hue/Saturation/Brightness values array
    public static Color getColor(float[] hsb) throws NumberFormatException {
        int rgb = java.awt.Color.HSBtoRGB( /*hue*/hsb[0], /*saturation*/ hsb[1], /*brightness*/ hsb[2]) & 0x00FFFFFF;
        int red = (rgb & 0xFF0000) >> 16;
        int green = (rgb & 0xFF00) >> 8;
        int blue = (rgb & 0xFF) >> 0;
        return new Color(red, green, blue); //NOTE: constructor that takes a single RGB int is broken -- new Color ( int ) --  it does not properly shift values into 0 - 255 range
    }

    //Get Hue/Saturation/Brightness values from Color object
    public static float[] getHSB(Color color, float[] hsbVals) {
        return java.awt.Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsbVals);
    }

    //Returns a number between 0.0 - 1.0
    private static float getValidPercent(String sPercent, boolean bPermitNegatives) {
        //Trim unwanted characters
        if (sPercent.charAt(sPercent.length() - 1) == '%') {
            sPercent = sPercent.substring(0, sPercent.length() - 1);
        }

        return ColorUtils.getValidPercent(Float.parseFloat(sPercent), bPermitNegatives);
    }

    private static float getValidPercent(float percent, boolean bPermitNegatives) {
        if (bPermitNegatives == false) {
            percent = Math.abs(percent);  //Get rid of negative numbers
        }
        //Fix out-of-range values
        float absPercent = Math.abs(percent);
        int isNegative = (percent < 0) ? -1 : 1;

        if (absPercent > 100) //Don't allow any number greater than 100
        {
            percent = 1.0f * isNegative;
        } else if (absPercent >= 2.0) //Convert 2% - 100% to .02 - 1.0
        {
            percent = percent / 100;
        }

        absPercent = Math.abs(percent);
        if (absPercent > 1.0) //Fix numbers out of range:  1.0 - 2.0
        {
            percent = 1.0f * isNegative;
        }

        return percent;
    }

    /**
     * Given a Color this function determines the lightness percent.
     *
     * @param color The Color to calculate from. If null, it will return 0.
     * @return the percent light of the specified color. This value will be >= 0
     * && <= 1.
     */
    private static double lightness(Color color) {
        if (color == null) {
            return 0;
        }
        double r = color.getRed();
        double g = color.getGreen();
        double b = color.getBlue();
        return Math.sqrt((.299 * r * r) + (.587 * g * g) + (.114f * b * b)) / 255;
    }

}
