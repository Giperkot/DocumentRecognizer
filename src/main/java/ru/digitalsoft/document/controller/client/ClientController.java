package ru.digitalsoft.document.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalsoft.document.dto.document.ClientDto;
import ru.digitalsoft.document.dto.document.DocumentTypeDto;
import ru.digitalsoft.document.service.document.DocumentService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private DocumentService documentService;

    @Autowired
    public ClientController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @RequestMapping("/clientList")
    public List<ClientDto> getClientList () {
        return documentService.getClientList();
    }

    @RequestMapping(value = "documentTypeList", method = RequestMethod.POST)
    public List<DocumentTypeDto> documentTypeList () {
        return documentService.documentTypeList();
    }

}
