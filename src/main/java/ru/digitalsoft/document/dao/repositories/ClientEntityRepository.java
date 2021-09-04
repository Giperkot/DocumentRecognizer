package ru.digitalsoft.document.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.digitalsoft.document.dao.entity.ClientEntity;

import java.util.List;

public interface ClientEntityRepository extends JpaRepository<ClientEntity, Long>, JpaSpecificationExecutor<ClientEntity> {

    List<ClientEntity> findAll();

}