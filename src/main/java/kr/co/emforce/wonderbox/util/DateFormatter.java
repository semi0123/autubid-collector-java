package kr.co.emforce.wonderbox.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateFormatter {
  private static final String YYYY_MM_DD_HH_MM_SS_SSSS = "yyyyMMddHHmmssSSS";
  private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
  private static final String YYYY_MM_DD = "yyyy-MM-dd";
  
  public static String dateToString(Date date, String f) {
    SimpleDateFormat formatter = new SimpleDateFormat(f);
    String str = "";
    if (date != null) {
      str = formatter.format(date);
    }
    return str;
  }

  public static String dateToString(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
    String str = "";
    if (date != null) {
      str = formatter.format(date);
    }
    return str;
  }

  public static String dateToStringYYYYMMDD(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
    String str = "";
    if (date != null) {
      str = formatter.format(date);
    }
    return str;
  }

  public static Date stringToDate(String date) throws Exception {
    SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
    Date returnDate = null;

    try {
      if (date != null) {
        returnDate = formatter.parse(date);
      }
    } catch (ParseException e) {
      throw new Exception("Date format invalid.");
    }
    return returnDate;
  }
  
  public static Date stringToDate(String date, String format) throws Exception {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    Date returnDate = null;

    try {
      if (date != null) {
        returnDate = formatter.parse(date);
      }
    } catch (ParseException e) {
      throw new Exception("Date format invalid.");
    }
    return returnDate;
  }

  public static String currentDateToString() {
    Date currnetDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSSS);
    return formatter.format(currnetDate);
  }
  
  public static String currentDateToString(String format) {
    Date currnetDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    return formatter.format(currnetDate);
  }

  public static Date currentDateAddMinutes(int minute) {
    Date addDate = DateUtils.addMinutes(new Date(), minute);
    return addDate;
  }

  public static Date dateAddMinutes(Date date, int minute) {
    Date addDate = DateUtils.addMinutes(date, minute);
    return addDate;
  }

  public static Date currentDateAddHours(int hour) {
    Date addDate = DateUtils.addHours(new Date(), hour);
    return addDate;
  }

}
