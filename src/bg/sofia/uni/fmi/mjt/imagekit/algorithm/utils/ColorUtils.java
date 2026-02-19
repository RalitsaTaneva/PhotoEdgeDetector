package bg.sofia.uni.fmi.mjt.imagekit.algorithm.utils;

public class ColorUtils {

    public static final short MAX_COLOUR_VALUE = 255;
    public static final short MIN_COLOUR_VALUE = 0;

    private static final short RED_SHIFT = 16;
    private static final short GREEN_SHIFT = 8;

    // Extract the red component from an RGB value
    public static int getRed(int rgb) {
        return (rgb >> RED_SHIFT) & MAX_COLOUR_VALUE; // Shift right by 16 bits to get the red component
    }

    // Extract the green component from an RGB value
    public static int getGreen(int rgb) {
        return (rgb >> GREEN_SHIFT) & MAX_COLOUR_VALUE; // Shift right by 8 bits to get the green component
    }

    // Extract the blue component from an RGB value
    public static int getBlue(int rgb) {
        return rgb & MAX_COLOUR_VALUE; // The blue component is the last 8 bits
    }

    public static int grayscaleToRGB(int grayscale) {
        if (grayscale < MIN_COLOUR_VALUE || grayscale > MAX_COLOUR_VALUE) {
            throw new IllegalArgumentException("Grayscale value must be between 0 and 255");
        }
        return (grayscale << RED_SHIFT) | (grayscale << GREEN_SHIFT) | grayscale;
    }
}
