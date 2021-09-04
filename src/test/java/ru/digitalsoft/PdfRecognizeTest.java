package ru.digitalsoft;

import net.sourceforge.tess4j.TesseractException;
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
//        File pdfFile = new File ("C:\\Users\\istvolov\\Documents\\leaderSoft 2021\\Тестовый dataset\\Тестовый dataset\\ПАО НКХП 2315014748\\Финансовое досье\\2020\\4 квартал\\Бухгалтерская отчетность\\Форма 2.pdf");
//        File pdfFile = new File ("C:\\Users\\istvolov\\Documents\\leaderSoft 2021\\Тестовый dataset\\Тестовый dataset\\ПАО НКХП 2315014748\\Финансовое досье\\2021\\1 квартал\\Бухгалтерская отчетность\\Форма 1_1кв21.pdf");
//        File pdfFile = new File ("C:\\Users\\istvolov\\Documents\\leaderSoft 2021\\Тестовый dataset\\Тестовый dataset\\ПАО НКХП 2315014748\\Финансовое досье\\2021\\1 квартал\\Бухгалтерская отчетность\\Форма 2_1кв21.pdf");
        String str = pdfService.getTextFromPdf(pdfFile);

        System.out.println(str);
    }

}
