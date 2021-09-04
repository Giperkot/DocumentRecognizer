package ru.digitalsoft.document.service.pdf;

import liquibase.util.StringUtils;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.digitalsoft.document.dto.document.CommonDocDto;
import ru.digitalsoft.document.dto.enums.EDocType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

@Service
public class PdfService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PdfService.class);

    private void cut (BufferedImage img, String fileName, int x, int y) throws IOException {

        /*BufferedImage newImage = new BufferedImage(
                img.getWidth(), img.getHeight(), img.getType());*/
        BufferedImage dest = img.getSubimage(x, y, img.getWidth() - x, img.getHeight() - y);

        File outputfile = new File(fileName);
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

                    if (isSingleBlack(color)) {
                        // считаем за чёрный
                        summInLine++;
                    }

                    /*int red =   (color & 0x00ff0000) >> 16;
                    int green = (color & 0x0000ff00) >> 8;
                    int blue =   color & 0x000000ff;

                    if (red + green + blue < 60) {
                        // считаем за чёрный

                        summInLine++;
                    }*/
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
        return color != -1;

        /*int red =   (color & 0x00ff0000) >> 16;
        int green = (color & 0x0000ff00) >> 8;
        int blue =   color & 0x000000ff;

        if (red + green + blue < 60) {
            return true;
        }

        return false;*/
    }

    private boolean isBlack (BufferedImage image, int x, int y, int treeshold) {
//        int color = image.getRGB(x, y);
        int meas = 2;
        int del = (meas + 1) * (meas + 1);

        int blackCount = 0;
        for (int i = -meas; i <= meas; i++) {
            for (int j = -meas; j <= meas; j++) {
//                colSumm = colSumm(image.getRGB(x - j, y - i));
                if (isSingleBlack(image.getRGB(x - j, y - i))) {
                    blackCount++;
                }
            }
        }

        if (treeshold < 0) {
            treeshold = 4;
        }

        if (blackCount > treeshold) {
            return true;
        }

        /*if (colSumm / (del) < 80) {
            return true;
        }*/

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
                    if (!isBlack(image, x + i, y, -1) || !isBlack(image, x, y + i, -1)) {
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
                LOGGER.info("angle: " + (angle * 180 / Math.PI));
                rotated = rotate(bufferedImage, -angle * 180 / Math.PI);

                cornerRect = getRectangleLeftCorner(rotated);

                cut(rotated, "cuted.jpg", cornerRect.x, cornerRect.y);

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
            parseAccountBalance(tesseract, rotated, cornerRect, text);
        }

        if (text.contains("Отчет о финансовых результатах")) {
            parseFinResults(tesseract, rotated, cornerRect, text);
        }




//        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    private boolean parseFinResults (Tesseract tesseract, BufferedImage rotated, Point cornerRect, String text) throws TesseractException, IOException {
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

        if (commonDocDto.getDate() != null && commonDocDto.getInn() != null
                && commonDocDto.getOkud() != null && "0710002".equals(commonDocDto.getOkud())) {
            // Можно определить тип
            if (commonDocDto.getDate().getMonth() == Month.DECEMBER) {
                // Окончательный отчёт.
                commonDocDto.setDocType(EDocType.ACCOUNTING_STATEMENTS_FORM1);
            } else {
                commonDocDto.setDocType(EDocType.ACCOUNTING_STATEMENTS_SUB_FORM1);
            }

            return true;
        }

        return false;
    }

    private boolean parseAccountBalance (Tesseract tesseract, BufferedImage rotated, Point cornerRect, String text) throws TesseractException, IOException {
        text = text.toLowerCase();

        if (!(text.contains("актив") && text.contains("пассив") && text.contains("форма по окуд"))) {
            return false;
        }

        CommonDocDto commonDocDto = parseOkudAndInn(tesseract, rotated, cornerRect);

        if (commonDocDto.getDate() != null && commonDocDto.getInn() != null
                && commonDocDto.getOkud() != null && "0710001".equals(commonDocDto.getOkud())) {
            // Можно определить тип
            if (commonDocDto.getDate().getMonth() == Month.DECEMBER) {
                // Окончательный отчёт.
                commonDocDto.setDocType(EDocType.ACCOUNTING_STATEMENTS_FORM1);
            } else {
                commonDocDto.setDocType(EDocType.ACCOUNTING_STATEMENTS_SUB_FORM1);
            }

            return true;
        }

        return false;
    }

    private LocalDate parseDate (Tesseract tesseract, BufferedImage rotated, Point cornerRect, int offset) throws TesseractException {
        String day = tesseract.doOCR(rotated, new Rectangle(cornerRect.x + 28, offset + 50 + 7, 72, 42)).trim();
        String month = tesseract.doOCR(rotated, new Rectangle(cornerRect.x + 145, offset + 50 + 7, 60, 42)).trim();
        String year = tesseract.doOCR(rotated, new Rectangle(cornerRect.x + 252, offset + 50 + 7, 92, 42)).trim();
        LocalDate localDate = null;

        if (StringUtils.isNotEmpty(day) && StringUtils.isNotEmpty(month) && StringUtils.isNotEmpty(year)) {


            int dayInt = Integer.parseInt(day.trim());
            dayInt = Math.min(dayInt, 31);
            localDate = LocalDate.of(Integer.parseInt(year.trim()), Integer.parseInt(month.trim()), dayInt);
        } else {
            String dateStr = tesseract.doOCR(rotated, new Rectangle(cornerRect.x, offset + 48, 700, 50)).trim();

            String[] dateParts = dateStr.split("\\|");

            if (dateParts.length >=3) {
                int[] dateArr = new int[3];
                int k = 0;

                for (int i = 0; i < dateParts.length; i++) {
                    if (dateParts[i].isEmpty()) {
                        continue;
                    }

                    dateArr[k] = Integer.parseInt(dateParts[i].trim());
                    k++;
                }

                localDate = LocalDate.of(dateArr[2], dateArr[1], dateArr[0]);
            }
        }

        if (localDate == null) {
            throw new RuntimeException();
        }

        return localDate;
    }

    private java.util.List<Integer> getLinesFromCorner (BufferedImage rotated, Point cornerRect) {

        java.util.List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 400; i++) {
            boolean isLine = true;

            for (int j = 0; j < 100; j++) {
                if (!isBlack(rotated, cornerRect.x + j, cornerRect.y + i, 1)) {
                    isLine = false;
                    break;
                }
            }

            if (isLine) {
                list.add(cornerRect.y + i);
                i += 5;
            }
        }

        return list;
    }

    private CommonDocDto parseOkudAndInn (Tesseract tesseract, BufferedImage rotated, Point cornerRect) throws TesseractException, IOException {

        java.util.List<Integer> lineCoords = getLinesFromCorner(rotated, cornerRect);

        int top = cornerRect.y;
        String okud = tesseract.doOCR(rotated, new Rectangle(cornerRect.x, top, 700, 50));
        String numOkud = okud.trim().replaceAll("[^0-9]", "");

        if (numOkud.isEmpty()) {
            top += 50;
            okud = tesseract.doOCR(rotated, new Rectangle(cornerRect.x, top, 700, 50));
            numOkud = okud.replaceAll("[^0-9]", "");
        }

        LocalDate localDate = null;
        int offset = top - 10;
        while (localDate == null && offset < top + 11) {
            try {
                localDate = parseDate(tesseract, rotated, cornerRect, offset);
            } catch (Exception ex) {
                offset += 10;
            }
        }

        cut(rotated, "date.jpg", cornerRect.x, top + 55);
        cut(rotated, "inn.jpg", cornerRect.x, top + 195);
        //top + 195
        String inn = tesseract.doOCR(rotated, new Rectangle(cornerRect.x, lineCoords.get(8), 700, 50)).trim();
        inn = inn.replaceAll("[^0-9]", "");

//        String[] dateParts = dateStr.split("\\|");



//        System.out.println("numOkud: " + okud + " inn: " + inn + " date: " + dateStr);

        return new CommonDocDto(numOkud, localDate, inn);
    }

}
