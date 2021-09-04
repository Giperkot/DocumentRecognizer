package ru.digitalsoft.document.dao.repositories.abilities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.digitalsoft.document.dao.entity.abilities.RoleEntity;

import java.util.List;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    @Query(value = "select r.* from abilities.user_to_role utr" +
            "   join abilities.role r on r.id = utr.role_id" +
            "   where user_id = ? ", nativeQuery= true)
    List<RoleEntity> getAllByUserId(long userId);


    List<RoleEntity> findAll(Pageable pageable);
}
