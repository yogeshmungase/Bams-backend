package com.management.studentattendancesystem.base.imgenhancer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GaborFilter {

    private static final Logger logger = LoggerFactory.getLogger(GaborFilter.class);

    public static byte[] enhanceImage(byte[] byteCode) {
        BufferedImage img = null;

        try {

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteCode);
            img = ImageIO.read(byteArrayInputStream);
            int width = img.getWidth(null);
            int height = img.getHeight(null);
            sharpen_image sharpenImage = new sharpen_image();

            int[][] array = null;
            int[][] filter_arr = null;
            int[][] clear_arr = null;

            estimate gf = new estimate();
            array = sharpenImage.img_arr(img);

            long process = System.currentTimeMillis();

            logger.info("height : {} ,width : {}", height, width);

            clear_arr = sharpenImage.Clear(array, 17, width, height);

            filter_arr = gf.orientation(clear_arr, width, height, 17, 6);
            long endtime = System.currentTimeMillis();
            logger.info("Time taken to enhance image : {}", (endtime - process));
            BufferedImage enhancedImage = sharpenImage.arr_img(filter_arr, width, height);
            BufferedImage binarizing = sharpenImage.binarizing(enhancedImage, filter_arr);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(binarizing, "bmp", baos);
            return baos.toByteArray();


        } catch (IOException e) {
            return null;
        }
    }


}
