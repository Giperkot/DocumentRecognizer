package ru.digitalsoft.document.dto.document;

import ru.digitalsoft.document.dto.enums.EDocType;

import java.time.LocalDate;

public class CommonDocDto {

    private String okud;

    private LocalDate date;

    private String inn;

    private EDocType docType;

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

    public EDocType getDocType() {
        return docType;
    }

    public void setDocType(EDocType docType) {
        this.docType = docType;
    }
}
