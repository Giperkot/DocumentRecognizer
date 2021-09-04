package ru.digitalsoft.document.enums.textcategory;

public enum  CurrentUstav {
    USTAV("Устав"),
    //TODO возможно опечатка в ТЗ
    USTAV_CAPITAL("Уставный капитал"),
    ORGAN_UPRAV("Органы управления"),
    REZERV_FOND("Резервный фонд"),
    BULLETIN("Бюллетени");

    private String property;

    CurrentUstav(String property) {
        this.property = property;
    }

    public String getProperty(){
        return property;
    }
}
