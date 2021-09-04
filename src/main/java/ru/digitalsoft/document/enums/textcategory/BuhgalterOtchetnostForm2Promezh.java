package ru.digitalsoft.document.enums.textcategory;

public enum BuhgalterOtchetnostForm2Promezh {
    GUID("3b4f4647-f755-4100-bd63-059627107919"),

    OTCHET_O_FIN_RESULT("Отчет о финансовых результатах"),

    FORMA_PO_OKUD("Форма по ОКУД"),
    FORMA_PO_OKUD_NUMBER("0710002"),

    DATA_PART_1("Дата"),
    DATA_PART_2("число"),
    DATA_PART_3("месяц"),
    DATA_PART_4("год"),

    CHISTAY_PRIBIL("Чистая прибыль"),
    NALOG_NA_PRIBIL("Налог на прибыль");

    private String property;

    BuhgalterOtchetnostForm2Promezh(String property) {
        this.property = property;
    }

    public String getProperty(){
        return property;
    }

}
