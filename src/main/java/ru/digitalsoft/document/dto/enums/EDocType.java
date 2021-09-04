package ru.digitalsoft.document.dto.enums;

public enum EDocType {

    ARTICLES_ASSOCIATION(1, "33a37ce4-c6a9-4dad-8424-707abd47c125", "Устав действующий"),
    POSITION_SD(2, "555ced1c-c169-4d61-9a82-348801494581", "Положение о СД"),
    ACCOUNTING_STATEMENTS_FORM1(3, "4f501f4a-c665-4cc8-9715-6ed26e7819f2", "Бухгалтерская отчетность форма 1"),
    ACCOUNTING_STATEMENTS_FORM2(4, "cabd193c-f9a9-4a9c-a4ae-80f0347adf40", "Бухгалтерская отчетность форма 2 промежуточная"),
    ACCOUNTING_STATEMENTS_SUB_FORM1(5, "2e321818-4571-43ae-9e08-2ade54b83e14", "Бухгалтерская отчетность форма 1 промежуточная"),
    ACCOUNTING_STATEMENTS_SUB_FORM2(6, "3b4f4647-f755-4100-bd63-059627107919", "Аудиторское заключение"),
    AUDIT_REPORT(7, "16f35ccc-b90f-4731-8178-11f3e0e3ca20", "Аудиторское заключение"),
    DESCRIPTION_OF_GC_ACTIVITIES(8, "a397c2cf-c5ad-4560-bc65-db4f79840f82", "Описание деятельности ГК"),
    DECISION_APPOINTMENT(9, "3af37c7f-d8b1-46de-98cc-683b0ffb3513", "Решение_назначение ЕИО"),
    UNRECOGNIZED(10, "none", "Неопознано");

    private final int id;

    private final String type;

    private final String name;

    EDocType(int id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
