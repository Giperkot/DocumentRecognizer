package ru.digitalsoft.document.service;

import net.sourceforge.tess4j.TesseractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.digitalsoft.document.dto.document.CommonDocDto;
import ru.digitalsoft.document.handler.HandlerTextCategory;
import ru.digitalsoft.document.service.connect.HacatonConnectorApacheService;
import ru.digitalsoft.document.service.pdf.PdfService;
import ru.digitalsoft.document.utils.ExcelToText;
import ru.digitalsoft.document.utils.FileFinder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class DocumentRecognizerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentRecognizerService.class);

    private PdfService pdfService;

    private HacatonConnectorApacheService hacatonConnectorApacheService;

    @Autowired
    public DocumentRecognizerService(PdfService pdfService, HacatonConnectorApacheService hacatonConnectorApacheService) {
        this.pdfService = pdfService;
        this.hacatonConnectorApacheService = hacatonConnectorApacheService;
    }

    public Map<String, String> searchAndDefinitionCategoryOfText(String basePath) throws IOException, TesseractException {
        Map<String, String> pathWithGuid = new HashMap<>();
        Map<String, String> allPathToFiles = FileFinder.parserDirForGetAllPathToFiles(basePath);

        HandlerTextCategory handlerTextCategory = new HandlerTextCategory();
        for (Map.Entry<String, String> entry : allPathToFiles.entrySet()) {
            String guid;
            LOGGER.info(entry.getKey());
            if (entry.getValue().equals(FileFinder.XLS)){
                try {
                    guid = handlerTextCategory.definitionOfCategory(
                            ExcelToText.readFirstSheetFromXLS(entry.getKey()));
                    pathWithGuid.put(entry.getKey(),
                            guid
                    );
                    LOGGER.info("Файл по адресу" + entry.getKey() +
                            " имеет guid " + guid);
                } catch (Exception e) {
                    LOGGER.error("Не удалось прочитать файл " + entry.getValue());
                }
            } else if (entry.getValue().equals(FileFinder.XLSX)) {
                try {
                    guid =  handlerTextCategory.definitionOfCategory(
                            ExcelToText.readFirstSheetFromXLSX(entry.getKey()));
                    pathWithGuid.put(entry.getKey(),
                            guid
                    );
                    LOGGER.info("Файл по адресу " + entry.getKey() +
                            " имеет guid " + guid);
                } catch (Exception e) {
                    LOGGER.error("Не удалось прочитать файл " + entry.getValue());
                }
            } else if (entry.getValue().equals(FileFinder.PDF)) {
                File pdfFile = new File (entry.getKey());
                CommonDocDto docDto = pdfService.getTextFromPdf(pdfFile);

                if (docDto.getOkud() != null && docDto.getInn() != null && docDto.getDocType() != null) {
                    hacatonConnectorApacheService.sendInfoToHacatonServer(docDto, pdfFile.getAbsolutePath());

                    pathWithGuid.put(entry.getKey(), docDto.getDocType().getType());
                }

            }
        }
        return pathWithGuid;
    }
}
