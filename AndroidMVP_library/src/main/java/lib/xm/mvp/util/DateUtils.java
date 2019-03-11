//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lib.xm.mvp.util;


import java.io.Serializable;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import lib.xm.mvp.util.log.LogUtils;

public final class DateUtils implements Serializable {

    private static final long serialVersionUID = -3098985139095632110L;
    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    private DateUtils() {
    }

    public static long getMillisFromDateString(String dateString) {
        long diff = 0L;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date e = ft.parse(dateString);
            diff = e.getTime();
        } catch (ParseException var5) {
            var5.printStackTrace();
        }

        return diff;
    }

    public static String getDateString() {
        String label = formatDate(new Date(), "MM月dd日 HH:mm");
        return label;
    }

    public static String currentDatetime() {
        return datetimeFormat.format(now());
    }

    public static Date getPreviousMonthEnd(Date date) {

        Calendar lastDate = Calendar.getInstance();
        lastDate.setTime(date);
        lastDate.add(Calendar.MONTH, -1);//减一个月
        lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天
        return lastDate.getTime();
    }

    public static int getLastDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Date getLastDateOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, getLastDaysOfMonth(date));
        return calendar.getTime();
    }

    public static String formatDatetime(Date date) {
        return datetimeFormat.format(date);
    }

    public static Date getNextMonthEnd(Date date) {
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);//加一个月
        lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天
        return lastDate.getTime();
    }

    public static String formatDatetime(Date date, String pattern) {
        SimpleDateFormat customFormat = (SimpleDateFormat) datetimeFormat.clone();
        customFormat.applyPattern(pattern);
        return customFormat.format(date);
    }

    public static String currentDate() {
        return dateFormat.format(now());
    }

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static String currentTime() {
        return timeFormat.format(now());
    }

    public static String formatTime(Date date) {
        return timeFormat.format(date);
    }

    public static Date getFirstDayOfMonth(Date date) {

        Calendar lastDate = Calendar.getInstance();
        lastDate.setTime(date);
        lastDate.set(Calendar.DATE, 1);//设为当前月的1号

        return lastDate.getTime();
    }

    public static Date now() {
        return new Date();
    }

    public static Calendar calendar() {
        Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        return cal;
    }

    public static long getCurrentMillis() {
        return System.currentTimeMillis();
    }

    public static int month() {
        return calendar().get(2) + 1;
    }

    public static int dayOfMonth() {
        return calendar().get(5);
    }

    public static int dayOfWeek() {
        return calendar().get(7);
    }

    public static int dayOfYear() {
        return calendar().get(6);
    }

    public static boolean isBefore(Date src, Date dst) {
        return src.before(dst);
    }

    public static boolean isAfter(Date src, Date dst) {
        return src.after(dst);
    }

    public static boolean isEqual(Date date1, Date date2) {
        return date1.compareTo(date2) == 0;
    }

    public static boolean between(Date beginDate, Date endDate, Date src) {
        Calendar dateTime = Calendar.getInstance();
        Calendar tempDate = Calendar.getInstance();
        Calendar tempDate2 = Calendar.getInstance();
        dateTime.setTime(beginDate);
        tempDate.setTime(endDate);
        tempDate2.setTime(src);
        dateTime.set(Calendar.SECOND, 0);
        tempDate.set(Calendar.SECOND, 0);
        tempDate2.set(Calendar.SECOND, 0);
        dateTime.set(Calendar.MILLISECOND, 0);
        tempDate.set(Calendar.MILLISECOND, 0);
        tempDate2.set(Calendar.MILLISECOND, 0);
        return (tempDate2.getTime().getTime() - dateTime.getTime().getTime() >= 0) && (tempDate.getTime().getTime() - tempDate2.getTime().getTime() >= 0);

//        return (Math.abs(endDate.getTime() - src.getTime())<60*1000)&&(Math.abs(beginDate.getTime() - src.getTime())<60*1000);
    }

    public static Date lastDayOfMonth() {
        Calendar cal = calendar();
        cal.set(5, 0);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        cal.set(2, cal.get(2) + 1);
        cal.set(14, -1);
        return cal.getTime();
    }

    public static Date firstDayOfMonth() {
        Calendar cal = calendar();
        cal.set(5, 1);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    private static Date weekDay(int week) {
        Calendar cal = calendar();
        cal.set(7, week);
        return cal.getTime();
    }

    public static Date friday() {
        return weekDay(6);
    }

    public static Date saturday() {
        return weekDay(7);
    }

    public static Date sunday() {
        return weekDay(1);
    }

    public static Date parseDatetime(String datetime) throws ParseException {
        return datetimeFormat.parse(datetime);
    }

    public static Date parseDate(String date) throws ParseException {
        return dateFormat.parse(date);
    }

    public static Date parseTime(String time) throws ParseException {
        return timeFormat.parse(time);
    }

    public static Date formatDate(String date, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date parseDatetime(String datetime, String pattern) throws ParseException {
        SimpleDateFormat format = (SimpleDateFormat) datetimeFormat.clone();
        format.applyPattern(pattern);
        return format.parse(datetime);
    }

    public static String dateFormat(String sdate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        java.sql.Date date = java.sql.Date.valueOf(sdate);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static long getIntervalDays(String sd, String ed) {
        return (java.sql.Date.valueOf(ed).getTime() - java.sql.Date.valueOf(sd).getTime()) / 86400000L;
    }

    public static int getInterval(String beginMonth, String endMonth) {
        int intBeginYear = Integer.parseInt(beginMonth.substring(0, 4));
        int intBeginMonth = Integer.parseInt(beginMonth.substring(beginMonth.indexOf("-") + 1));
        int intEndYear = Integer.parseInt(endMonth.substring(0, 4));
        int intEndMonth = Integer.parseInt(endMonth.substring(endMonth.indexOf("-") + 1));
        return (intEndYear - intBeginYear) * 12 + (intEndMonth - intBeginMonth) + 1;
    }

    public static Date getDate(String sDate, String dateFormat) {
        SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);
        ParsePosition pos = new ParsePosition(0);
        return fmt.parse(sDate, pos);
    }

    public static String getCurrentYear() {
        return getFormatCurrentTime("yyyy");
    }

    public static String getBeforeYear() {
        String currentYear = getFormatCurrentTime("yyyy");
        int beforeYear = Integer.parseInt(currentYear) - 1;
        return "" + beforeYear;
    }

    public static String getCurrentMonth() {
        return getFormatCurrentTime("MM");
    }

    public static String getCurrentDay() {
        return getFormatCurrentTime("dd");
    }

    public static String getCurrentDate() {
        return getFormatDateTime(new Date(), "yyyy-MM-dd");
    }

    public static String getCurrentDateTime() {
        return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getFormatDate(Date date) {
        return getFormatDateTime(date, "yyyy-MM-dd");
    }

    public static String getFormatDate(String format) {
        return getFormatDateTime(new Date(), format);
    }

    public static String getFormatDate(Date date, String format) {
        return getFormatDateTime(date, format);
    }

    public static String getCurrentTime() {
        return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getFormatTime(Date date) {
        return getFormatDateTime(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getFormatShortTime(Date date) {
        return getFormatDateTime(date, "yyyy-MM-dd");
    }

    public static String getFormatCurrentTime(String format) {
        return getFormatDateTime(new Date(), format);
    }

    public static String getFormatDateTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date getDateObj(int year, int month, int day) {
        GregorianCalendar c = new GregorianCalendar();
        c.set(year, month - 1, day);
        return c.getTime();
    }

    public static String getDateTomorrow(String date) {
        Date tempDate = null;
        if (date.indexOf("/") > 0) {
            tempDate = getDateObj(date, "[/]");
        }

        if (date.indexOf("-") > 0) {
            tempDate = getDateObj(date, "[-]");
        }

        tempDate = getDateAdd(tempDate, 1);
        return getFormatDateTime(tempDate, "yyyy/MM/dd");
    }

    public static String getDateOffset(String date, int offset) {
        Date tempDate = null;
        if (date.indexOf("/") > 0) {
            tempDate = getDateObj(date, "[/]");
        }

        if (date.indexOf("-") > 0) {
            tempDate = getDateObj(date, "[-]");
        }

        tempDate = getDateAdd(tempDate, offset);
        return getFormatDateTime(tempDate, "yyyy/MM/dd");
    }

    public static Date getDateObj(String argsDate, String split) {
        String[] temp = argsDate.split(split);
        int year = (new Integer(temp[0])).intValue();
        int month = (new Integer(temp[1])).intValue();
        int day = (new Integer(temp[2])).intValue();
        return getDateObj(year, month, day);
    }

    public static Date getDateFromString(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date resDate = null;

        try {
            resDate = sdf.parse(dateStr);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resDate;
    }

    public static Date getDateObj() {
        GregorianCalendar c = new GregorianCalendar();
        return c.getTime();
    }

    public static int getDaysOfCurMonth() {
        int curyear = (new Integer(getCurrentYear())).intValue();
        int curMonth = (new Integer(getCurrentMonth())).intValue();
        int[] mArray = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (curyear % 400 == 0 || curyear % 100 != 0 && curyear % 4 == 0) {
            mArray[1] = 29;
        }

        return mArray[curMonth - 1];
    }

    public static int getDaysOfCurMonth(String time) {
        if (time.length() != 7) {
            throw new NullPointerException("参数的格式必须是yyyy-MM");
        } else {
            String[] timeArray = time.split("-");
            int curyear = (new Integer(timeArray[0])).intValue();
            int curMonth = (new Integer(timeArray[1])).intValue();
            if (curMonth > 12) {
                throw new NullPointerException("参数的格式必须是yyyy-MM，�?�且月份必须小于等于12�?");
            } else {
                int[] mArray = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                if (curyear % 400 == 0 || curyear % 100 != 0 && curyear % 4 == 0) {
                    mArray[1] = 29;
                }

                return curMonth == 12 ? mArray[0] : mArray[curMonth - 1];
            }
        }
    }

    public static int getDayofWeekInMonth(String year, String month, String weekOfMonth, String dayOfWeek) {
        GregorianCalendar cal = new GregorianCalendar();
        int y = (new Integer(year)).intValue();
        int m = (new Integer(month)).intValue();
        cal.clear();
        cal.set(y, m - 1, 1);
        cal.set(8, (new Integer(weekOfMonth)).intValue());
        cal.set(7, (new Integer(dayOfWeek)).intValue());
        return cal.get(5);
    }

    public static Date getDate(int year, int month, int date, int hourOfDay, int minute, int second) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(year, month, date, hourOfDay, minute, second);
        return cal.getTime();
    }

    public static int getDayOfWeek(String year, String month, String day) {
        GregorianCalendar cal = new GregorianCalendar((new Integer(year)).intValue(), (new Integer(month)).intValue() - 1, (new Integer(day)).intValue());
        return cal.get(7);
    }

    public static int getDayOfWeek(String date) {
        String[] temp = null;
        if (date.indexOf("/") > 0) {
            temp = date.split("/");
        }

        if (date.indexOf("-") > 0) {
            temp = date.split("-");
        }

        return getDayOfWeek(temp[0], temp[1], temp[2]);
    }

    public static String getChinaDayOfWeek(String date) {
        String[] weeks = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int week = getDayOfWeek(date);
        return weeks[week - 1];
    }

    public static int getDayOfWeek(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(7);
    }

    public static int getWeekOfYear(String year, String month, String day) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.clear();
        cal.set((new Integer(year)).intValue(), (new Integer(month)).intValue() - 1, (new Integer(day)).intValue());
        return cal.get(3);
    }

    public static Date getDateAdd(Date date, int amount) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(5, amount);
        return cal.getTime();
    }

    public static String getFormatDateAdd(Date date, int amount, String format) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(5, amount);
        return getFormatDateTime(cal.getTime(), format);
    }

    public static String getFormatCurrentAdd(int amount, String format) {
        Date d = getDateAdd(new Date(), amount);
        return getFormatDateTime(d, format);
    }

    public static String getFormatYestoday(String format) {
        return getFormatCurrentAdd(-1, format);
    }

    public static String getYestoday(String sourceDate, String format) {
        return getFormatDateAdd(getDateFromString(sourceDate, format), -1, format);
    }

    public static String getFormatTomorrow(String format) {
        return getFormatCurrentAdd(1, format);
    }

    public static String getFormatDateTommorrow(String sourceDate, String format) {
        return getFormatDateAdd(getDateFromString(sourceDate, format), 1, format);
    }

    public static String getCurrentDateString(String dateFormat) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(cal.getTime());
    }

    public static String getCurTimeByFormat(String format) {
        Date newdate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(newdate);
    }

    public static long getDiff(String startTime, String endTime) {
        long diff = 0L;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date e = ft.parse(startTime);
            Date endDate = ft.parse(endTime);
            diff = e.getTime() - endDate.getTime();
            diff /= 1000L;
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        return diff;
    }

    public static String getHour(long second) {
        long hour = second / 60L / 60L;
        long minute = (second - hour * 60L * 60L) / 60L;
        long sec = second - hour * 60L * 60L - minute * 60L;
        return hour + "小时" + minute + "分钟" + sec + "秒";
    }

    public static String getDateTime(long microsecond) {
        return getFormatDateTime(new Date(microsecond), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDateByAddFltHour(float flt) {
        int addMinute = (int) (flt * 60.0F);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(12, addMinute);
        return getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDateByAddHour(String datetime, int minute) {
        String returnTime = null;
        GregorianCalendar cal = new GregorianCalendar();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date = ft.parse(datetime);
            cal.setTime(date);
            cal.add(12, minute);
            returnTime = getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        return returnTime;
    }

    public static int getDiffHour(String startTime, String endTime) {
        long diff = 0L;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date e = ft.parse(startTime);
            Date endDate = ft.parse(endTime);
            diff = e.getTime() - endDate.getTime();
            diff /= 3600000L;
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        return (new Long(diff)).intValue();
    }

    public static String getYearSelect(String selectName, String value, int startYear, int endYear) {
        int start = startYear;
        int end = endYear;
        if (startYear > endYear) {
            start = endYear;
            end = startYear;
        }

        StringBuffer sb = new StringBuffer("");
        sb.append("<select name=\"" + selectName + "\">");

        for (int i = start; i <= end; ++i) {
            if (!value.trim().equals("") && i == Integer.parseInt(value)) {
                sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
            } else {
                sb.append("<option value=\"" + i + "\">" + i + "</option>");
            }
        }

        sb.append("</select>");
        return sb.toString();
    }

    public static String getYearSelect(String selectName, String value, int startYear, int endYear, boolean hasBlank) {
        int start = startYear;
        int end = endYear;
        if (startYear > endYear) {
            start = endYear;
            end = startYear;
        }

        StringBuffer sb = new StringBuffer("");
        sb.append("<select name=\"" + selectName + "\">");
        if (hasBlank) {
            sb.append("<option value=\"\"></option>");
        }

        for (int i = start; i <= end; ++i) {
            if (!value.trim().equals("") && i == Integer.parseInt(value)) {
                sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
            } else {
                sb.append("<option value=\"" + i + "\">" + i + "</option>");
            }
        }

        sb.append("</select>");
        return sb.toString();
    }

    public static String getYearSelect(String selectName, String value, int startYear, int endYear, boolean hasBlank, String js) {
        int start = startYear;
        int end = endYear;
        if (startYear > endYear) {
            start = endYear;
            end = startYear;
        }

        StringBuffer sb = new StringBuffer("");
        sb.append("<select name=\"" + selectName + "\" " + js + ">");
        if (hasBlank) {
            sb.append("<option value=\"\"></option>");
        }

        for (int i = start; i <= end; ++i) {
            if (!value.trim().equals("") && i == Integer.parseInt(value)) {
                sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
            } else {
                sb.append("<option value=\"" + i + "\">" + i + "</option>");
            }
        }

        sb.append("</select>");
        return sb.toString();
    }

    public static String getYearSelect(String selectName, String value, int startYear, int endYear, String js) {
        int start = startYear;
        int end = endYear;
        if (startYear > endYear) {
            start = endYear;
            end = startYear;
        }

        StringBuffer sb = new StringBuffer("");
        sb.append("<select name=\"" + selectName + "\" " + js + ">");

        for (int i = start; i <= end; ++i) {
            if (!value.trim().equals("") && i == Integer.parseInt(value)) {
                sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
            } else {
                sb.append("<option value=\"" + i + "\">" + i + "</option>");
            }
        }

        sb.append("</select>");
        return sb.toString();
    }

    public static String getMonthSelect(String selectName, String value, boolean hasBlank) {
        StringBuffer sb = new StringBuffer("");
        sb.append("<select name=\"" + selectName + "\">");
        if (hasBlank) {
            sb.append("<option value=\"\"></option>");
        }

        for (int i = 1; i <= 12; ++i) {
            if (!value.trim().equals("") && i == Integer.parseInt(value)) {
                sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
            } else {
                sb.append("<option value=\"" + i + "\">" + i + "</option>");
            }
        }

        sb.append("</select>");
        return sb.toString();
    }

    public static String getMonthSelect(String selectName, String value, boolean hasBlank, String js) {
        StringBuffer sb = new StringBuffer("");
        sb.append("<select name=\"" + selectName + "\" " + js + ">");
        if (hasBlank) {
            sb.append("<option value=\"\"></option>");
        }

        for (int i = 1; i <= 12; ++i) {
            if (!value.trim().equals("") && i == Integer.parseInt(value)) {
                sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
            } else {
                sb.append("<option value=\"" + i + "\">" + i + "</option>");
            }
        }

        sb.append("</select>");
        return sb.toString();
    }

    public static String getDaySelect(String selectName, String value, boolean hasBlank) {
        StringBuffer sb = new StringBuffer("");
        sb.append("<select name=\"" + selectName + "\">");
        if (hasBlank) {
            sb.append("<option value=\"\"></option>");
        }

        for (int i = 1; i <= 31; ++i) {
            if (!value.trim().equals("") && i == Integer.parseInt(value)) {
                sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
            } else {
                sb.append("<option value=\"" + i + "\">" + i + "</option>");
            }
        }

        sb.append("</select>");
        return sb.toString();
    }

    public static String getDaySelect(String selectName, String value, boolean hasBlank, String js) {
        StringBuffer sb = new StringBuffer("");
        sb.append("<select name=\"" + selectName + "\" " + js + ">");
        if (hasBlank) {
            sb.append("<option value=\"\"></option>");
        }

        for (int i = 1; i <= 31; ++i) {
            if (!value.trim().equals("") && i == Integer.parseInt(value)) {
                sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
            } else {
                sb.append("<option value=\"" + i + "\">" + i + "</option>");
            }
        }

        sb.append("</select>");
        return sb.toString();
    }

    public static int countWeekend(String startDate, String endDate) {
        int result = 0;
        Date sdate = null;
        Date edate = null;
        if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
            sdate = getDateObj(startDate, "/");
            edate = getDateObj(endDate, "/");
        }

        if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
            sdate = getDateObj(startDate, "-");
            edate = getDateObj(endDate, "-");
        }

        int sumDays = Math.abs(getDiffDays(startDate, endDate));
        boolean dayOfWeek = false;

        for (int i = 0; i <= sumDays; ++i) {
            int var8 = getDayOfWeek(getDateAdd(sdate, i));
            if (var8 == 1 || var8 == 7) {
                ++result;
            }
        }

        return result;
    }

    public static int getDiffDays(String startDate, String endDate) {
        long diff = 0L;
        SimpleDateFormat ft = null;
        if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
            ft = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }

        if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
            ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        try {
            Date e = ft.parse(startDate + " 00:00:00");
            Date eDate = ft.parse(endDate + " 00:00:00");
            diff = eDate.getTime() - e.getTime();
            diff /= 86400000L;
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        return (int) diff;
    }

    public static String[] getArrayDiffDays(String startDate, String endDate) {
        boolean LEN = false;
        if (startDate.equals(endDate)) {
            return new String[]{startDate};
        } else {
            Date sdate = null;
            if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
                sdate = getDateObj(startDate, "/");
            }

            if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
                sdate = getDateObj(startDate, "-");
            }

            int var6 = getDiffDays(startDate, endDate);
            String[] dateResult = new String[var6 + 1];
            dateResult[0] = startDate;

            for (int i = 1; i < var6 + 1; ++i) {
                if (startDate.indexOf("/") > 0 && endDate.indexOf("/") > 0) {
                    dateResult[i] = getFormatDateTime(getDateAdd(sdate, i), "yyyy/MM/dd");
                }

                if (startDate.indexOf("-") > 0 && endDate.indexOf("-") > 0) {
                    dateResult[i] = getFormatDateTime(getDateAdd(sdate, i), "yyyy-MM-dd");
                }
            }

            return dateResult;
        }
    }

    public static boolean isInStartEnd(String srcDate, String startDate, String endDate) {
        return startDate.compareTo(srcDate) <= 0 && endDate.compareTo(srcDate) >= 0;
    }

    public static String getQuarterSelect(String selectName, String value, boolean hasBlank) {
        StringBuffer sb = new StringBuffer("");
        sb.append("<select name=\"" + selectName + "\">");
        if (hasBlank) {
            sb.append("<option value=\"\"></option>");
        }

        for (int i = 1; i <= 4; ++i) {
            if (!value.trim().equals("") && i == Integer.parseInt(value)) {
                sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
            } else {
                sb.append("<option value=\"" + i + "\">" + i + "</option>");
            }
        }

        sb.append("</select>");
        return sb.toString();
    }

    public static String getQuarterSelect(String selectName, String value, boolean hasBlank, String js) {
        StringBuffer sb = new StringBuffer("");
        sb.append("<select name=\"" + selectName + "\" " + js + ">");
        if (hasBlank) {
            sb.append("<option value=\"\"></option>");
        }

        for (int i = 1; i <= 4; ++i) {
            if (!value.trim().equals("") && i == Integer.parseInt(value)) {
                sb.append("<option value=\"" + i + "\" selected>" + i + "</option>");
            } else {
                sb.append("<option value=\"" + i + "\">" + i + "</option>");
            }
        }

        sb.append("</select>");
        return sb.toString();
    }

    public static String changeDate(String argDate) {
        if (argDate != null && !argDate.trim().equals("")) {
            String result = "";
            if (argDate.length() == 10 && argDate.indexOf("/") > 0) {
                return argDate;
            } else {
                String[] str = argDate.split("[.]");
                int LEN = str.length;

                for (int i = 0; i < LEN; ++i) {
                    if (str[i].length() == 1) {
                        if (str[i].equals("0")) {
                            str[i] = "01";
                        } else {
                            str[i] = "0" + str[i];
                        }
                    }
                }

                if (LEN == 1) {
                    result = argDate + "/01/01";
                }

                if (LEN == 2) {
                    result = str[0] + "/" + str[1] + "/01";
                }

                if (LEN == 3) {
                    result = str[0] + "/" + str[1] + "/" + str[2];
                }

                return result;
            }
        } else {
            return "";
        }
    }

    public static String changeDateWithSplit(String argDate, String split) {
        if (argDate != null && !argDate.trim().equals("")) {
            if (split == null || split.trim().equals("")) {
                split = "-";
            }

            String result = "";
            if (argDate.length() == 10 && argDate.indexOf("/") > 0) {
                return argDate;
            } else if (argDate.length() == 10 && argDate.indexOf("-") > 0) {
                return argDate;
            } else {
                String[] str = argDate.split("[.]");
                int LEN = str.length;

                for (int i = 0; i < LEN; ++i) {
                    if (str[i].length() == 1) {
                        if (str[i].equals("0")) {
                            str[i] = "01";
                        } else {
                            str[i] = "0" + str[i];
                        }
                    }
                }

                if (LEN == 1) {
                    result = argDate + split + "01" + split + "01";
                }

                if (LEN == 2) {
                    result = str[0] + split + str[1] + split + "01";
                }

                if (LEN == 3) {
                    result = str[0] + split + str[1] + split + str[2];
                }

                return result;
            }
        } else {
            return "";
        }
    }

    public static int getNextMonthDays(String argDate) {
        String[] temp = null;
        if (argDate.indexOf("/") > 0) {
            temp = argDate.split("/");
        }

        if (argDate.indexOf("-") > 0) {
            temp = argDate.split("-");
        }

        GregorianCalendar cal = new GregorianCalendar((new Integer(temp[0])).intValue(), (new Integer(temp[1])).intValue() - 1, (new Integer(temp[2])).intValue());
        int curMonth = cal.get(2);
        cal.set(2, curMonth + 1);
        int curyear = cal.get(1);
        curMonth = cal.get(2);
        int[] mArray = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (curyear % 400 == 0 || curyear % 100 != 0 && curyear % 4 == 0) {
            mArray[1] = 29;
        }

        return mArray[curMonth];
    }

    public static void main(String[] args) {
        LogUtils.e(getCurrentDateTime());
        LogUtils.e("first=" + changeDateWithSplit("2000.1", ""));
        LogUtils.e("second=" + changeDateWithSplit("2000.1", "/"));
        String[] t = getArrayDiffDays("2008/02/15", "2008/02/19");

        int i;
        for (i = 0; i < t.length; ++i) {
            LogUtils.e(t[i]);
        }

        t = getArrayDiffDays("2008-02-15", "2008-02-19");

        for (i = 0; i < t.length; ++i) {
            LogUtils.e(t[i]);
        }

        LogUtils.e(getNextMonthDays("2008/02/15") + "||" + getCurrentMonth() + "||" + changeDate("1999"));
        LogUtils.e(changeDate("1999.1"));
        LogUtils.e(changeDate("1999.11"));
        LogUtils.e(changeDate("1999.1.2"));
        LogUtils.e(changeDate("1999.11.12"));
    }

    public static Date getSpecifiedDayAfter(Date date, int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + i);
        return c.getTime();
    }

    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    public static Date getApartDate(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);

        return now.getTime();
    }

    /**
     * 当月最后一天
     *
     * @return
     */
    public static Date getLastDay() {
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }

    /**
     * 当月第一天
     *
     * @return
     */
    public static Date getFirstDay() {
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return c.getTime();
    }

    public static Date lastDayOfPreMonth() {
        //获取前月的最后一天
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
        return cale.getTime();
    }

    public static Date firstDayOfPreMonth() {
        //获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return cal_1.getTime();
    }

    public static Date getApartMonth(Date date, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static Date getNextMonthLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

        return calendar.getTime();
    }

    public static int daysOfTwo(Date fDate, Date oDate) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fDate = sdf.parse(sdf.format(fDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            oDate = sdf.parse(sdf.format(oDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(fDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(oDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static int getMonthSpace(Date date1, Date date2) {
        Calendar c = Calendar.getInstance();
        c.setTime(date1);
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        c.setTime(date2);
        int year2 = c.get(Calendar.YEAR);
        int month2 = c.get(Calendar.MONTH);
        int result;
        if (year1 == year2) {
            result = month1 - month2;
        } else {
            result = 12 * (year1 - year2) + month1 - month2;
        }
        return result;
    }

    public static boolean isBeforeIgnoreSeconds(Date beforeDate, Date afterDate) {
        Calendar beforeCalendar = Calendar.getInstance();
        Calendar afterCalendar = Calendar.getInstance();
        beforeCalendar.setTime(beforeDate);
        afterCalendar.setTime(afterDate);
        beforeCalendar.set(Calendar.SECOND, 0);
        beforeCalendar.set(Calendar.MILLISECOND, 0);
        afterCalendar.set(Calendar.SECOND, 0);
        afterCalendar.set(Calendar.MILLISECOND, 0);
        return afterCalendar.getTime().getTime() > beforeCalendar.getTime().getTime();
    }

    public static boolean isBeforeIgnoreDayAndSeconds(Date beforeDate, Date afterDate) {
        Calendar beforeCalendar = Calendar.getInstance();
        Calendar afterCalendar = Calendar.getInstance();
        beforeCalendar.setTime(beforeDate);
        afterCalendar.setTime(afterDate);
        beforeCalendar.set(Calendar.YEAR, afterCalendar.get(Calendar.YEAR));
        beforeCalendar.set(Calendar.MONTH, afterCalendar.get(Calendar.MONTH));
        beforeCalendar.set(Calendar.DAY_OF_MONTH, afterCalendar.get(Calendar.DAY_OF_MONTH));
        beforeCalendar.set(Calendar.SECOND, 0);
        beforeCalendar.set(Calendar.MILLISECOND, 0);
        afterCalendar.set(Calendar.SECOND, 0);
        afterCalendar.set(Calendar.MILLISECOND, 0);
        return afterCalendar.getTime().getTime() > beforeCalendar.getTime().getTime();
    }

    public static long beforeIgnoreDayAndSeconds(Date beforeDate, Date afterDate) {
        Calendar beforeCalendar = Calendar.getInstance();
        Calendar afterCalendar = Calendar.getInstance();
        beforeCalendar.setTime(beforeDate);
        afterCalendar.setTime(afterDate);
        beforeCalendar.set(Calendar.YEAR, afterCalendar.get(Calendar.YEAR));
        beforeCalendar.set(Calendar.MONTH, afterCalendar.get(Calendar.MONTH));
        beforeCalendar.set(Calendar.DAY_OF_MONTH, afterCalendar.get(Calendar.DAY_OF_MONTH));
        beforeCalendar.set(Calendar.SECOND, 0);
        beforeCalendar.set(Calendar.MILLISECOND, 0);
        afterCalendar.set(Calendar.SECOND, 0);
        afterCalendar.set(Calendar.MILLISECOND, 0);
        return afterCalendar.getTime().getTime() - beforeCalendar.getTime().getTime();
    }

    public static long getBetweenIgnoreSeconds(Date beforeDate, Date afterDate) {
        Calendar beforeCalendar = Calendar.getInstance();
        Calendar afterCalendar = Calendar.getInstance();
        beforeCalendar.setTime(beforeDate);
        afterCalendar.setTime(afterDate);
        beforeCalendar.set(Calendar.SECOND, 0);
        beforeCalendar.set(Calendar.MILLISECOND, 0);
        afterCalendar.set(Calendar.SECOND, 0);
        afterCalendar.set(Calendar.MILLISECOND, 0);
        return afterCalendar.getTime().getTime() - beforeCalendar.getTime().getTime();
    }

    public static boolean isInRightTime(long currentTime, int beginHour, int beginMinute, int endHour, int endMinute, int endSecond) {
        Date date = new Date(currentTime);
        int hours = date.getHours();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();
        return hours >= beginHour && minutes >= beginMinute && endHour >= hours && endMinute >= hours && endSecond >= seconds;
    }

    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

}
