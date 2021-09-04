package ru.digitalsoft.document.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.digitalsoft.document.enums.textcategory.BuhgalterOtchetnostForm1Promezh;
import ru.digitalsoft.document.enums.textcategory.BuhgalterOtchetnostForm2Promezh;
import ru.digitalsoft.document.utils.ParserDate;

import java.text.ParseException;

public class HandlerTextCategory {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerTextCategory.class);

//  CurrentUstav
//1. Устав
//2. Уставный капитал
//3. Органы управления
//4. Резервный фонд
//5. Бюллетени

//  PolozhenieCD
//1. Положение о совете директоров
//2. Председатель совета директоров
//3. Письменное мнение
//4. Опросный лист
//5. Уведомление о проведении Совета директоров

// _3
//1. Бухгалтерский баланс
//2. Форма по ОКУД 0710001
//3. Дата (число,месяц,год) (Условие если месяц =12 размещение папка = год ==>4кв)
//4. Актив
//5. Пассив

// _4
//1. Отчет о финансовых результатах
//2. Форма по ОКУД 0710002
//3. Дата (число,месяц,год) (Условие если месяц =12 размещение папка = год ==>4кв)
//4. Чистая прибыль
//5. Налог на прибыль

    public String definitionOfCategory(String text){
        if (checkIfStringBelongToBuhgalterOtchetnostForm1Promezh(text)){
            return BuhgalterOtchetnostForm1Promezh.GUID.getProperty();
        } else if (checkIfStringBelongToBuhgalterOtchetnostForm2Promezh(text)) {
            return BuhgalterOtchetnostForm2Promezh.GUID.getProperty();
        } else {
            return null;
        }
    }

// _5 BuhgalterOtchetnostForm1Promezh Бухгалтерская отчетность_форма 1 _промежуточная
//1. Бухгалтерский баланс
//2. Форма по ОКУД 0710001
//3. Дата (число,месяц,год) если месяц = 3  размещение папка = год ==>1кв и тп)
//4. Актив
//5. Пассив

    /**
     * Максимальное количество баллов равно количества свойств данного документа.
     * В данном методе по баллам определяется принадлежность текста к категории Бухгалтерская отчетность_форма 1 _промежуточная.
     * @param text
     * @return
     */
    private boolean checkIfStringBelongToBuhgalterOtchetnostForm1Promezh(String text){
        LOGGER.info("Начало проверки на тип  Бухгалтерская отчетность_форма 1 _промежуточная");
        int score = 0;
        if (text.contains(BuhgalterOtchetnostForm1Promezh.BUHGALTERSKIY_BALANS.getProperty())){
            LOGGER.info("Найден " + BuhgalterOtchetnostForm1Promezh.BUHGALTERSKIY_BALANS.getProperty());
            score++;
        } else {
            LOGGER.info("НЕ НАЙДЕН " + BuhgalterOtchetnostForm1Promezh.BUHGALTERSKIY_BALANS.getProperty());
        }


        if (text.contains(BuhgalterOtchetnostForm1Promezh.FORMA_PO_OKUD.getProperty())
                && text.contains(BuhgalterOtchetnostForm1Promezh.FORMA_PO_OKUD_NUMBER.getProperty())) {
            LOGGER.info("Найден " + BuhgalterOtchetnostForm1Promezh.FORMA_PO_OKUD_NUMBER.getProperty() +
                    BuhgalterOtchetnostForm1Promezh.FORMA_PO_OKUD_NUMBER.getProperty());
            score++;
        } else {
            LOGGER.info("НЕ НАЙДЕН " + BuhgalterOtchetnostForm1Promezh.FORMA_PO_OKUD_NUMBER.getProperty() +
                    BuhgalterOtchetnostForm1Promezh.FORMA_PO_OKUD_NUMBER.getProperty());
        }



        try {
            //TODO здесь бы желательно ещё учитывать параметры из Enum DATA_PART_...
            ParserDate.parseDateByRegexpForExcel(text);
            score++;
            LOGGER.info("Найден " + BuhgalterOtchetnostForm1Promezh.DATA_PART_1.getProperty());
        } catch (ParseException e) {
           LOGGER.error("НЕ НАЙДЕН " + BuhgalterOtchetnostForm1Promezh.DATA_PART_1.getProperty());
        }


        if (text.contains(BuhgalterOtchetnostForm1Promezh.ACTIV.getProperty()) ||
                text.contains(BuhgalterOtchetnostForm1Promezh.ACTIV.getProperty().toUpperCase()) ||
                        text.contains(BuhgalterOtchetnostForm1Promezh.ACTIV.getProperty().toLowerCase())) {
            LOGGER.info("Найден " + BuhgalterOtchetnostForm1Promezh.ACTIV.getProperty());
            score++;
        } else {
            LOGGER.info("НЕ НАЙДЕН " + BuhgalterOtchetnostForm1Promezh.ACTIV.getProperty());
        }

        if (text.contains(BuhgalterOtchetnostForm1Promezh.PASSIVE.getProperty())||
                text.contains(BuhgalterOtchetnostForm1Promezh.PASSIVE.getProperty().toUpperCase()) ||
                text.contains(BuhgalterOtchetnostForm1Promezh.PASSIVE.getProperty().toLowerCase())){
            LOGGER.info("Найден " + BuhgalterOtchetnostForm1Promezh.PASSIVE.getProperty());
            score++;
        } else {
            LOGGER.info("НЕ НАЙДЕН " + BuhgalterOtchetnostForm1Promezh.PASSIVE.getProperty());
        }

        LOGGER.info("Конец проверки на тип  Бухгалтерская отчетность_форма 1 _промежуточная");
        return score >= 4;
    }

