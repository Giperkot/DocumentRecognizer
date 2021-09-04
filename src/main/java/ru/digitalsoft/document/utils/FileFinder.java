package ru.digitalsoft.document.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileFinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileFinder.class);

    public static List<String> parserDirForGetAllPathToFiles(String path){
        List<String> allFilesPath = new ArrayList<>();
        File folder = new File(path);
        if (folder.isDirectory()) {
            for(File item : Objects.requireNonNull(folder.listFiles())){
                if(item.isDirectory()){
                    parserDirForGetAllPathToFiles(String.valueOf((item)));
                } else {
                    if (getFileExtension(item).equals("pdf")) {
                        LOGGER.info("PDF" + " " + item.getAbsolutePath());
                        allFilesPath.add(item.getAbsolutePath());
                    } else if (getFileExtension(item).equals("xls") |
                            getFileExtension(item).equals("xlsx")) {
                        LOGGER.info("XLS" + " " + item.getAbsolutePath());
                        allFilesPath.add(item.getAbsolutePath());
                    }
                }
            }
        }
        return allFilesPath;
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

}
