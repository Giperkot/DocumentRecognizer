package ru.digitalsoft.document.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.digitalsoft.document.dao.entity.RequiredDocumentsEntity;

public interface RequiredDocumentsEntityRepository extends JpaRepository<RequiredDocumentsEntity, Long>, JpaSpecificationExecutor<RequiredDocumentsEntity> {

}