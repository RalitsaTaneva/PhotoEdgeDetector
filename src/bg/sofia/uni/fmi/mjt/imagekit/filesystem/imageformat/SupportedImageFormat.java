package bg.sofia.uni.fmi.mjt.imagekit.filesystem.imageformat;

public enum SupportedImageFormat {
    JPEG(".jpeg"),
    PNG(".png"),
    BMP(".bmp");

    private final String extension;

    SupportedImageFormat(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static boolean isSupported(String fileName) {
        for (SupportedImageFormat format : values()) {
            if (fileName.toLowerCase().endsWith(format.getExtension())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSupported(SupportedImageFormat imageFormat) {
        for (SupportedImageFormat supportedFormat : values()) {
            if (supportedFormat.equals(imageFormat)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Extracts the format from the given file name and returns it in lowercase (for use with ImageIO).
     *
     * @param fileName the name of the file
     * @return the format name in lowercase (e.g., "jpeg", "png", "bmp") if supported; null otherwise
     */
    public static SupportedImageFormat getFormatFromFileName(String fileName) {
        for (SupportedImageFormat format : values()) {
            if (fileName.toLowerCase().endsWith(format.getExtension())) { //to lower case???
                return format; // .png
            }
        }
        return null; // Unsupported format
    }
}
