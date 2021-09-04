package ru.digitalsoft.document.service.pdf;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import ru.digitalsoft.document.dto.document.CommonDocDto;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class PdfService {

    private void cut (BufferedImage img, int x, int y) throws IOException {

        /*BufferedImage newImage = new BufferedImage(
                img.getWidth(), img.getHeight(), img.getType());*/
        BufferedImage dest = img.getSubimage(x, y, img.getWidth() - x, img.getHeight() - y);

        File outputfile = new File("cuted.jpg");
        ImageIO.write(dest, "jpg", outputfile);


    }

    public static BufferedImage rotate(BufferedImage img, double angle) {

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
        for (int i = -1; i < 1; i++) {
            for (int j = -1; j < 1; j++) {
                colSumm = colSumm(image.getRGB(x - j, y - i));
            }
        }

        if (colSumm / 9 < 60) {
            return true;
        }

        return false;
    }

    private Point getRectangleLeftCorner (BufferedImage image) {

        java.util.List<Point> pointList = new ArrayList();

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

//                pointList.add(new Point(x, y));
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
        Point cornerRect = null;
        BufferedImage rotated = null;

        for (int i = 0; i < Math.min(3, pagesCount); i++) {
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(i, 300, ImageType.RGB);

            if (i == 0) {
                double angle = getPageAngle(bufferedImage);

                rotated = rotate(bufferedImage, -angle * 180 / Math.PI);

                cornerRect = getRectangleLeftCorner(rotated);

                cut(rotated, cornerRect.x, cornerRect.y);

                File outputfile = new File(i + "image.jpg");
                ImageIO.write(rotated, "jpg", outputfile);
//                System.out.println(tesseract.doOCR(bufferedImage, new Rectangle(1796, 530, 341, 574)));
            }

            stringBuilder.append(tesseract.doOCR(bufferedImage));
        }

        // Определить тип документа
        String text = stringBuilder.toString();

        System.out.println(text);
        boolean isValid = false;

        if (text.contains("Бухгалтерский баланс")) {
            parseBugBalance(tesseract, rotated, cornerRect, text);
        }

        if (text.contains("Отчет о финансовых результатах")) {
            parseFinResults(tesseract, rotated, cornerRect, text);
        }




//        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    private boolean parseFinResults (Tesseract tesseract, BufferedImage rotated, Point cornerRect, String text) throws TesseractException {
        /**
         * 1. Отчет о финансовых результатах
         * 2. Форма по ОКУД 0710002
         * 3. Дата (число,месяц,год) если месяц = 3  размещение папка = год ==>1кв и тп)
         * 4. Чистая прибыль
         * 5. Налог на прибыль
         */
        text = text.toLowerCase();

        if (!(text.contains("чистая прибыль") && text.contains("налог на прибыль") && text.contains("форма по окуд"))) {
            return false;
        }

        CommonDocDto commonDocDto = parseOkudAndInn(tesseract, rotated, cornerRect);

        return true;
    }

    private boolean parseBugBalance (Tesseract tesseract, BufferedImage rotated, Point cornerRect, String text) throws TesseractException {
        text = text.toLowerCase();

        if (!(text.contains("актив") && text.contains("пассив") && text.contains("форма по окуд"))) {
            return false;
        }

        CommonDocDto commonDocDto = parseOkudAndInn(tesseract, rotated, cornerRect);

        return true;
    }

    private CommonDocDto parseOkudAndInn (Tesseract tesseract, BufferedImage rotated, Point cornerRect) throws TesseractException {
        int top = cornerRect.y;
        String okud = tesseract.doOCR(rotated, new Rectangle(cornerRect.x, top, 700, 50));
        String numOkud = okud.trim().replaceAll("[^0-9]", "");

        if (numOkud.isEmpty()) {
            top += 50;
            okud = tesseract.doOCR(rotated, new Rectangle(cornerRect.x, top, 700, 50));
            numOkud = okud.replaceAll("[^0-9]]", "");
        }

        String dateStr = tesseract.doOCR(rotated, new Rectangle(cornerRect.x, top + 50, 700, 50)).trim();
        String inn = tesseract.doOCR(rotated, new Rectangle(cornerRect.x, top + 195, 700, 50)).trim();

        String[] dateParts = dateStr.split("|");
        LocalDate localDate = null;

        if (dateParts.length == 3) {
            localDate = LocalDate.of(Integer.parseInt(dateParts[2].trim()), Integer.parseInt(dateParts[1].trim()), Integer.parseInt(dateParts[0].trim()));
        }
        System.out.println("numOkud: " + okud + " inn: " + inn + " date: " + dateStr);

        return new CommonDocDto(numOkud, localDate, inn);
    }

}
