package ru.digitalsoft.document.mapper;

import org.mapstruct.Mapper;
import ru.digitalsoft.document.dao.entity.ClientEntity;
import ru.digitalsoft.document.dao.entity.DocumentTypeEntity;
import ru.digitalsoft.document.dto.document.ClientDto;
import ru.digitalsoft.document.dto.document.DocumentTypeDto;

@Mapper
public interface SimpleMapper {

    ClientDto toClientDto(ClientEntity entity);

    ClientEntity toClientEntity(ClientDto dto);

    DocumentTypeDto toDocumentTypeDto(DocumentTypeEntity entity);

}
