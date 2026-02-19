package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class SobelEdgeDetection implements EdgeDetectionAlgorithm {

    public static final int[][] HORIZONTAL_SOBEL_KERNEL = {
        {-1, 0, 1},
        {-2, 0, 2},
        {-1, 0, 1}
    };

    public static final int[][] VERTICAL_SOBEL_KERNEL = {
        {-1, -2, -1},
        {0, 0, 0},
        {1, 2, 1}
    };

    private final ImageAlgorithm grayscaleAlgorithm;

    public SobelEdgeDetection(ImageAlgorithm grayscaleAlgorithm) {
        if (grayscaleAlgorithm == null) {
            throw new IllegalArgumentException("Grayscale algorithm cannot be null");
        }
        this.grayscaleAlgorithm = grayscaleAlgorithm;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }

        BufferedImage grayscaleImage = grayscaleAlgorithm.process(image);
        int width = grayscaleImage.getWidth();
        int height = grayscaleImage.getHeight();

        BufferedImage edgeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int magnitude = calculateGradientMagnitude(grayscaleImage, x, y);
                int newPixelValue = ColorUtils.grayscaleToRGB(magnitude);  // Convert to RGB format
                edgeImage.setRGB(x, y, newPixelValue);
            }
        }
        return edgeImage;
    }

    private int calculateGradientMagnitude(BufferedImage image, int x, int y) {
        int gradientX = 0;
        int gradientY = 0;

        for (int ky = -1; ky <= 1; ky++) {
            for (int kx = -1; kx <= 1; kx++) {

                int gray = getGrayscaleValue(image, x + kx, y + ky);  // Get grayscale value
                gradientX += gray * HORIZONTAL_SOBEL_KERNEL[ky + 1][kx + 1];
                gradientY += gray * VERTICAL_SOBEL_KERNEL[ky + 1][kx + 1];

            }
        }
        return calculateMagnitude(gradientX, gradientY);
    }

    private int getGrayscaleValue(BufferedImage image, int x, int y) {
        int rgb = image.getRGB(x, y);

        return rgb & ColorUtils.MAX_COLOUR_VALUE; //(R = G = B) return the blue colour
    }

    private int calculateMagnitude(int gradientX, int gradientY) {
        int magnitude = Math.abs(gradientX) + Math.abs(gradientY);

        return Math.min(ColorUtils.MAX_COLOUR_VALUE, magnitude); //normalization [0, 255]
    }
}
