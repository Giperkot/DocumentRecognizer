package ru.digitalsoft.document.dto.document;

import java.time.LocalDate;

public class CommonDocDto {

    private String okud;

    private LocalDate date;

    private String inn;

    public CommonDocDto(String okud, LocalDate date, String inn) {
        this.okud = okud;
        this.date = date;
        this.inn = inn;
    }

    public String getOkud() {
        return okud;
    }

    public void setOkud(String okud) {
        this.okud = okud;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }
}
