package ru.digitalsoft.document.dao.entity.abilities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/*@Entity
@Table(name = "permission_to_role", schema = "abilities", catalog = "kaf_doc")*/
public class PermissionToRoleEntity implements Serializable {
    private long permissionId;
    private long roleId;

    /*@Id
    @Column(name = "permission_id", nullable = false)
    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    @Id
    @Column(name = "role_id", nullable = false)
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionToRoleEntity that = (PermissionToRoleEntity) o;
        return permissionId == that.permissionId &&
                roleId == that.roleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionId, roleId);
    }*/
}
