package com.serio.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

public class DateUtils {
  public static final String PATTERN_ALL = "yyyy-MM-dd HH:mm:ss.S";
  public static final String PATTERN_YMDHMS = "yyyy-MM-dd HH:mm:ss";

  /**
   * 
   * @param formatTime  : 已经格式化的 时间格式 例如 "1:09:09","2015-6-8 1:9:9";
   * @param displayTime : 需要的时间格式    例如："时分秒"
   * @return 例如 formatTime="1:09:09" displayTime="时分秒" return 1时9分9秒;
   *         若 formatTime="0:00:09" 则 return 9秒
   */
  public static String convertFormat2Display(String formatTime, String displayTime) {
    StringBuilder timeStr = new StringBuilder();
    if (StringUtils.isNotBlank(formatTime) && StringUtils.isNotBlank(displayTime)) {
      String forTimeNew = formatTime.replaceAll("\\s+|\\-|\\:", ","); //2015,6,8,1,9,9
      String[] forTimeArr = forTimeNew.split(",");
      char[] disTimeArr = displayTime.toCharArray();
      
      for(int i=0;i<disTimeArr.length;i++){
        if(!forTimeArr[i].equals("0000") && !forTimeArr[i].equals("00") &&!forTimeArr[i].equals("0")){
          String forTimInd = forTimeArr[i];
          if(forTimeArr[i].startsWith("0")){
            forTimInd = forTimInd.replace("0", "");
          }
          timeStr.append(forTimInd+disTimeArr[i]);
        }
      }
    }
    return timeStr.toString();
  }
 
   
  /**
   * 将视频时长 毫秒数 转换成 时分秒 字符串
   * @param millisecond 毫秒数
   * @param pattern  'HH:mm:sss' 
   * @return  时分秒 字符串
   */
  public static String countMediaLength(String millisecond, String pattern) {
    String  mediaLength = null; 
    if (StringUtils.isNotBlank(millisecond)) {
      DateFormat df = new SimpleDateFormat(pattern);
      //获取时间差 timeLag
      long  timeLag = TimeZone.getDefault().getRawOffset();
      mediaLength = df.format(Long.valueOf(millisecond)- timeLag);
    }
    return mediaLength;
  }

