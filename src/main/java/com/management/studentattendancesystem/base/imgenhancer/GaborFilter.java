package com.management.studentattendancesystem.base.imgenhancer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class GaborFilter {

    private static final Logger logger = LoggerFactory.getLogger(GaborFilter.class);

    public static byte[] enhanceImage(byte[] byteCode) {
        BufferedImage img = null;

        try {

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteCode);
            img = ImageIO.read(byteArrayInputStream);
            int width = img.getWidth(null);
            int height = img.getHeight(null);
            Graphics2D g2d = img.createGraphics();
            g2d.drawImage(img, 0 + width, 0, -width, height, null);
            sharpen_image sharpenImage = new sharpen_image();

            int[][] array = null;
            int[][] filter_arr = null;
            int[][] clear_arr = null;

            estimate gf = new estimate();
            array = sharpenImage.img_arr(img);

            long process = System.currentTimeMillis();

            clear_arr = sharpenImage.Clear(array, 17, width, height);

            filter_arr = gf.orientation(clear_arr, width, height, 17, 7);
            BufferedImage enhancedImage = sharpenImage.arr_img(filter_arr, width, height);
            int[] cropWidth = sharpenImage.getCropWidth(enhancedImage, filter_arr);

            BufferedImage binarizing = sharpenImage.binarizingWithWidth(enhancedImage, filter_arr, cropWidth);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(binarizing, "bmp", baos);
            long endtime = System.currentTimeMillis();
            logger.info("Time taken to enhance image : {}", (endtime - process));
            return baos.toByteArray();


        } catch (Exception e) {
            logger.error("Error occured while enhancing image with probable cause : {}", e.getMessage());
            return null;
        }
    }

}
