package ru.digitalsoft.document.enums;

import ru.digitalsoft.document.dto.exceptions.UserException;

public enum EEntityStatus {

    CREATED(1, "CREATED", "Создано"),
    UPLOADED(2, "UPLOADED", "Загружено"),
    ACCEPTED(3, "ACCEPTED", "Утверждено"),
    DELETED(4, "DELETED", "Удалено");

    private long id;

    private String name;

    private String title;

    private static final EEntityStatus[] values = values();

    EEntityStatus(long id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public static EEntityStatus getByName (String workTypeStr) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].getName().equals(workTypeStr)) {
                return values[i];
            }
        }

        throw new UserException("Данный статус не существует");
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

}
