package ru.digitalsoft.document.dao.repositories.abilities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.digitalsoft.document.dao.entity.abilities.PermissionEntity;

import java.util.List;

public interface PermissionRepository extends CrudRepository<PermissionEntity, Long> {

    @Query(value = "select p.* from abilities.user_to_permission utp" +
            "   join abilities.permission p on p.id = utp.permission_id" +
            "   where utp.user_id = ? ", nativeQuery = true)
    List<PermissionEntity> getAllByUserId(long userId);

}
