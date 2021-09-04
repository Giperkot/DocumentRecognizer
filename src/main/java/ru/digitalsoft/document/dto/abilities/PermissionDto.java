package ru.digitalsoft.document.dto.abilities;

import org.springframework.security.core.GrantedAuthority;
import ru.digitalsoft.document.dao.entity.abilities.PermissionEntity;

public class PermissionDto implements GrantedAuthority {
    private long id;
    private String name;
    private String title;
    private String type;
    private String description;

    public PermissionDto(PermissionEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.title = entity.getTitle();
        this.type = entity.getType();
        this.description = entity.getDescription();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
