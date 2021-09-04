package ru.digitalsoft.document.service.pdf;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class PdfService {

    public static BufferedImage rotate(BufferedImage img, double angle)
    {

        // Getting Dimensions of image
        int width = img.getWidth();
        int height = img.getHeight();

        // Creating a new buffered image
        BufferedImage newImage = new BufferedImage(
                img.getWidth(), img.getHeight(), img.getType());

        // creating Graphics in buffered image
        Graphics2D g2 = newImage.createGraphics();

        // Rotating image by degrees using toradians()
        // method
        // and setting new dimension t it
        g2.rotate(Math.toRadians(angle), width / 2,
                height / 2);
        g2.drawImage(img, null, 0, 0);

        // Return rotated buffer image
        return newImage;
    }

    private double getPageAngle (BufferedImage bufferedImage) {

        int maxSummInLine = -1;
        double tangens = -5;

        for (double k = -0.103; k < 0.103; k += 0.01) {
            for (int b = 0; b < bufferedImage.getHeight(); b++) {
                int summInLine = 0;

                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    int y = (int)Math.round(k * x + b);

                    if (y < 0 || y >= bufferedImage.getHeight()) {
                        continue;
                    }

                    int color = bufferedImage.getRGB(x, y);

                    int red =   (color & 0x00ff0000) >> 16;
                    int green = (color & 0x0000ff00) >> 8;
                    int blue =   color & 0x000000ff;

                    if (red + green + blue < 60) {
                        // считаем за чёрный

                        summInLine++;
                    }
                }
                if (summInLine > maxSummInLine) {
                    tangens = k;
                    maxSummInLine = summInLine;
                }
            }


        }

        return Math.atan(tangens);
    }

    int colSumm (int color) {
        int red =   (color & 0x00ff0000) >> 16;
        int green = (color & 0x0000ff00) >> 8;
        int blue =   color & 0x000000ff;

        return red + green + blue;
    }

    private boolean isSingleBlack (int color) {
        int red =   (color & 0x00ff0000) >> 16;
        int green = (color & 0x0000ff00) >> 8;
        int blue =   color & 0x000000ff;

        if (red + green + blue < 60) {
            return true;
        }

        return false;
    }

    private boolean isBlack (BufferedImage image, int x, int y) {
//        int color = image.getRGB(x, y);

        int colSumm = 0;
        for (int i = -3; i < 3; i++) {
            for (int j = -3; j < 3; j++) {
                colSumm = colSumm(image.getRGB(x - j, y - i));
            }
        }

        if (colSumm / 36 < 60) {
            return true;
        }

        return false;
    }

    private Point getRectangleLeftCorner (BufferedImage image) {

//        List<Point> pointList = new ArrayList<>();
        
        for (int y = 100; y < image.getHeight() - 300; y++) {
            label:
            for (int x = 100; x < image.getWidth() - 300; x++) {
                int color = image.getRGB(x, y);

                if (!isSingleBlack(color)) {
                    continue;
                }

                boolean isRect = true;

                for (int i = 0; i < 200; i++) {
                    if (!isBlack(image, x + i, y) || !isBlack(image, x, y + 1)) {
                        break label;
                    }
                }

                return new Point(x, y);
            }
        }

        return null;
    }

    public String getTextFromPdf (File pdfFile) throws IOException, TesseractException {
        PDDocument document = PDDocument.load(pdfFile);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        int pagesCount = document.getNumberOfPages();

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Additions\\osr\\tesseract");
        tesseract.setLanguage("rus");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 1; i++) {
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(i, 300, ImageType.RGB);

            if (i == 0) {
                double angle = getPageAngle(bufferedImage);

                BufferedImage rotated = rotate(bufferedImage, -angle * 180 / Math.PI);

                Point cornerRect = getRectangleLeftCorner(rotated);

                File outputfile = new File(i + "image.jpg");
                ImageIO.write(rotated, "jpg", outputfile);

                // 1891, 595, 163, 39
                System.out.println(angle * 180 / Math.PI);
//                System.out.println(tesseract.doOCR(bufferedImage, new Rectangle(1796, 530, 341, 574)));
            }

//            stringBuilder.append(tesseract.doOCR(bufferedImage));


        }


//        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

}
