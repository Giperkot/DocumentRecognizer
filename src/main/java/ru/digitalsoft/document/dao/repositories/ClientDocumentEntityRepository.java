package ru.digitalsoft.document.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.digitalsoft.document.dao.entity.ClientDocumentEntity;

public interface ClientDocumentEntityRepository extends JpaRepository<ClientDocumentEntity, Long>, JpaSpecificationExecutor<ClientDocumentEntity> {

}