package ru.digitalsoft.document.enums.textcategory;

public enum PolozhenieCD {
    POLOZHENIE_SOVET_DIRECTOROV("Положение о совете директоров"),
    PREDSEDATEL_SOVETA_DIRECTOROV("Председатель совета директоров"),
    PISMEN_MNENIE("Письменное мнение"),
    OPROSNIY_LIST("Опросный лист"),
    UVEDOMLENIE_O_PROVEDENII_SOVETA_DIRECTOROV("Уведомление о проведении Совета директоров");

    private String property;

    PolozhenieCD(String property) {
        this.property = property;
    }

    public String getProperty(){
        return property;
    }
}
