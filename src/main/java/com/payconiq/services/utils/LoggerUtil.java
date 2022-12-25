package com.payconiq.services.utils;

import org.apache.log4j.FileAppender;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoggerUtil extends FileAppender {

    public LoggerUtil() {
    }

    @Override
    public void setFile(String file) {
        super.setFile(appendDate(file));
    }

    private static String appendDate(String filename) {
        SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy'T'HH_mm_ss");
        Calendar Caldate = Calendar.getInstance();
        String dateFormateString = formater.format(Caldate.getTime());
        return "logs/" + filename + dateFormateString + ".log";
    }
}
