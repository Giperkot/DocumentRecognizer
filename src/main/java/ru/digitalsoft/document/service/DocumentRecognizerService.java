package ru.digitalsoft.document.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.digitalsoft.document.handler.HandlerTextCategory;
import ru.digitalsoft.document.utils.ExcelToText;
import ru.digitalsoft.document.utils.FileFinder;

import java.util.HashMap;
import java.util.Map;

@Service
public class DocumentRecognizerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentRecognizerService.class);

    public Map<String, String> searchAndDefinitionCategoryOfText(String basePath) {
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
                //TODO сделать файл и вызвать сервис PDF
            }
        }
        return pathWithGuid;
    }
}
