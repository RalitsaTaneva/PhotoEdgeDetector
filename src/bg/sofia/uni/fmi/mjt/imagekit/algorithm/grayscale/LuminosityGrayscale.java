package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class LuminosityGrayscale implements GrayscaleAlgorithm {

    public static final double RED_COEFFICIENT = 0.21;
    public static final double GREEN_COEFFICIENT = 0.72;
    public static final double BLUE_COEFFICIENT = 0.07;

    @Override
    public BufferedImage process(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int grayscale = calculateGrayscaleValue(image, x, y);
                int newRGB = ColorUtils.grayscaleToRGB(grayscale);

                grayscaleImage.setRGB(x, y, newRGB);
            }
        }
        return grayscaleImage;
    }

    //private  static?
    private int calculateGrayscaleValue(BufferedImage image, int x, int y) {
        int rgb = image.getRGB(x, y);

        int red = ColorUtils.getRed(rgb);
        int green = ColorUtils.getGreen(rgb);
        int blue = ColorUtils.getBlue(rgb);

        return (int)(red * RED_COEFFICIENT + green * GREEN_COEFFICIENT + blue * BLUE_COEFFICIENT);
    }
}