// _6 BuhgalterOtchetnostForm2Promezh Бухгалтерская отчетность_форма 2 _промежуточная
//1. Отчет о финансовых результатах
//2. Форма по ОКУД 0710002
//3. Дата (число,месяц,год) если месяц = 3  размещение папка = год ==>1кв и тп)
//4. Чистая прибыль
//5. Налог на прибыль

    private boolean checkIfStringBelongToBuhgalterOtchetnostForm2Promezh(String text){
        LOGGER.info("Начало проверки на тип  Бухгалтерская отчетность_форма 2 _промежуточная");
        int score = 0;
        if (text.contains(BuhgalterOtchetnostForm2Promezh.OTCHET_O_FIN_RESULT.getProperty())) {
            LOGGER.info("Найден " + BuhgalterOtchetnostForm2Promezh.OTCHET_O_FIN_RESULT.getProperty());
            score++;
        } else {
            LOGGER.info("НЕ НАЙДЕН " + BuhgalterOtchetnostForm2Promezh.OTCHET_O_FIN_RESULT.getProperty());
        }

        if (text.contains(BuhgalterOtchetnostForm2Promezh.FORMA_PO_OKUD.getProperty())
                && text.contains(BuhgalterOtchetnostForm2Promezh.FORMA_PO_OKUD_NUMBER.getProperty()))  {
            LOGGER.info("Найден " + BuhgalterOtchetnostForm2Promezh.FORMA_PO_OKUD_NUMBER.getProperty() +
                    BuhgalterOtchetnostForm2Promezh.FORMA_PO_OKUD_NUMBER.getProperty());
            score++;
        } else {
            LOGGER.info("НЕ НАЙДЕН " + BuhgalterOtchetnostForm2Promezh.FORMA_PO_OKUD_NUMBER.getProperty() +
                    BuhgalterOtchetnostForm2Promezh.FORMA_PO_OKUD_NUMBER.getProperty());
        }

        try {
            //TODO здесь бы желательно ещё учитывать параметры из Enum DATA_PART_...
            ParserDate.parseDateByRegexpForExcel(text);
            score++;
            LOGGER.info("Найден " + BuhgalterOtchetnostForm2Promezh.DATA_PART_1.getProperty());
        } catch (ParseException e) {
            LOGGER.error("НЕ НАЙДЕН " + BuhgalterOtchetnostForm2Promezh.DATA_PART_1.getProperty());
        }

        if (text.contains(BuhgalterOtchetnostForm2Promezh.CHISTAY_PRIBIL.getProperty())) {
            LOGGER.info("Найден " + BuhgalterOtchetnostForm2Promezh.CHISTAY_PRIBIL.getProperty());
            score++;
        } else {
            LOGGER.info("НЕ НАЙДЕН " + BuhgalterOtchetnostForm2Promezh.CHISTAY_PRIBIL.getProperty());
        }

        if (text.contains(BuhgalterOtchetnostForm2Promezh.NALOG_NA_PRIBIL.getProperty())) {
            LOGGER.info("Найден " + BuhgalterOtchetnostForm2Promezh.NALOG_NA_PRIBIL.getProperty());
            score++;
        } else {
            LOGGER.info("НЕ НАЙДЕН " + BuhgalterOtchetnostForm2Promezh.NALOG_NA_PRIBIL.getProperty());
        }

        LOGGER.info("Конец проверки на тип  Бухгалтерская отчетность_форма 2 _промежуточная");
        return score >= 4;
    }

}
