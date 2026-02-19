package bg.sofia.uni.fmi.mjt.imagekit.filesystem.imageformat;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class ImageFormatExtractor {

    public static String extractImageFormat(File imageFile) throws IOException {
        // Validate file existence
        if (imageFile == null || !imageFile.exists()) {
            throw new IOException(
                "The specified file does not exist: " + (imageFile != null ? imageFile.getAbsolutePath() : "null"));
        }

        // Create ImageInputStream
        try (ImageInputStream imageInputStream = ImageIO.createImageInputStream(imageFile)) {
            if (imageInputStream == null) {
                throw new IOException(
                    "Could not create an ImageInputStream for the file: " + imageFile.getAbsolutePath());
            }

            // Get available image readers for the file
            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);

            // If no readers are found, format is unknown
            if (!readers.hasNext()) {
                throw new IOException("Could not determine the image format for file: " + imageFile.getAbsolutePath());
            }

            ImageReader reader = readers.next();
            String formatName = reader.getFormatName();

            return formatName.toLowerCase();
        } catch (IOException e) {
            throw new IOException("Error determining the image format for file: " + imageFile.getAbsolutePath(), e);
        }
    }
}