  public static Date parseString2Date(String dateStr, String pattern) {
    Date date = null;
    if (StringUtils.isNotBlank(dateStr)) {
      DateFormat df = new SimpleDateFormat(pattern);
      try {
        date = df.parse(dateStr);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return date;
  }

  public static String parseDate2String(Date date, String pattern) {
    String dateStr = "";
    if (date != null) {
      DateFormat df = new SimpleDateFormat(pattern);
      dateStr = df.format(date);
    }
    return dateStr;
  }

  public static String appendTimeFrom(String dateStr) {
    String result = " 00:00:00.0";
    if (StringUtils.isNotBlank(dateStr)) {
      return dateStr + result;
    }
    return "";
  }

  public static String appendTimeTo(String dateStr) {
    String result = " 23:59:59.999";
    if (StringUtils.isNotBlank(dateStr)) {
      return dateStr + result;
    }
    return "";
  }

  /**
   * @param dateStr yyyy-MM-dd格式的日期
   * @return
   */
  public static Date parseString2DateFrom(String dateStr) {
    Date date = null;
    if (StringUtils.isNotBlank(dateStr)) {
      dateStr = appendTimeFrom(dateStr);
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
      try {
        date = df.parse(dateStr);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return date;
  }

  /**
   * @param dateStr yyyy-MM-dd格式的日期
   * @return
   */
  public static Date parseString2DateTo(String dateStr) {
    Date date = null;
    if (StringUtils.isNotBlank(dateStr)) {
      dateStr = appendTimeTo(dateStr);
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
      try {
        date = df.parse(dateStr);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return date;
  }

  /**
   * @param dateStr 指定年月日格式
   * @return
   */
  public static Date parseString2DateTo(String dateStr, String yyMMdd) {
    Date date = null;
    if (StringUtils.isNotBlank(dateStr)) {
      dateStr = appendTimeTo(dateStr);
      DateFormat df = new SimpleDateFormat(yyMMdd + " HH:mm:ss.S");
      try {
        date = df.parse(dateStr);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return date;
  }

  /**
   * @param dateStr yyyy-MM-dd格式的日期
   * @return
   */
  public static Date parseString2DateTo2(String dateStr) {
    Date date = null;
    if (StringUtils.isNotBlank(dateStr)) {
      dateStr = appendTimeTo(dateStr);
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
      try {
        date = df.parse(dateStr);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return date;
  }

  /**
   * 获取指定时间段的日期列表
   * 
   * @param dateFrom
   * @param dateTo
   * @param pattern
   * @return
   */
  public static List<String> getDateRangeList(Date dateFrom, Date dateTo, String pattern) {
    List<String> dateRangeList = new ArrayList<String>();
    Date date = dateFrom;
    Calendar calendar = Calendar.getInstance();
    while (date.compareTo(dateTo) <= 0) {
      dateRangeList.add(parseDate2String(date, pattern));
      calendar.setTime(date);
      calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
      date = calendar.getTime();
    }
    return dateRangeList;
  }

  /**
   * 根据基准日期获取其偏移天数的日期
   * 
   * @param baseDate
   * @param offsetDay 正数把日期往后移动,负数往前移动
   * @return 日期字符串。格式："yyyy-MM-dd"
   */
  public static String getDate(Date baseDate, int offsetDay) {
	String format = "yyyy-MM-dd";
	return getDateWithFormat(baseDate, offsetDay, format);
//    Calendar calendar = new GregorianCalendar();
//    calendar.setTime(baseDate);
//    calendar.add(calendar.DATE, offsetDay);// 把日期往后增加一天.整数往后推,负数往前移动
//    baseDate = calendar.getTime(); // 这个时间就是日期往后推一天的结果
//    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//    String dateString = formatter.format(baseDate);
//
//    return dateString;
  }
  
  /**
   * 根据基准日期获取其偏移天数的日期
   * 
   * @param baseDate
   * @param offsetDay 正数把日期往后移动,负数往前移动
   * @param format 格式
   * @return 日期字符串。格式化后的string
   */
  public static String getDateWithFormat(Date baseDate, int offsetDay, String format) {
    Calendar calendar = new GregorianCalendar();
    if (baseDate == null)
    	baseDate = new Date();
    calendar.setTime(baseDate);
    calendar.add(calendar.DATE, offsetDay);// 把日期往后增加一天.整数往后推,负数往前移动
    baseDate = calendar.getTime(); // 这个时间就是日期往后推一天的结果
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    String dateString = formatter.format(baseDate);

    return dateString;
  }

  /**
   * 获取当前应用服务器的系统日期时间
   * 
   * @return
   */
  public static Date getCurrentDateTime() {
    return new Date(System.currentTimeMillis());
  }


  /**
   * 获取当前应用服务器的系统日期时间
   * 
   * @return 日期时间字符串，格式：yyyy-MM-dd HH:mm:ss
   */
  public static String getCurrentDateTimeStr() {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return df.format(new Date(System.currentTimeMillis()));
  }

  /**
   * 获取两时间的相隔秒数
   * 
   * @param date
   * @return
   */
  public static int dateBetween(Date dateFrom, Date dateTo) {

    Calendar startCalendar = Calendar.getInstance();
    Calendar endCalendar = Calendar.getInstance();
    long datespan = (dateTo.getTime() - dateFrom.getTime()) / 1000;
    return (int) datespan;

  }

  /**
   * add by fy-14-5-28 from bucea
   * 
   * @param date
   * @return
   */
  public static int getWeekIndexOfDate(Date date) {
    int[] weekDaysName = {7, 1, 2, 3, 4, 5, 6};
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    return weekDaysName[intWeek];
  }

  /**
   * add by fy-14-5-28 from bucea 转换日期格式
   * 
   * @param date 日期格式为"yyyy年MM月dd日"
   * @return "yyyy-MM-dd"
   */

  public static String getTodayDate(String date) {
    if(date==null){
      return null;
    }
    String[] dataString = date.split("年");
    String year = dataString[0].toString();
    String month = dataString[1].split("月")[0].toString();
    String data = dataString[1].split("月")[1].split("日")[0].toString();
    String todayData = year + "-" + month + "-" + data;
    return todayData;
  }

  /**
   * add by fy-14-6-3 fro bucea
   * 
   * @param date 根据yyyy-MM-dd格式的日期返回星期几
   * @return
   */

  public static String getWeekOfDate(Date date) {
    String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    return weekDaysName[intWeek];
  }

  // 获得当前日期与本周日相差的天数
  public static int getMondayPlus() {
    Calendar cd = Calendar.getInstance();
    // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
    int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
    if (dayOfWeek == 1) {
      return 0;
    } else {
      return 1 - dayOfWeek;
    }
  }

  // 获取获取当前日期的一周时间日期列表
  public static List<WeekDate> getWeekDateList() {

    int mondayPlus = getMondayPlus();
    // 获取周一日期
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, mondayPlus);
    Date monday = currentDate.getTime();
    // 获取周日日期
    GregorianCalendar currentDates = new GregorianCalendar();
    currentDates.add(GregorianCalendar.DATE, mondayPlus + 6);
    Date weekday = currentDates.getTime();

    List<WeekDate> weekDateList = new ArrayList<WeekDate>();
    Calendar calendar = Calendar.getInstance();
    while (monday.compareTo(weekday) <= 0) {
      WeekDate weekDate = new WeekDate();
      weekDate.setCurrentDate(parseDate2String(monday, "yyyy-MM-dd"));
      weekDate.setTheWeekDate(DateUtils.getWeekOfDate(monday));
      weekDateList.add(weekDate);
      calendar.setTime(monday);
      calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
      monday = calendar.getTime();
    }
    return weekDateList;
  }

  // 获取两周的日历列表
  public static List<WeekDate> getTwoWeekDateList() {
    int mondayPlus = getMondayPlus();
    // 获取周一日期
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, mondayPlus - 7);
    Date monday = currentDate.getTime();
    // 获取周日日期
    GregorianCalendar currentDates = new GregorianCalendar();
    currentDates.add(GregorianCalendar.DATE, mondayPlus + 6);
    Date weekday = currentDates.getTime();

    List<WeekDate> weekDateList = new ArrayList<WeekDate>();
    Calendar calendar = Calendar.getInstance();
    while (monday.compareTo(weekday) <= 0) {
      WeekDate weekDate = new WeekDate();
      weekDate.setCurrentDate(parseDate2String(monday, "yyyy-MM-dd"));
      weekDate.setTheWeekDate(DateUtils.getWeekOfDate(monday));
      weekDateList.add(weekDate);
      calendar.setTime(monday);
      calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
      monday = calendar.getTime();
    }
    return weekDateList;
  }

  // 获取 上一周日历表
  public static List<WeekDate> lastWeekDates() {
    int mondayPlus = getMondayPlus();
    // 获取周一日期
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, mondayPlus - 7);
    Date monday = currentDate.getTime();
    // 获取周日日期
    GregorianCalendar currentDates = new GregorianCalendar();
    currentDates.add(GregorianCalendar.DATE, mondayPlus + 6 - 7);
    Date weekday = currentDates.getTime();

    List<WeekDate> weekDateList = new ArrayList<WeekDate>();
    Calendar calendar = Calendar.getInstance();
    while (monday.compareTo(weekday) <= 0) {
      WeekDate weekDate = new WeekDate();
      weekDate.setCurrentDate(parseDate2String(monday, "yyyy-MM-dd"));
      weekDate.setTheWeekDate(DateUtils.getWeekOfDate(monday));
      weekDateList.add(weekDate);
      calendar.setTime(monday);
      calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
      monday = calendar.getTime();
    }
    return weekDateList;
  }

  /**
   * 获取一周星期一的日期
   * 
   * @param currentDate
   * @return
   */
  public static String getWeekFirstDay(String currentDate) {
    Date date = parseString2Date(currentDate, "yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int week = cal.get(Calendar.DAY_OF_WEEK);
    if (week == 1) {
      week = 8;
    }
    cal.add(Calendar.DAY_OF_MONTH, -week + 2);
    return parseDate2String(cal.getTime(), "yyyy-MM-dd");
  }

  /**
   * 获取一周星期日的日期
   * 
   * @param currentDate
   * @return
   */
  public static String getWeekLastDay(String currentDate) {
    Date date = parseString2Date(currentDate, "yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int week = cal.get(Calendar.DAY_OF_WEEK);
    if (week == 1) {
      week = 8;
    }
    cal.add(Calendar.DAY_OF_MONTH, -week + 8);
    return parseDate2String(cal.getTime(), "yyyy-MM-dd");
  }
  
  /**
   * 将秒数转为时间
   * 
   * @param time 秒数表示的时间
   * @return 时间格式的字符串
   */
  public static String formatTime(String time) {
    if (StringUtils.isNotBlank(time) && time.lastIndexOf(":") == -1) {
      Double dTime = Double.parseDouble(time);
      Integer min = (int) (dTime / 60);
      Integer sec = (int) (dTime % 60);
      String smin = min < 10 ? "0" + min : min + "";
      String ssec = sec < 10 ? "0" + sec : sec + "";
      return smin + ":" + ssec;
    }
    return "00:00";
  }
  
}
