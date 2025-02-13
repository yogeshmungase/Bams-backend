package com.management.studentattendancesystem.base.imgenhancer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;


public class sharpen_image {
    double[][] gauss_lp = new double[][]{{1, 4, 7, 4, 1},
            {4, 16, 26, 16, 4},
            {7, 26, 41, 26, 7},
            {4, 16, 26, 16, 4},
            {1, 4, 7, 4, 1}

    };
    double[][] gauss_lp2 = new double[][]{{1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1}

    };
    double[][] gauss = new double[][]{{14, 19, 23, 24, 23, 19, 14},
            {19, 23, 30, 32, 30, 23, 19},
            {23, 30, 36, 38, 36, 30, 23},
            {24, 32, 38, 40, 38, 32, 24},
            {23, 30, 36, 38, 36, 30, 23},
            {19, 23, 30, 32, 30, 23, 19},
            {14, 19, 23, 24, 23, 19, 14}
    };

    public int[][] binar(BufferedImage img) {

        int width = img.getWidth(null);
        int height = img.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
        int[] black = new int[]{0, 0, 0};
        int[] white = new int[]{255, 255, 255};
        int i, j;
        int[][] bin_arr = new int[height][width];
        //float mean = mean(img);
        //img_arr = new int[width][height];
        for (i = 0; i < width; i++) {
            for (j = 0; j < height; j++) {
                int clr = img.getRGB(i, j);
                int red = (clr & 0x00ff0000) >> 16;
                if (red > 160) bin_arr[j][i] = 0;
                else bin_arr[j][i] = 1;
            }
        }

        return bin_arr;
    }

    public BufferedImage binarizing(BufferedImage img, int[][] array) {

        int width = img.getWidth(null);
        int height = img.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
        int[] black = new int[]{0, 0, 0};
        int[] white = new int[]{255, 255, 255};
        int i, j;
        int rightSpace = width - 25;
        int lefetSpace = 25;
        float mean = mean(array, width, height);
        for (i = 0; i < width; i++) {

            for (j = 0; j < height; j++) {

                int clr = img.getRGB(i, j);
                int red = (clr & 0x00ff0000) >> 16;
                if (red > mean) {
                    raster.setPixel(i, j, black);

                } else {
                    raster.setPixel(i, j, white);
                }
            }
        }

        return image;
    }

    public int[] getCropWidth(BufferedImage img, int[][] array) {

        int width = img.getWidth(null);
        int height = img.getHeight(null);
        int[] widthArray = new int[4];
        int i, j;
        float mean = mean(array, width, height);
        int blackPixelCount = 0;
        int whitePixelCount = 0;
        int cropWidthLeft = 0;
        int cropWidthRight = 0;
        int cropWidthBottom = 0;
        int cropWidthTop = 0;
        for (i = 0; i < width; i++) {
            for (j = 0; j < height; j++) {
                int clr = img.getRGB(i, j);
                int red = (clr & 0x00ff0000) >> 16;
                if (red > mean) {
                    blackPixelCount++;
                } else {
                    whitePixelCount++;
                }

            }


            if (i <= 75) {
                if (whitePixelCount > blackPixelCount && (whitePixelCount - blackPixelCount) >= 170) {
                    cropWidthLeft = i;
                }
            }

            if (i >= (width - 75)) {
                if (whitePixelCount > blackPixelCount && (whitePixelCount - blackPixelCount) >= 170) {
                    // take fist one
                    if (cropWidthRight == 0) {
                        cropWidthRight = i;
                    }
                }
            }

            whitePixelCount = 0;
            blackPixelCount = 0;

        }

        for (j = 0; j < height; j++) {
            for (i = 0; i < width; i++) {
                int clr = img.getRGB(i, j);
                int red = (clr & 0x00ff0000) >> 16;
                if (red > mean) {
                    blackPixelCount++;
                } else {
                    whitePixelCount++;
                }

            }


            if (j <= 60) {
                if (whitePixelCount > blackPixelCount && (whitePixelCount - blackPixelCount) >= 270) {
                    cropWidthBottom = j;
                }
            }

            if (j >= (width - 60)) {
                if (whitePixelCount > blackPixelCount && (whitePixelCount - blackPixelCount) >= 270) {
                    // take fist one
                    if (cropWidthTop == 0) {
                        cropWidthTop = j;
                    }
                }
            }

            whitePixelCount = 0;
            blackPixelCount = 0;

        }
        widthArray[0] = cropWidthLeft;
        widthArray[1] = cropWidthRight;
        widthArray[2] = cropWidthBottom;
        widthArray[3] = cropWidthTop;

      /*  System.out.println("height : "+height+"width : "+width);
        System.out.println("cropWidthLeft : "+cropWidthLeft+ " cropWidthRight : "+cropWidthRight+" cropWidthBottom:"+cropWidthBottom+" cropWidthTop:"+cropWidthTop);
*/
        return widthArray;
    }

