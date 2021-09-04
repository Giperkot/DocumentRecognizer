package ru.digitalsoft.document.dto.auth;

import ru.digitalsoft.document.dao.entity.auth.UserEntity;
import ru.digitalsoft.document.dto.abilities.PermissionDto;
import ru.digitalsoft.document.dto.abilities.RoleDto;

import java.util.ArrayList;
import java.util.List;

public class FullUserDto extends ru.digitalsoft.document.dto.auth.UserDto {

    private List<RoleDto> roleDtoList = new ArrayList<>();

    private List<Long> roleDtoIdList = new ArrayList<>();

    private List<PermissionDto> permissionDtoList = new ArrayList<>();

    private boolean changePassword;

    public FullUserDto() {
    }

    public FullUserDto(UserEntity entity) {
        super(entity);
    }

    public FullUserDto(UserEntity entity, List<PermissionDto> permissionDtoList) {
        super(entity);
        this.permissionDtoList = permissionDtoList;
    }

    public List<RoleDto> getRoleDtoList() {
        return roleDtoList;
    }

    public void setRoleDtoList(List<RoleDto> roleDtoList) {
        this.roleDtoList = roleDtoList;
    }

    public boolean isChangePassword() {
        return changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    public List<Long> getRoleDtoIdList() {
        return roleDtoIdList;
    }

    public void setRoleDtoIdList(List<Long> roleDtoIdList) {
        this.roleDtoIdList = roleDtoIdList;
    }

    public List<PermissionDto> getPermissionDtoList() {
        return permissionDtoList;
    }

    public void setPermissionDtoList(List<PermissionDto> permissionDtoList) {
        this.permissionDtoList = permissionDtoList;
    }
}
