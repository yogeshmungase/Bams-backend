package com.management.studentattendancesystem.base.factory;
import org.apache.commons.io.FileUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yogeshb
 */
@Service
public class HtmlToPdfConverter {

    private static final Logger logger = LoggerFactory.getLogger(HtmlToPdfConverter.class);

    protected static Integer topValue = 10;
    protected static Integer leftValue = 20;
    protected static Integer rightValue = 20;
    protected static Integer bottomValue = 10;
    protected static Integer userSpaceWidth =920;


    public static byte[] generatePdfByteArray(String inputHtml, String templateName) {

        if (StringUtils.isBlank(inputHtml)) {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            // starting stopwatch here
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            StringReader htmlReport = new StringReader(inputHtml);
            if (htmlReport == null || baos == null) {
            }

            PD4ML pd4ml = new PD4ML();
            pd4ml.interpolateImages(true);
            pd4ml.setPageInsets(new Insets(topValue, leftValue, bottomValue,
                    rightValue));
            pd4ml.setHtmlWidth(userSpaceWidth);
            pd4ml.setPageSize(PD4ML.A4);

            pd4ml.render(htmlReport, baos);

            stopWatch.stop();
            logger.warn("Time taken to convert html to pdf by pd4ml library for template {} is :{}", templateName, stopWatch.getLastTaskTimeMillis());
            return baos.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidParameterException e) {

            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {

            e.printStackTrace();
            return null;
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (Throwable e) {

            e.printStackTrace();
            return null;
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static FileInputStream generatePdfByteArrayOutputStream(
            String inputHtml) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PD4ML pd4ml = new PD4ML();
            pd4ml.interpolateImages(true);
            String fileName = new SimpleDateFormat("ddMMyyyyhhmmss")
                    .format(new Date()) + ".pdf";
            pd4ml.setPageInsets(new Insets(topValue, leftValue, bottomValue,
                    rightValue));
            pd4ml.setHtmlWidth(userSpaceWidth);
            pd4ml.setPageSize(PD4ML.A4);

            int footerStart = StringUtils.indexOf(inputHtml, "<tfoot>");
            int footerEnd = StringUtils.indexOf(inputHtml, "</tfoot>") + 8;

            String footerText = StringUtils.substring(inputHtml, footerStart,
                    footerEnd);
            if (StringUtils.isNotBlank(footerText)) {

                inputHtml = StringUtils.remove(inputHtml, footerText);
                int start = StringUtils.indexOf(footerText, "<table");
                int end = StringUtils.indexOf(footerText, "</table>") + 8;
                footerText = StringUtils.substring(footerText, start, end);
                PD4PageMark footer = new PD4PageMark();
                footer.setHtmlTemplate(footerText);
                footer.setAreaHeight(-1);
                pd4ml.setPageFooter(footer);
            }

            pd4ml.render(new StringReader(inputHtml), baos);

            FileOutputStream fos2 = new FileOutputStream(new File(fileName));
            fos2.write(baos.toByteArray());
            fos2.flush();
            fos2.close();
            return FileUtils.openInputStream(new File(fileName));

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return null;
        } catch (InvalidParameterException e) {

            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {

            e.printStackTrace();
            return null;
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } catch (Throwable e) {

            e.printStackTrace();
            return null;
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static FileInputStream generateMergePdfByteArrayOutputStream(
            List<String> inputHtmlReports) {

        List<String> pdfFiles = new ArrayList<String>();
        String fileName = new SimpleDateFormat("ddMMyyyyhhmmss")
                .format(new Date()) + "_Merge.pdf";

        try {

            for (int i = 0; i < inputHtmlReports.size(); i++) {
                ByteArrayOutputStream baosInner = new ByteArrayOutputStream();
                PD4ML pd4ml = new PD4ML();
                pd4ml.interpolateImages(true);
                pd4ml.setPageInsets(new Insets(topValue, leftValue,
                        bottomValue, rightValue));
                pd4ml.setHtmlWidth(userSpaceWidth);
                pd4ml.setPageSize(PD4ML.A4);

                if (inputHtmlReports.size() - 1 == i) {
                    if (inputHtmlReports.size() != 1) {
                        FileInputStream openInputStream = FileUtils
                                .openInputStream(new File(pdfFiles.get(i - 1)));
                        pd4ml.merge(openInputStream, 1, -1, true);
                    }

                    String inputHtml = inputHtmlReports.get(i);
                    int footerStart = StringUtils.indexOf(inputHtml, "<tfoot>");
                    int footerEnd = StringUtils.indexOf(inputHtml, "</tfoot>") + 8;

                    String footerText = StringUtils.substring(inputHtml,
                            footerStart, footerEnd);
                    if (StringUtils.isNotBlank(footerText)) {

                        inputHtml = StringUtils.remove(inputHtml, footerText);
                        int start = StringUtils.indexOf(footerText, "<table");
                        int end = StringUtils.indexOf(footerText, "</table>") + 8;
                        footerText = StringUtils.substring(footerText, start,
                                end);
                        PD4PageMark footer = new PD4PageMark();
                        footer.setHtmlTemplate(footerText);
                        footer.setAreaHeight(-1);
                        pd4ml.setPageFooter(footer);
                    }

                    pd4ml.render(inputHtml, baosInner);
                    FileOutputStream fos2 = new FileOutputStream(new File(
                            fileName));
                    fos2.write(baosInner.toByteArray());
                    fos2.flush();
                    fos2.close();

                } else {
                    String fileNameLocal = new SimpleDateFormat(
                            "ddMMyyyyhhmmss").format(new Date())
                            + "_"
                            + i
                            + "_.pdf";

                    if (pdfFiles.size() > 0) {
                        FileInputStream openInputStream = FileUtils
                                .openInputStream(new File(pdfFiles.get(i - 1)));
                        pd4ml.merge(openInputStream, 1, -1, true);
                    }

                    String inputHtml = inputHtmlReports.get(i);
                    int footerStart = StringUtils.indexOf(inputHtml, "<tfoot>");
                    int footerEnd = StringUtils.indexOf(inputHtml, "</tfoot>") + 8;

                    String footerText = StringUtils.substring(inputHtml,
                            footerStart, footerEnd);
                    if (StringUtils.isNotBlank(footerText)) {

                        inputHtml = StringUtils.remove(inputHtml, footerText);
                        int start = StringUtils.indexOf(footerText, "<table");
                        int end = StringUtils.indexOf(footerText, "</table>") + 8;
                        footerText = StringUtils.substring(footerText, start,
                                end);
                        PD4PageMark footer = new PD4PageMark();
                        footer.setHtmlTemplate(footerText);
                        footer.setAreaHeight(-1);
                        pd4ml.setPageFooter(footer);
                    }

                    pd4ml.render(inputHtml, baosInner);
                    FileOutputStream fos2 = new FileOutputStream(new File(
                            fileNameLocal));
                    fos2.write(baosInner.toByteArray());
                    fos2.flush();
                    fos2.close();
                    pdfFiles.add(fileNameLocal);
                }

                if (baosInner != null) {
                    try {
                        baosInner.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            FileInputStream openInputStream = FileUtils
                    .openInputStream(new File(fileName));
            return openInputStream;

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return null;
        } catch (InvalidParameterException e) {

            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {

            e.printStackTrace();
            return null;
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } catch (Throwable e) {

            e.printStackTrace();
            return null;
        } finally {
            for (String fileNameLocal : pdfFiles) {
                FileUtils.deleteQuietly(new File(fileNameLocal));
            }
        }
    }

    public static byte[] generateBase64PdfByteArray(String inputHtml) {

        if (StringUtils.isBlank(inputHtml)) {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            StringReader htmlReport = new StringReader(inputHtml);
            if (htmlReport == null || baos == null) {
                logger.error("Input HTML is null/blank.");
            }

            PD4ML pd4ml = new PD4ML();
            pd4ml.interpolateImages(true);
            pd4ml.setPageInsets(new Insets(topValue, leftValue, bottomValue,
                    rightValue));
            pd4ml.setHtmlWidth(userSpaceWidth);
            pd4ml.setPageSize(PD4ML.A4);

            int footerStart = StringUtils.indexOf(inputHtml, "<tfoot>");
            int footerEnd = StringUtils.indexOf(inputHtml, "</tfoot>") + 8;

            String footerText = StringUtils.substring(inputHtml, footerStart,
                    footerEnd);
            if (StringUtils.isNotBlank(footerText)) {

                inputHtml = StringUtils.remove(inputHtml, footerText);
                htmlReport = new StringReader(inputHtml);
                int start = StringUtils.indexOf(footerText, "<table");
                int end = StringUtils.indexOf(footerText, "</table>") + 8;
                footerText = StringUtils.substring(footerText, start, end);
                PD4PageMark footer = new PD4PageMark();
                footer.setHtmlTemplate(footerText);
                footer.setAreaHeight(-1);
                pd4ml.setPageFooter(footer);
            }

            pd4ml.render(htmlReport, baos);

            byte[] byteArray = baos.toByteArray();
            return byteArray;
        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return null;
        } catch (InvalidParameterException e) {

            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {

            e.printStackTrace();
            return null;
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        } catch (Throwable e) {

            e.printStackTrace();
            return null;
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
