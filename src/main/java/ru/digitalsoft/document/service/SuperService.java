package ru.digitalsoft.document.service;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.digitalsoft.document.dto.abilities.PermissionDto;

import java.util.List;

public class SuperService {

    protected boolean hasPermission (String permissionName) {

        List<PermissionDto> permissionEntityList = (List<PermissionDto>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        for (int i = 0; i < permissionEntityList.size(); i++) {
            if (permissionName.equals(permissionEntityList.get(i).getName())) {
                return true;
            }
        }

        return false;
    }

}
