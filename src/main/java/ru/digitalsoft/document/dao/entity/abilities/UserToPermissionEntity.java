package ru.digitalsoft.document.dao.entity.abilities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/*@Entity
@Table(name = "user_to_permission", schema = "abilities", catalog = "kaf_doc")*/
public class UserToPermissionEntity implements Serializable {
    private long userId;
    private long permissionId;

    /*@Id
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "permission_id", nullable = false)
    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserToPermissionEntity that = (UserToPermissionEntity) o;
        return userId == that.userId &&
                permissionId == that.permissionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, permissionId);
    }*/
}
