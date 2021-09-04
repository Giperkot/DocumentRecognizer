package ru.digitalsoft.document.dao.repositories.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.digitalsoft.document.dao.entity.auth.SessionEntity;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

}
