package ru.digitalsoft.document.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.digitalsoft.document.enums.textcategory.BuhgalterOtchetnostForm1Promezh;
import ru.digitalsoft.document.enums.textcategory.BuhgalterOtchetnostForm2Promezh;
import ru.digitalsoft.document.utils.ExcelToText;

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



    public static void main(String[] args) throws Exception {
        LOGGER.info("---Start test form 1---");
        HandlerTextCategory handlerTextCategory1 = new HandlerTextCategory();
        String forTest1 = ExcelToText.readFirstSheetFromXLS("D:\\resources\\Баланс_КАМАЗ_9 мес.2020.xls");
        LOGGER.info(String.valueOf(handlerTextCategory1.checkIfStringBelongToBuhgalterOtchetnostForm1Promezh(forTest1)));
        LOGGER.info(String.valueOf(handlerTextCategory1.checkIfStringBelongToBuhgalterOtchetnostForm2Promezh(forTest1)));
        LOGGER.info("---End test form 1---");

        LOGGER.info("---Start test form 2---");
        HandlerTextCategory handlerTextCategory2 = new HandlerTextCategory();
        String forTest2 = ExcelToText.readFirstSheetFromXLS("D:\\resources\\F2_КАМАЗ_09.2020.xls");
        LOGGER.info(String.valueOf(handlerTextCategory2.checkIfStringBelongToBuhgalterOtchetnostForm1Promezh(forTest2)));
        LOGGER.info(String.valueOf(handlerTextCategory2.checkIfStringBelongToBuhgalterOtchetnostForm2Promezh(forTest2)));
        LOGGER.info("---End test form 2---");
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


        //TODO здесь должна быть проверка на дату

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

        //TODO здесь должна быть проверка на дату

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
