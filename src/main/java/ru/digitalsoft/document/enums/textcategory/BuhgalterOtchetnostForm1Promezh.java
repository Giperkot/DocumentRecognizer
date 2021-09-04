package ru.digitalsoft.document.enums.textcategory;

public enum  BuhgalterOtchetnostForm1Promezh {
    BUHGALTERSKIY_BALANS("Бухгалтерский баланс"),

    FORMA_PO_OKUD("Форма по ОКУД"),
    FORMA_PO_OKUD_NUMBER("0710001"),

    //TODO косяк с датой
    DATA("Дата (число,месяц,год) если месяц = 3  размещение папка = год ==>1кв и тп)"),
    ACTIV("Актив"),
    PASSIVE("Пассив");

    private String property;

    BuhgalterOtchetnostForm1Promezh(String property) {
        this.property = property;
    }

    public String getProperty(){
        return property;
    }
}
