package ru.digitalsoft.document.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

public class FileFinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileFinder.class);

    public final static String XLS = "xls";
    public final static String XLSX = "xlsx";
    public final static String PDF = "pdf";

    private static Map<String, String> allFilesPathWitExtension = new HashMap<>();

    public static  Map<String, String> parserDirForGetAllPathToFiles(String path){
        File folder = new File(path);
        if (folder.isDirectory()) {
            for(File item : Objects.requireNonNull(folder.listFiles())){
                if(item.isDirectory()){
                    parserDirForGetAllPathToFiles(String.valueOf((item)));
                } else {
                    if (getFileExtension(item).equals(PDF)) {
                        LOGGER.info("PDF" + " " + item.getAbsolutePath());
                        allFilesPathWitExtension.put(item.getAbsolutePath(), PDF);
                    } else if (getFileExtension(item).equals(XLS)) {
                        LOGGER.info("XLS" + " " + item.getAbsolutePath());
                        allFilesPathWitExtension.put(item.getAbsolutePath(), XLS);
                    } else if (getFileExtension(item).equals(XLSX)) {
                        LOGGER.info("XLSX" + " " + item.getAbsolutePath());
                        allFilesPathWitExtension.put(item.getAbsolutePath(), XLSX);
                    }
                }
            }
        }
        return allFilesPathWitExtension;
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

}
