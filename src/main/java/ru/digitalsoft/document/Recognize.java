package ru.digitalsoft.document;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.boot.SpringApplication;

import java.io.File;

public class Recognize {

    public static void main (String... args) throws TesseractException {

        File image = new File("C:\\Users\\istvolov\\Documents\\leaderSoft 2021\\new\\doc_example.png");
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Additions\\osr\\tesseract");
        tesseract.setLanguage("rus");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        String result = tesseract.doOCR(image);
        System.out.println(result);
    }

}