    public BufferedImage binarizingWithWidth(BufferedImage img, int[][] array, int[] cropWidth) {
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        int leftCropWidth = cropWidth[0];
        int rightCropWidth = cropWidth[1];
        int bottomCropWidth = cropWidth[2];
        int topCropWidth = cropWidth[3];
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
        int[] black = new int[]{0, 0, 0};
        int[] white = new int[]{255, 255, 255};
        int i, j;
        float mean = mean(array, width, height);
        for (i = 0; i < width; i++) {

            for (j = 0; j < height; j++) {

                // white pixel draw for left and right side
                if (i <= leftCropWidth || i >= rightCropWidth ) {
                    raster.setPixel(i, j, white);
                    continue;
                }

                // whit pixel darw for bottom and top
                if (j <= bottomCropWidth || j >= topCropWidth ) {
                    raster.setPixel(i, j, white);
                    continue;
                }

                // normal image draw
                int clr = img.getRGB(i, j);
                int red = (clr & 0x00ff0000) >> 16;
                if (red > mean) {
                    raster.setPixel(i, j, black);

                } else {
                    raster.setPixel(i, j, white);
                }
            }
        }

        return image;
    }

    public int[][] processImage(int[][] array, int width, int height) {
        float mean = mean(array, width, height);
        float var = variance(array, mean, width, height);
        int[][] arr = new int[height + 100][width + 100];
        if (var < 500) {
            for (int i = 50; i < height + 50; i++) {
                for (int j = 50; j < width + 50; j++) {
                    arr[i][j] = clamp(9 * array[i][j] - (array[i - 1][j - 1] + array[i - 1][j] + array[i - 1][j + 1] + array[i][j - 1] + array[i][j + 1] + array[i + 1][j - 1] + array[i + 1][j] + array[i + 1][j + 1]));

                }
            }
        } else {
            for (int i = 50; i < height + 50; i++) {
                for (int j = 50; j < width + 50; j++) {
                    arr[i][j] = clamp(5 * array[i][j] - (array[i][j - 1] + array[i][j + 1]));

                }
            }
        }
        return arr;

    }

    public BufferedImage Gauss_lp(BufferedImage img, int[][] vecx) {
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
        double[][] im_ar = new double[height + 100][width + 100];
        for (int r2 = 50; r2 < height + 50; r2++) {
            for (int c2 = 50; c2 < width + 50; c2++) {
                double sum_fx = 0, sum_fy = 0;
                for (int u2 = -2; u2 <= 2; u2++) {
                    for (int v2 = -2; v2 <= 2; v2++) {
                        sum_fx = sum_fx + (0.003663) * gauss_lp[2 + u2][2 + v2] * vecx[r2 - 16 * u2][c2 - 16 * v2];

                    }
                }

                im_ar[r2][c2] = sum_fx;
                int set_color = (int) (im_ar[r2][c2]);
                int[] color = new int[]{set_color, set_color, set_color};
                raster.setPixel(c2 - 50, r2 - 50, color);
            }
        }
        return image;
    }


    private final int clamp(int c) {
        return (c > 255 ? 255 : (c < 0 ? 0 : c));
    }

    public int[][] Normalize(int[][] array, int width, int height) {
        int[][] arr = new int[height + 100][width + 100];

        float mean1 = mean(array, width, height);

        float variance1 = variance(array, mean1, width, height);

        for (int i = 50; i < width + 50; i++) {
            for (int j = 50; j < height + 50; j++) {
                float var0;
                if (variance1 < 1000) var0 = 1000;
                else var0 = variance1;
                double d = Math.sqrt((1000 * (array[j][i] - mean1) * (array[j][i] - mean1)) / variance1);
                int money = (int) d;

                if (array[j][i] > mean1) array[j][i] = clamp(60 + money);
                else array[j][i] = clamp(60 - money);

                arr[j][i] = array[j][i];

            }
        }

        //System.out.println("mean:      "+mean1);
        //System.out.println("variance:  "+variance1);

        return arr;

    }

