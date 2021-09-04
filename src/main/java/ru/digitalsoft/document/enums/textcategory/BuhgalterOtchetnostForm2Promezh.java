package ru.digitalsoft.document.enums.textcategory;

public enum BuhgalterOtchetnostForm2Promezh {
    OTCHET_O_FIN_RESULT("Отчет о финансовых результатах"),

    FORMA_PO_OKUD("Форма по ОКУД"),
    FORMA_PO_OKUD_NUMBER("0710002"),

    //TODO косяк с датой
    DATA("Дата (число,месяц,год) если месяц = 3  размещение папка = год ==>1кв и тп)"),
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
