package com.management.studentattendancesystem.base.imgenhancer;

import com.management.studentattendancesystem.base.rest.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GaborFilter {

    private static Logger logger = LoggerFactory.getLogger(GaborFilter.class);

    public static byte[] enhanceImage(byte[] byteCode) {

        BufferedImage img = null;

        try {

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteCode);
            img = ImageIO.read(byteArrayInputStream);
            int width = img.getWidth(null);
            int height = img.getHeight(null);
            sharpen_image shar = new sharpen_image();

            int[][] array = new int[height + 100][width + 100];
            int[][] filter_arr = new int[height + 100][width + 100];
            int[][] norm_arr = new int[height + 100][width + 100];
            int[][] sharp_arr = new int[height + 100][width + 100];
            int[][] clear_arr = new int[height + 100][width + 100];
            int[][] foreground_arr = new int[height + 100][width + 100];


            estimate gf = new estimate();
            array = shar.img_arr(img);
            long process = System.currentTimeMillis();

            logger.info("height : {} ,width : {}",height,width);

            clear_arr = shar.Clear(array, 17, width, height);

            filter_arr = gf.orientation(clear_arr, width, height, 17, 7);
            long endtime = System.currentTimeMillis();
            logger.info("Time taken to enhance image : {}",(endtime - process));
            BufferedImage enhancedImage = shar.arr_img(filter_arr, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(enhancedImage, "bmp", baos);
            return baos.toByteArray();


        } catch (IOException e) {
            return null;
        }
    }
}