    public float mean(int[][] array, int width, int height) {

        float sum = 0;
        for (int i = 50; i < width + 50; i++) {
            for (int j = 50; j < height + 50; j++) {
                sum = sum + array[j][i];
            }
        }

        sum = sum / (height * width);

        return sum;
    }


    public float variance(int[][] array, float mean, int width, int height) {
        float sum = 0, var = 0;
        for (int i = 50; i < width + 50; i++) {
            for (int j = 50; j < height + 50; j++) {
                sum = sum + ((array[j][i] - mean) * (array[j][i] - mean));
            }
        }
        var = sum / (width * height);
        return var;
    }

    public int[][] img_arr(BufferedImage img) {
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        int[][] array = new int[height + 100][width + 100];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int clr = img.getRGB(i, j);
                int red = (clr & 0x00ff0000) >> 16;
                array[j + 50][i + 50] = red;
            }
        }
        return array;
    }


    public BufferedImage arr_img(int[][] array, int width, int height) {

        BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = bImage.getRaster();
        for (int i = 50; i < width + 50; i++) {
            for (int j = 50; j < height + 50; j++) {
                int[] color = new int[]{array[j][i], array[j][i], array[j][i]};
                raster.setPixel(i - 50, j - 50, color);
            }
        }
        return bImage;
    }


    public BufferedImage inverseimage(BufferedImage imgin) {

        int width = imgin.getWidth(null);
        int height = imgin.getHeight(null);
        BufferedImage imagein = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = imagein.getRaster();
        int[] black = new int[]{0, 0, 0};
        int[] white = new int[]{255, 255, 255};
        int i, j;


        for (i = 0; i < width; i++) {
            for (j = 0; j < height; j++) {

                int clr = imgin.getRGB(i, j);
                int red = (clr & 0x00ff0000) >> 16;
                if (red > 0) raster.setPixel(i, j, black);
                else raster.setPixel(i, j, white);
            }
        }

        return imagein;
    }

    public int[][] Clear(int[][] array, int Block_size, int width, int height) {
        int[][] mean_block = new int[(height / Block_size) + 1][(width / Block_size) + 1];

        mean_blocks_int(array, mean_block, 17, width, height);

        for (int i = 50; i < width + 50; i++) {
            for (int j = 50; j < height + 50; j++) {

                if ((mean_block[(j - 50) / Block_size][(i - 50) / Block_size] - array[j][i]) > 0)
                    array[j][i] = 255;
                else array[j][i] = 0;

            }
        }

        return array;
    }

    public void mean_blocks(double[][] array, double[][] mean_block, int block_size, int width, int height) {

        for (int i = 50 + (block_size / 2); i < width + 50 - (block_size / 2); i = i + block_size) {
            for (int j = 50 + (block_size / 2); j < height + 50 - (block_size / 2); j = j + block_size) {
                double mean1 = 0;
                for (int u = -(block_size / 2); u <= (block_size / 2); u++) {
                    for (int v = -(block_size / 2); v <= (block_size / 2); v++) {
                        mean1 = mean1 + array[j - u][i - v];
                    }
                }
                mean_block[(j - 50) / block_size][(i - 50) / block_size] = (int) (mean1 / (block_size * block_size));
                //System.out.println(array[(int)(j/17)][(int)(i/17)]);
            }
        }
        //System.out.println("outside mean_blocks "+ (int)(j/17)+" "+(int)(i/17));

    }

    public void mean_blocks_int(int[][] array, int[][] mean_block, int block_size, int width, int height) {

        for (int i = 50 + (block_size / 2); i < width + 50 - (block_size / 2); i = i + block_size) {
            for (int j = 50 + (block_size / 2); j < height + 50 - (block_size / 2); j = j + block_size) {
                int mean1 = 0;
                for (int u = -(block_size / 2); u <= (block_size / 2); u++) {
                    for (int v = -(block_size / 2); v <= (block_size / 2); v++) {
                        mean1 = mean1 + array[j - u][i - v];
                    }
                }
                mean_block[(j - 50) / block_size][(i - 50) / block_size] = mean1 / (block_size * block_size);
                //System.out.println(array[(int)(j/17)][(int)(i/17)]);
            }
        }
        //System.out.println("outside mean_blocks "+ (int)(j/17)+" "+(int)(i/17));

    }

    public int[][] check_foreground(int[][] array, int block_size, int width, int height) {

        double[][] gx = new double[height + 100][width + 100];
        double[][] gy = new double[height + 100][width + 100];
        double[][] Mx = new double[(height / block_size) + 1][(width / block_size) + 1];
        double[][] My = new double[(height / block_size) + 1][(width / block_size) + 1];
        double[][] stdx = new double[(height / block_size) + 1][(width / block_size) + 1];
        double[][] stdy = new double[(height / block_size) + 1][(width / block_size) + 1];
        double[][] grddev = new double[(height / block_size) + 1][(width / block_size) + 1];

        for (int r = 50; r < height + 50; r++) {
            for (int c = 50; c < width + 50; c++) {

                gx[r][c] = (array[r - 1][c - 1] - array[r - 1][c + 1]) + 2 * (array[r][c - 1] - array[r][c + 1]) + (array[r + 1][c - 1] - array[r + 1][c + 1]);
                gy[r][c] = (array[r - 1][c - 1] - array[r + 1][c - 1]) + 2 * (array[r - 1][c] - array[r + 1][c]) + (array[r - 1][c + 1] - array[r + 1][c + 1]);
            }

        }
        mean_blocks(gx, Mx, block_size, width, height);
        mean_blocks(gy, My, block_size, width, height);

        for (int i = 50 + (block_size / 2); i < width + 50 - (block_size / 2); i = i + block_size) {
            for (int j = 50 + (block_size / 2); j < height + 50 - (block_size / 2); j = j + block_size) {
                double mean1 = 0, mean2 = 0;
                for (int u = -(block_size / 2); u <= (block_size / 2); u++) {
                    for (int v = -(block_size / 2); v <= (block_size / 2); v++) {
                        mean1 = mean1 + (gx[j + u][i + v] - Mx[(j - 50) / block_size][(i - 50) / block_size]) * (gx[j + u][i + v] - Mx[(j - 50) / block_size][(i - 50) / block_size]);
                        mean2 = mean2 + (gy[j + u][i + v] - My[(j - 50) / block_size][(i - 50) / block_size]) * (gy[j + u][i + v] - My[(j - 50) / block_size][(i - 50) / block_size]);

                    }
                }
                stdx[(j - 50) / block_size][(i - 50) / block_size] = (Math.sqrt(mean1) / block_size);
                stdy[(j - 50) / block_size][(i - 50) / block_size] = (Math.sqrt(mean2) / block_size);
                grddev[(j - 50) / block_size][(i - 50) / block_size] = (stdx[(j - 50) / block_size][(i - 50) / block_size] + stdy[(j - 50) / block_size][(i - 50) / block_size]);

                //	System.out.println(grddev[(int)((j-50)/block_size)][(int)((i-50)/block_size)]);
                if (grddev[(j - 50) / block_size][(i - 50) / block_size] < 120) {

                    for (int u = -(block_size / 2); u <= (block_size / 2); u++) {
                        for (int v = -(block_size / 2); v <= (block_size / 2); v++) {
                            array[j + u][i + v] = 80;

                        }
                    }


                }
            }
        }

        return array;

    }

    public BufferedImage sharpen(BufferedImage img, int max) {
        int r0 = 0;
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
        int i = 0, j = 0;
        for (int run = 1; run <= max; run++) {
            for (i = 1; i < width - 1; i++) {
                for (j = 1; j < height - 1; j++) {
                    int rs = 0;
                    for (int k = -1; k <= 1; k++) {
                        for (int p = -1; p <= 1; p++) {

                            int clr = img.getRGB(i + k, j + p);
                            int r = (clr & 0x00ff0000) >> 16;
                            if (p == 0 && k == 0) r0 = r;
                            else rs = rs + r;

                        }
                    }

                    rs = rs / 8;
                    int red = clamp(r0 + (r0 - rs));
                    int[] colour = new int[]{red, red, red};
                    raster.setPixel(i, j, colour);

                }
            }
        }
        return image;
    }

}
