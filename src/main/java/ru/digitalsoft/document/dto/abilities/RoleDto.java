package ru.digitalsoft.document.dto.abilities;

import ru.digitalsoft.document.dao.entity.abilities.RoleEntity;

public class RoleDto {
    private long id;
    private String name;
    private String title;
    private String description;

    public RoleDto() {
    }

    public RoleDto(RoleEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.title = entity.getTitle();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
