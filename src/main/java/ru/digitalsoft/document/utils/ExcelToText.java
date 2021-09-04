package ru.digitalsoft.document.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

public class ExcelToText {

    public static String readFirstSheetFromXLS(String excelFilePath) throws Exception {
        String result;

        try(FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = new HSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(0);

            result = parseTextFromSheet(firstSheet);
        }

        return result;
    }

    public static String readFirstSheetFromXLSX(String excelFilePath) throws Exception {
        String result;

        try(FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(0);

            result = parseTextFromSheet(firstSheet);
        }

        return result;
    }

    private static String parseTextFromSheet(Sheet sheet){
        StringBuilder sb = new StringBuilder();
        for (Row nextRow : sheet) {
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case STRING:
                        sb.append(cell.getStringCellValue()).append(", ");
                        break;
                    case BOOLEAN:
                        sb.append(cell.getBooleanCellValue()).append(", ");
                        break;
                    case NUMERIC:
                        sb.append(cell.getNumericCellValue()).append(", ");
                        break;
                    case FORMULA:
                        sb.append(cell.getCellFormula()).append(", ");
                        break;
                    default:
                        sb.append(cell.getStringCellValue()).append(", ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}