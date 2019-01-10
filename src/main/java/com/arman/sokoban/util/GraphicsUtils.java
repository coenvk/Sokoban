package com.arman.sokoban.util;

import java.awt.*;
import java.awt.image.*;

public class GraphicsUtils {

    private GraphicsUtils() {

    }

    public static void drawCenteredString(Graphics g, String text, Font font, Rectangle rect) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public static void drawCenteredString(Graphics g, String text, Font font, int x, int y, int width, int height) {
        drawCenteredString(g, text, font, new Rectangle(x, y, width, height));
    }

    public static Image filterImage(Image image, Color filterColor) {
        ImageFilter filter = new RGBImageFilter() {
            int transparent = filterColor.getRGB() | 0xFF000000;
            @Override
            public int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == transparent) {
                    return 0x00FFFFFF & rgb;
                } else {
                    return rgb;
                }
            }
        };
        ImageProducer filteredImgProd = new FilteredImageSource(image.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(filteredImgProd);
    }

    public static Image resizeImage(Image original, int resizedWidth, int resizedHeight) {
        Image resized = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = resized.getGraphics();
        g.drawImage(original, 0, 0, resizedWidth, resizedHeight, null);
        g.dispose();
        return resized;
    }

    public static Image resizeImageWithRatio(Image original, int resizedWidth, int resizedHeight) {
        int newHeight = original.getHeight(null);
        int newWidth = original.getWidth(null);
        if (original.getWidth(null) > resizedWidth) {
            newWidth = resizedWidth;
            newHeight = (newWidth * original.getHeight(null)) / original.getWidth(null);
        }
        if (newHeight > resizedHeight) {
            newHeight = resizedHeight;
            newWidth = (newHeight * original.getWidth(null)) / original.getHeight(null);
        }
        return resizeImage(original, newWidth, newHeight);
    }

    public static void drawCenteredImage(Graphics g, Image image, int x, int y, int width, int height) {
        x += (width - image.getWidth(null)) / 2;
        y += (height - image.getHeight(null)) / 2;
        g.drawImage(image, x, y, null);
    }

}
