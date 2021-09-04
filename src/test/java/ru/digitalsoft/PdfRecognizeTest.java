package ru.digitalsoft;

import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.digitalsoft.document.service.pdf.PdfService;

import java.io.File;
import java.io.IOException;

//@SpringBootTest
public class PdfRecognizeTest {

//    @Autowired
    private static PdfService pdfService = new PdfService();

//    @Test
    public static void main (String... args) throws IOException, TesseractException {
        File pdfFile = new File ("C:\\Users\\istvolov\\Documents\\leaderSoft 2021\\Тестовый dataset\\Тестовый dataset\\ПАО НКХП 2315014748\\Финансовое досье\\2020\\4 квартал\\Бухгалтерская отчетность\\Форма 1.pdf");
        String str = pdfService.getTextFromPdf(pdfFile);

        System.out.println(str);
    }

}
