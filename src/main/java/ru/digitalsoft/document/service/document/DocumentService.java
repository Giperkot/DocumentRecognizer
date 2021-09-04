package ru.digitalsoft.document.service.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.digitalsoft.document.dao.entity.ClientEntity;
import ru.digitalsoft.document.dao.entity.DocumentTypeEntity;
import ru.digitalsoft.document.dao.repositories.ClientDocumentEntityRepository;
import ru.digitalsoft.document.dao.repositories.ClientEntityRepository;
import ru.digitalsoft.document.dao.repositories.DocumentTypeEntityRepository;
import ru.digitalsoft.document.dto.document.ClientDto;
import ru.digitalsoft.document.dto.document.DocumentTypeDto;
import ru.digitalsoft.document.mapper.SimpleMapper;
import ru.digitalsoft.document.mapper.SimpleMapperImpl;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private SimpleMapper mapper;

    private ClientEntityRepository clientEntityRepository;

    private ClientDocumentEntityRepository clientDocumentRepository;

    private DocumentTypeEntityRepository documentTypeEntityRepository;

    @Autowired
    public DocumentService(ClientEntityRepository clientEntityRepository,
                           ClientDocumentEntityRepository clientDocumentRepository, DocumentTypeEntityRepository documentTypeEntityRepository) {
        this.mapper = new SimpleMapperImpl();
        this.clientEntityRepository = clientEntityRepository;
        this.clientDocumentRepository = clientDocumentRepository;
        this.documentTypeEntityRepository = documentTypeEntityRepository;
    }

    public List<ClientDto> getClientList () {
        List<ClientEntity> clientEntityList = clientEntityRepository.findAll();

        return clientEntityList.stream().map((entity) -> {
            return mapper.toClientDto(entity);
        }).collect(Collectors.toList());
    }

    public List<DocumentTypeDto> documentTypeList () {
        List<DocumentTypeEntity> documentTypeEntityList = documentTypeEntityRepository.findAll();

        return documentTypeEntityList.stream().map((entity) -> {
            return mapper.toDocumentTypeDto(entity);
        }).collect(Collectors.toList());

    }
}
