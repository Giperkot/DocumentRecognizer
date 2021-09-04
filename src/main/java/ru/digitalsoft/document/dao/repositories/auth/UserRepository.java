package ru.digitalsoft.document.dao.repositories.auth;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.digitalsoft.document.dao.entity.auth.UserEntity;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    List<UserEntity> findAll(Pageable page);

    UserEntity getByEmail(String email);

    @Query(value = "select u.* from auth.user u " +
            "   join auth.session s on s.user_id = u.id " +
            "   where s.session_key = ? ", nativeQuery = true)
    UserEntity getBySessionKey(String sessionKey);

    UserEntity getById(long id);
}
