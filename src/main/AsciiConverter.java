import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Image;

public class AsciiConverter {
    private static final String ASCII_CHARS = "@%#*+=-:. ";
    
    /**
     * converts an image to ascii, first changing the color to gray then each color is converted into special character based on color tone
     * @param image an image file 
     * @return the appended special characters
     */
    public static String convertToAscii(BufferedImage image) {
        StringBuilder asciiArtStr = new StringBuilder();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                double gray = (pixelColor.getRed() + pixelColor.getGreen() + pixelColor.getBlue()) / 3.0;   //divided by 3.0 to calculate the average
                int index = Math.min((int) (gray * (ASCII_CHARS.length() / 255.0)), ASCII_CHARS.length() - 1); //255 is max range for grayness
                asciiArtStr.append(ASCII_CHARS.charAt(index));
            }
            asciiArtStr.append("\n");
        }
        return asciiArtStr.toString();
    }

    /**
     * This class handles the size of any image to predetermined size
     * @param originalImage an image
     * @param targetWidth the adjusted width of originalImage
     * @param targetHeight the adjusted height of originalImage
     * @return an image on canvas
     * @throws IOException
     */
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        // since the result is elongated in height, adjust height to account for character aspect ratio
        double aspectRatioCorrection = 0.5; // This value might need tweaking based on your font/display
        int correctedHeight = (int) (targetHeight * aspectRatioCorrection);

        Image resultingImage = originalImage.getScaledInstance(targetWidth, correctedHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, correctedHeight,  BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public static void printAsciiImg(File file) throws Exception{
        BufferedImage image = ImageIO.read(file); 
        BufferedImage resizedImage = resizeImage(image, 100, 100); // resized image into console canvas
        String asciiArt = convertToAscii(resizedImage); //converting image into special characters
        System.out.println(asciiArt); // Print to console (for testing)
    }
}


