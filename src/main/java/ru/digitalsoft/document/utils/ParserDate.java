package ru.digitalsoft.document.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserDate {

    public static Date parseDateByRegexpForExcel(String text) throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
        String lines[] = text.split("\\n");

        for(String line: lines) {
            Pattern pattern = Pattern.compile("'(0?[1-9]|[12][0-9]|3[01]).*(0?[1-9]|1[012]).*(\\d{4})'");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String resultDate = matcher.group(1) + "." + matcher.group(2) + "." + matcher.group(3);
                return ft.parse(resultDate);
            }
        }
        return null;
    }
}
