package ru.digitalsoft.document.dao.repositories.abilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.digitalsoft.document.dao.entity.auth.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Repository
public class AbilitiesRepository {

    private EntityManager entityManager;

    @Autowired
    public AbilitiesRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void clearRolesAndPermissionFromUser (UserEntity entity) {

        Query clearRolesQuery = entityManager.createNativeQuery("delete from abilities.user_to_role where user_id = :userId ");

        Query clearPermissionsQuery = entityManager.createNativeQuery("delete from abilities.user_to_permission where user_id = :userId ");

        clearRolesQuery.setParameter("userId", entity.getId());
        clearPermissionsQuery.setParameter("userId", entity.getId());

        clearRolesQuery.executeUpdate();
        clearPermissionsQuery.executeUpdate();
    }

    public void createRolesAndPermissionsToUser(UserEntity entity, List<Long> roleIdList) {

        if (roleIdList.size() < 1) {
            return;
        }

        Query insertRolesQuery = entityManager.createNativeQuery("insert into abilities.user_to_role (user_id, role_id) values (:userId, :roleId)");

        for (int i = 0; i < roleIdList.size(); i++) {
            insertRolesQuery.setParameter("userId", entity.getId());
            insertRolesQuery.setParameter("roleId", roleIdList.get(i));

            insertRolesQuery.executeUpdate();
        }

        entityManager.flush();

        Query selectPermissionQuery = entityManager.createNativeQuery("select distinct ptr.permission_id from abilities.permission_to_role ptr" +
                "   where role_id in :roleList ");

        selectPermissionQuery.setParameter("roleList", roleIdList);

        List<BigInteger> permissionIdList = (List<BigInteger>)selectPermissionQuery.getResultList();

        Query insertPermissionQuery = entityManager.createNativeQuery("insert into abilities.user_to_permission (user_id, permission_id) values (:userId, :permissionId)");

        for (int i = 0; i < permissionIdList.size(); i++) {
            insertPermissionQuery.setParameter("userId", entity.getId());
            insertPermissionQuery.setParameter("permissionId", permissionIdList.get(i).longValue());

            insertPermissionQuery.executeUpdate();
        }



    }

}
