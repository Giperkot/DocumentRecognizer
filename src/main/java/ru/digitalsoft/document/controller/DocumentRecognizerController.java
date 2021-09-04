package ru.digitalsoft.document.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalsoft.document.service.DocumentRecognizerService;

import java.util.Map;

@RestController
@RequestMapping()
public class DocumentRecognizerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentRecognizerController.class);

    @Autowired
    private DocumentRecognizerService documentRecognizerService;


    @GetMapping("/scan/")
    public Map<String, String> redirectWithUsingRedirectView() {
        String mockPath = "C:\\Users\\koldy\\Downloads\\Промсвязьбанк Датасет\\Тестовый dataset\\ПАО НКХП 2315014748";
        return documentRecognizerService.searchAndDefinitionCategoryOfText(mockPath);
    }
}
