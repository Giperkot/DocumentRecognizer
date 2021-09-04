package ru.digitalsoft.document.enums.textcategory;

public enum  BuhgalterOtchetnostForm1Promezh {
    GUID("2e321818-4571-43ae-9e08-2ade54b83e14"),

    BUHGALTERSKIY_BALANS("Бухгалтерский баланс"),

    FORMA_PO_OKUD("Форма по ОКУД"),
    FORMA_PO_OKUD_NUMBER("0710001"),

    //TODO косяк с датой
    DATA_PART_1("Дата"),
    DATA_PART_2("число"),
    DATA_PART_3("месяц"),
    DATA_PART_4("год"),

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
