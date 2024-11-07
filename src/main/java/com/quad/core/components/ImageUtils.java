package com.quad.core.components;

import java.util.ArrayList;

import com.quad.core.fx.Image;

import fr.crusche.beziermanagement.BezierCurve;
import fr.crusche.beziermanagement.Point;

public class ImageUtils {

    public static Image darkenImage(Image image, double d) {
        int width = image.width;
        int height = image.height;
        int[] pixels = image.pixels.clone(); // Clone the pixel array to avoid modifying the original image

        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];

            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = pixel & 0xff;

            // Reduce the brightness of each color component
            red = (int) (red * d);
            green = (int) (green * d);
            blue = (int) (blue * d);

            // Ensure the values are within the valid range [0, 255]
            red = Math.max(0, Math.min(255, red));
            green = Math.max(0, Math.min(255, green));
            blue = Math.max(0, Math.min(255, blue));

            // Combine the components back into a single pixel value
            pixels[i] = (red << 16) | (green << 8) | blue;
        }

        return new Image(width, height, pixels);
    }

    public static Image lightenImage(Image image, float factor) {
        int width = image.width;
        int height = image.height;
        int[] pixels = image.pixels.clone(); // Clone the pixel array to avoid modifying the original image

        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];

            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = pixel & 0xff;

            // Increase the brightness of each color component
            red = (int) (red * factor);
            green = (int) (green * factor);
            blue = (int) (blue * factor);

            // Ensure the values are within the valid range [0, 255]
            red = Math.max(0, Math.min(255, red));
            green = Math.max(0, Math.min(255, green));
            blue = Math.max(0, Math.min(255, blue));

            // Combine the components back into a single pixel value
            pixels[i] = (red << 16) | (green << 8) | blue;
        }

        return new Image(width, height, pixels);
    }

    public static Image BWtoW(Image image, float factor) {
        // Convert the image to black and white
        // to a white image
        // with a given factor
        int width = image.width;
        int height = image.height;
        int[] pixels = image.pixels.clone(); // Clone the pixel array to avoid modifying the original image

        BezierCurve bc = new BezierCurve(
                new ArrayList<Point>() {
                    {
                        add(new Point(0, 0));
                        add(new Point(1, Math.pow(factor, 3) * 256));
                        add(new Point(101 - factor * 100, factor * 256));
                        add(new Point(256, factor * 256));
                    }
                }, 1);

        bc.calculatePoints(10);

        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];

            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = pixel & 0xff;

            // Calculate the average of the color components
            float average = (red + green + blue) / (3 * 255);

            // System.out.println(bc.getPointWithX(factor));

            // Increase the brightness of each color component
            int gradient = (int) bc.getPointWithX(average);

            if (gradient > 255) {
                gradient = 255;
            }

            // Combine the components back into a single pixel value
            pixels[i] = (gradient << 16) | (gradient << 8) | gradient;
        }

        return new Image(width, height, pixels);
    }

    private static void MultiplyMatrix(float[][] a, float[][] b) {
        float[][] c = new float[1][3];

    }
}