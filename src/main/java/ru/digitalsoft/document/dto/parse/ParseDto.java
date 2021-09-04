package ru.digitalsoft.document.dto.parse;

public class ParseDto {

    private String path;

    private String uuid;

    private String categoryName;

    public ParseDto(String path, String uuid, String categoryName) {
        this.path = path;
        this.uuid = uuid;
        this.categoryName = categoryName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
