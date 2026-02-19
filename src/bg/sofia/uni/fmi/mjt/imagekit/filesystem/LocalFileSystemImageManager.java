package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import bg.sofia.uni.fmi.mjt.imagekit.filesystem.imageformat.SupportedImageFormat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocalFileSystemImageManager implements FileSystemImageManager {

    @Override
    public BufferedImage loadImage(File imageFile) throws IOException {
        if (imageFile == null) {
            throw new IllegalArgumentException("File cannot be null");
        }

        if (!Files.isRegularFile(imageFile.toPath()) || !imageFile.exists()) {
            throw new IOException("Invalid file: file does not exist or is not a regular file");
        }

        if (!SupportedImageFormat.isSupported(imageFile.getName())) { //getFileName
            throw new IOException("File format is not supported");
        }

        BufferedImage image = ImageIO.read(imageFile); //handles resources management internally
        if (image == null) {
            throw new IOException("Failed to read image from file: " + imageFile.getAbsolutePath());
        }
        return image;
    }

    @Override
    public List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException {
        if (imagesDirectory == null) {
            throw new IllegalArgumentException("The directory cannot be null.");
        }

        if (!imagesDirectory.exists() || !imagesDirectory.isDirectory()) {
            throw new IOException("The specified directory does not exist or is not a directory: "
                + imagesDirectory.getAbsolutePath());
        }

        List<BufferedImage> images = new ArrayList<>();
        File[] files = imagesDirectory.listFiles();

        if (files == null) {
            throw new IOException("Unable to list files in the directory: " + imagesDirectory.getAbsolutePath());
        }

        for (File file : files) {
            processImageFile(file, images);
        }
        return images;
    }

    private void processImageFile(File file, List<BufferedImage> images) throws IOException {
        if (!file.exists() || !file.isFile()) {
            return; // Skip non-regular files
        }

        if (SupportedImageFormat.isSupported(file.getName())) {
            BufferedImage image = ImageIO.read(file);
            if (image != null) {
                images.add(image);
            } else {
                throw new IOException("Failed to read image file: " + file.getAbsolutePath());
            }
        } else {
            throw new IOException("Unsupported image format for file: " + file.getAbsolutePath());
        }
    }

    @Override
    public void saveImage(BufferedImage image, File imageFile) throws IOException {
        if (image == null || imageFile == null) {
            throw new IllegalArgumentException("Image and image file cannot be null");
        }

        File parentDirectory = imageFile.getParentFile();
        if (parentDirectory == null || !parentDirectory.exists() || !parentDirectory.isDirectory()) { //isDirectory???
            throw new IOException("Parent directory does not exist or is invalid: " + parentDirectory);
        }

        if (imageFile.exists()) {
            throw new IOException("File already exists: " + imageFile.getName());
        }

        // Extract format from file extension
        //String format = SupportedImageFormat.getFormatFromFileName(imageFile.getName());
        SupportedImageFormat format = SupportedImageFormat.getFormatFromFileName(imageFile.getName());
        if (format == null || !SupportedImageFormat.isSupported(format)) {
            throw new IOException("Unsupported file format: " + format);
        }

        // Write image
        if (!ImageIO.write(image, format.name().toLowerCase(Locale.ROOT), imageFile)) {
            throw new IOException("Failed to write image to file: " + imageFile);
        }
    }
}
