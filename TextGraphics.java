package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class TextGraphics implements  TextGraphicsConverter {
    protected double maxRatio;
    protected int maxWidth;
    protected int maxHeight;

    @Override

    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        int width = img.getWidth();
        int height = img.getHeight();
        double ratio = width / height;
        int proportions;
        int newWidth;
        int newHeight;
        if (maxRatio != 0) {
            if (ratio > maxRatio) {
                throw new BadImageSizeException(ratio, maxRatio);
            } else if (1 / ratio > maxRatio) {
                throw new BadImageSizeException(1 / ratio, maxRatio);
            }
        }
        if (maxHeight != 0 && maxWidth != 0) {
            if (maxWidth < width || maxHeight < height) {
                if (width / maxWidth >= height / maxWidth) {
                    proportions = width / maxWidth;
                } else {
                    proportions = height / maxHeight;
                }
                newHeight = height / proportions;
                newWidth = width / proportions;
            }else {
                newWidth = width;
                newHeight = height;
            }
        } else {
            newWidth = width;
            newHeight = height;
        }
        char[][] array = new char[newHeight][newWidth];
        TextColorSchema schema = new TextColor();
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();
        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                array[h][w] = c;
            }
        }
        String text ="";
        StringBuilder stringBuilder=new StringBuilder();
        for (int h = 0; h <newHeight; h++) {
            for (int w =0; w < newWidth; w++) {
                stringBuilder.append(array[h][w]);
                stringBuilder.append(array[h][w]);
            }
            stringBuilder.append('\n');
        }
        text=stringBuilder.toString();
        return text;
    }


    @Override
    public void setMaxWidth(int width) {
        this.maxWidth=width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight=height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio=maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {

    }
}
