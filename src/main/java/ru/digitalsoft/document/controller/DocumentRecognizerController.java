package ru.digitalsoft.document.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalsoft.document.dao.entity.DocumentTypeEntity;
import ru.digitalsoft.document.dao.repositories.DocumentTypeEntityRepository;
import ru.digitalsoft.document.dto.parse.ParseDto;
import ru.digitalsoft.document.service.DocumentRecognizerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recognition")
public class DocumentRecognizerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentRecognizerController.class);

    private DocumentRecognizerService documentRecognizerService;

    private DocumentTypeEntityRepository repository;

    @Autowired
    public DocumentRecognizerController(DocumentRecognizerService documentRecognizerService, DocumentTypeEntityRepository repository) {
        this.documentRecognizerService = documentRecognizerService;
        this.repository = repository;
    }

    @GetMapping("/scan")
    public List<ParseDto> redirectWithUsingRedirectView() {
        String mockPath = "C:\\Users\\istvolov\\Documents\\leaderSoft 2021\\Тестовый dataset\\Тестовый dataset";

        Map<String, String> resultMap = documentRecognizerService.searchAndDefinitionCategoryOfText(mockPath);

        List<ParseDto> parseDtoList = new ArrayList<>();
        List<DocumentTypeEntity> documentTypeEntityList = repository.findAll();
        Map<String, DocumentTypeEntity> documentTypeEntityMap = new HashMap<>();

        for (DocumentTypeEntity entity : documentTypeEntityList) {
            documentTypeEntityMap.put(entity.getUuid(), entity);
        }


        for (String key : resultMap.keySet()) {
            String uuid = resultMap.get(key);
            parseDtoList.add(new ParseDto(key, uuid, documentTypeEntityMap.get(uuid).getTitle()));
        }

        return parseDtoList;
    }
}
