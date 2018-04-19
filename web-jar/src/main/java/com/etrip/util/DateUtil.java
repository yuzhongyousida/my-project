package com.etrip.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/11/4.
 */
public class DateUtil {

    public static String[] MONTHS = { "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" };

    private static int[] DOMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private static int[] lDOMonth = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    public static final String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String PATTERN_yyyyMMdd = "yyyyMMdd";
    public static final String PATTERN_MMdd = "MM-dd";
    public static final String PATTERN_yyMMdd = "yy/MM/dd";
    public static final String PATTERN_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String PATTERN_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    public static final String PATTERN_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_HH_mm_ss = "HH:mm:ss";
    public static final String PATTERN_HHmmss = "HHmmss";
    public static final String PATTERN_yyyyMMdd_HHmmss = "yyyy/MM/dd HH:mm:ss";
    public static final String PATTERN_yyyyMMdd2 = "yyyy/MM/dd";

    private static final Logger LOG = Logger.getLogger(DateUtil.class);


    /**
     * 获取日期的年
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Object year = Integer.valueOf(cal.get(Calendar.YEAR));
        if (year != null) {
            return Integer.valueOf(year.toString()).intValue();
        }
        return 0;
    }


    /**
     * 获取日期的月
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        Object day = Integer.valueOf(cal.get(Calendar.MONTH));
        if (day != null) {
            return (Integer.valueOf(day.toString()).intValue() + 1);
        }
        return 0;
    }


    /**
     * 获取今年某月的总天数
     * @param month
     * @return
     */
    public static int getDaysOfmonth(int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        if (cal.get(Calendar.YEAR) % 4 == 0) {
            if ((cal.get(Calendar.YEAR) % 100 == 0) && (cal.get(Calendar.YEAR) % 400 != 0)) {
                return DOMonth[cal.get(Calendar.MONTH)];
            }
            return lDOMonth[cal.get(Calendar.MONTH)];
        }
        return DOMonth[cal.get(Calendar.MONTH)];
    }

    /**
     * 获取当前天的0点0时0分0秒
     * @return
     */
    public static Calendar getClearCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }


    /**
     * 获取某日期之后多少天的0时0分0秒的日期
     * @param date
     * @param days
     * @return
     */
    public static Date getDateAfter0000Date(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取某日期之后多少天的23时59分59秒999微秒的日期
     * @param date
     * @param days
     * @return
     */
    public static Date getDateAfter2359Date(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }


    /**
     * 获取某日期多少天之后的日期
     * @param date
     * @param days
     * @return
     */
    public static Date getDateAfterDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }


    /**
     * 获取某日期多少天之前的日期
     * @param date
     * @param days
     * @return
     */
    public static Date getDateBeforeDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -days);
        return cal.getTime();
    }



    /**
     * 获取某日期多少小时前的日期
     * @param date
     * @param hour
     * @return
     */
    public static Date getDateBeforeHours(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, -hour);
        return cal.getTime();
    }


    /**
     * 获取某日期多少小时后的日期
     * @param date
     * @param hour
     * @return
     */
    public static Date getDateAfterHours(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hour);
        return cal.getTime();
    }


    /**
     * 获取某日期多少分钟前的日期
     * @param date
     * @param minute
     * @return
     */
    public static Date getDateBeforeMinute(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, -minute);
        return cal.getTime();
    }


    /**
     * 获取某日期多少分钟后的日期
     * @param date
     * @param minute
     * @return
     */
    public static Date getDateAfterMinute(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    /**
     * 获取某日期多少秒后的日期
     * @param date
     * @param second
     * @return
     */
    public static Date getDateAfterSecond(Date date, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, second);
        return cal.getTime();
    }

    /**
     * 获取两个日期之间的间隔天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDaysBetween(Date startDate, Date endDate) {
        boolean isPlus = true;
        Calendar d1 = Calendar.getInstance();
        d1.setTime(startDate);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(endDate);
        if (d1.after(d2)) {
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
            isPlus = false;
        }
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar)d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        if(!isPlus){
            days = 0-days;
        }
        return days;
    }

    /**
     * 获取两个日期之间的间隔天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getYearsBetween(Date startDate, Date endDate) {
        boolean isPlus = true;
        Calendar d1 = Calendar.getInstance();
        d1.setTime(startDate);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(endDate);
        if (d1.after(d2)) {
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
            isPlus = false;
        }
        int years = d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR);
        if(!isPlus){
            years = 0-years;
        }
        return years;
    }

    /**
     *   获取两个日期之间的日期信息 (包含前后日期)
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static List<String> getDateInfo(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);
        List<String> dates = new ArrayList<String>();
        int i = 0;
        while (true) {
            if (startCalendar.getTimeInMillis() == endCalendar.getTimeInMillis()&&i==0){
                dates.add(df.format(startDate));
                i++;
            }
                startCalendar.add(Calendar.DAY_OF_MONTH, 1);
            if (startCalendar.getTimeInMillis() <= endCalendar.getTimeInMillis()) {
                if (i == 0) {
                    dates.add(df.format(startDate));
                }
                dates.add(df.format(startCalendar.getTime()));
                i++;
            } else {
                break;
            }
        }
        return dates;
    }

    public static Date getMonthBeforeMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -months);
        return cal.getTime();
    }

    public static Date getYearBeforeYear(Date date, int year) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -year);
        return cal.getTime();
    }

    public static Date getYearAfterYear(Date date, int year) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, year);
        return cal.getTime();
    }


    /**
     * 获取下一分钟的时间,比如将2018-01-05 11:53:01 转化为2018-01-05 11:54:00
     * @param date
     * @return
     */
    public static Date getNextMinute(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, 1);
        return cal.getTime();
    }
























    public static Date getDateAfterMinutes(long duration) {
        long curr = System.currentTimeMillis();
        curr += duration * 60L * 1000L;
        return new Date(curr);
    }

    public static String formatDate(Date date, String format) {
        return getFormatDate(date, format);
    }

    public static String getFormatDate(Date date, String format)
    {
        if (date != null) {
            SimpleDateFormat f = new SimpleDateFormat(format);
            return f.format(date);
        }
        return null;
    }

    public static String getFormatUTCDate(Date date, String format)
    {
        if (date != null) {
            SimpleDateFormat f = new SimpleDateFormat(format);
            f.setTimeZone(TimeZone.getTimeZone("UTC"));
            return f.format(date);
        }
        return null;
    }

    public static String getZHDay(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (calendar.get(7))
        {
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            case 1:
                return "星期日";
        }
        return "";
    }

    public static Date DsDay_Hour(Date date, Integer hours)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(10, hours.intValue());

        return cal.getTime();
    }

    public static Date accurateToDay(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return clearDateAfterDay(cal);
    }

    public static int getDayOfWeek(Calendar cal) {
        cal.set(5, 1);
        return cal.get(7);
    }

    public static Date getTheMiddle(Date date, int plus) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(5, 1);
        cal.add(2, plus);
        return cal.getTime();
    }

    public static Map<String, Object> getBeginAndEndDateByDate(Date date)
    {
        Calendar calClearDate = Calendar.getInstance();
        calClearDate.setTime(date);
        calClearDate.set(5, 1);
        date = calClearDate.getTime();
        Map map = new HashMap();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        int dayOfWeek = getDayOfWeek(cal);
        cal.set(5, -(dayOfWeek - 2));
        map.put("beginDate", cal.getTime());
        cal.add(5, 21);
        map.put("currPageDate", cal.getTime());
        cal.add(5, 20);
        map.put("endDate", cal.getTime());
        return map;
    }

    public static String getDateTime(String format, Date aDate)
    {
        SimpleDateFormat df = new SimpleDateFormat(format);
        String returnValue = df.format(aDate);
        return returnValue;
    }

    public static Date DsDay_Minute(Date date, int minute)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int minutes = calendar.get(12);
        calendar.set(12, minutes + minute);
        return calendar.getTime();
    }

    public static Date DsDay_Second(Date date, int second)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int seconds = calendar.get(13);
        calendar.set(13, seconds + second);
        return calendar.getTime();
    }

    public static Date clearDateAfterDay(Calendar c)
    {
        c.set(14, 0);
        c.set(13, 0);
        c.set(12, 0);
        c.set(11, 0);
        return c.getTime();
    }

    public static Date toDate(String sdate, String fmString)
    {
        DateFormat df = new SimpleDateFormat(fmString);
        try {
            return df.parse(sdate);
        } catch (ParseException e) {
            throw new RuntimeException("日期格式不正确 ");
        }
    }

    public static int getAge(Date birthDay)
    {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            return 0;
        }

        int yearNow = cal.get(1);
        int monthNow = cal.get(2) + 1;
        int dayOfMonthNow = cal.get(5);
        cal.setTime(birthDay);

        int yearBirth = cal.get(1);
        int monthBirth = cal.get(2) + 1;
        int dayOfMonthBirth = cal.get(5);

        int age = yearNow - yearBirth;
        if ((monthNow < monthBirth) || ((monthNow == monthBirth) && (dayOfMonthNow < dayOfMonthBirth)))
        {
            --age;
        }
        return age;
    }



    public static Date dsDay_Date(Date date, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int days = calendar.get(5);
        calendar.set(5, days + day);
        Date cc = calendar.getTime();
        return cc;
    }

    public static String getFormatYear(Date date)
    {
        String str = null;
        if (date != null) {
            str = DateFormatUtils.format(date, "yyyy");
        }
        return str;
    }

    public static List<Date> getDateList(Date beginDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        List dateList = new ArrayList();

        dateList.add(beginDate);

        while (!(clean(beginDate).equals(endDate)))
        {
            calendar.add(5, 1);
            Date currentDate = calendar.getTime();
            dateList.add(currentDate);
            if (currentDate.after(endDate)) break; if (clean(currentDate).equals(clean(endDate))) {
            break;
        }

        }

        return dateList;
    }

    public static Date getYestoday18Hour()
            throws ParseException
    {
        Date d = new Date();
        SimpleDateFormat simpleOldDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

        Calendar ca = Calendar.getInstance();
        ca.setTime(simpleOldDate.parse(simpleOldDate.format(d)));
        ca.add(5, -1);
        ca.add(10, 18);
        return ca.getTime();
    }

    private static Date clean(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(10, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static Date getAfterDay(Date date) {
        SimpleDateFormat simpleOldDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(simpleOldDate.parse(simpleOldDate.format(date)));
        } catch (ParseException e) {
            ca.setTime(getDayStart(new Date()));
        }
        ca.add(5, 1);
        return ca.getTime();
    }

    public static Date getBeforeDay(Date date)
    {
        SimpleDateFormat simpleOldDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(simpleOldDate.parse(simpleOldDate.format(date)));
        } catch (ParseException e) {
            ca.setTime(getDayStart(new Date()));
        }
        ca.add(5, -1);
        return ca.getTime();
    }

    public static Date getToDay(Date date)
    {
        SimpleDateFormat simpleOldDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(simpleOldDate.parse(simpleOldDate.format(date)));
        } catch (ParseException e) {
            ca.setTime(getDayStart(new Date()));
        }
        return ca.getTime();
    }

    public static Date getDayStart(Date date)
    {
        if (date == null)
            return null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        return c.getTime();
    }

	public static Date getDayEnd(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

    public static Date getDayEndBeforeOneH(Date date)
    {
        if (date == null)
            return null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(11, 23);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        return c.getTime();
    }

    public static boolean inAdvance(Date date1, Date date2)
    {
        if ((date1 == null) || (date2 == null)) {
            return false;
        }
        return (date1.getTime() < date2.getTime());
    }

    public static Date mergeDateTime(Date date, Date time)
    {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(time);
        dateCalendar.set(10, timeCalendar.get(10));
        dateCalendar.set(12, timeCalendar.get(12));
        dateCalendar.set(13, timeCalendar.get(13));
        return dateCalendar.getTime();
    }

    public static Date getDateByStr(String dateStr, String formate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            LOG.error(e);
        }
        return date;
    }

    public static Date stringToDate(String dateStr, String formatStr)
    {
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            LOG.error(e);
        }
        return date;
    }

    public static boolean compareDateLessOneDayMore(Date date1, Date date2)
    {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        if (c1.get(1) > c2.get(1))
            return true;
        if ((c1.get(1) == c2.get(1)) && (c1.get(2) > c2.get(2)))
        {
            return true;
        }

        return ((c1.get(1) == c2.get(1)) && (c1.get(2) == c2.get(2)) && (c1.get(5) > c2.get(5)));
    }

    public static Date mergeDateTimeAddYear(Date date, Integer years)
    {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        dateCalendar.set(1, dateCalendar.get(1) + years.intValue());

        return dateCalendar.getTime();
    }

    public static Date mergeDateTimeAddMonth(Date date, Integer Months)
    {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        dateCalendar.set(2, dateCalendar.get(2) + Months.intValue());

        return dateCalendar.getTime();
    }

    public static Long getMinBetween(Date startDate, Date endDate) {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(startDate);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(endDate);
        Long min = Long.valueOf(0L);
        if (d1.getTimeInMillis() > d2.getTimeInMillis())
            min = Long.valueOf((d1.getTimeInMillis() - d2.getTimeInMillis()) / 60000L);
        else {
            min = Long.valueOf((d2.getTimeInMillis() - d1.getTimeInMillis()) / 60000L);
        }
        return min;
    }

    public static Long getMillisBetween(Date startDate, Date endDate)
    {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(startDate);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(endDate);
        Long min = Long.valueOf(0L);
        if (d1.getTimeInMillis() > d2.getTimeInMillis())
            min = Long.valueOf((d1.getTimeInMillis() - d2.getTimeInMillis()) / 1000L);
        else {
            min = Long.valueOf((d2.getTimeInMillis() - d1.getTimeInMillis()) / 1000L);
        }
        return min;
    }


    public static Date getTodayYMDDate()
    {
        Calendar c = Calendar.getInstance();
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        return c.getTime();
    }

    public static int getTodayHourDate()
    {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(11);
        return hour;
    }

    public static Date getTodayDate()
    {
        return stringToDate(formatDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
    }

    public static float convertToHours(Long minutes)
    {
        if (minutes == null) {
            return 0.0F;
        }
        String fStr = (new Float(new StringBuilder().append(minutes).append("").toString()).floatValue() / 60.0F) + "";
        String res = fStr.substring(0, fStr.lastIndexOf(".") + 2);
        return Float.parseFloat(res);
    }


    public static long convertToMinutes(Float hours)
    {
        if (hours == null) {
            return 0L;
        }
        long h = hours.intValue() * 60;
        Float f = Float.valueOf((hours.floatValue() - new Float(hours.intValue()).floatValue()) * 60.0F);
        String fStr = f.toString();
        return (h + Long.parseLong(fStr.substring(0, fStr.indexOf("."))));
    }

    public static Date toYMDDate(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        return c.getTime();
    }

    public static Date getFirstdayOfMonth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(5, 1);
        return cal.getTime();
    }

    public static Date getLastdayOfMonth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMaximum(5);
        cal.set(5, value);
        date = cal.getTime();
        return date;
    }

    public static String getRemainTimeByCurrentDate(Date date)
    {
        String str = "剩余0天0时0分";
        if (null != date) {
            Date d = new Date();
            long seconds = (date.getTime() - d.getTime()) / 1000L;
            if (seconds > 0L) {
                long day = seconds / 86400L;
                long house = seconds % 86400L / 3600L;
                long min = seconds % 3600L / 60L;
                return "剩余" + day + "天" + house + "时" + min + "分";
            }
        }

        return str;
    }

    public static Date getTimesByTimes(int Month, int day)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(2, Month);
        cal.add(5, day);
        return cal.getTime();
    }

    public static long getDateTime(String date)
    {
        if (StringUtils.isEmpty(date)) {
            return 0L;
        }
        Date d = toDate(date, "yyyy-MM-dd");
        return d.getTime();
    }

    public static String compareDate(Date date1, Date date2)
    {
        if ((date1 == null) || (date2 == null)) {
            return null;
        }
        if (date1.getTime() - date2.getTime() < 0L) {
            return null;
        }
        long seconds = (date1.getTime() - date2.getTime()) / 1000L;
        Long day = Long.valueOf(seconds / 86400L);
        Long hour = Long.valueOf(seconds % 86400L / 3600L);
        Long min = Long.valueOf(seconds % 3600L / 60L);
        String str = "";
        if (day.longValue() > 0L) {
            str = str + day + "天";
        }
        if (hour.longValue() > 0L) {
            str = str + hour + "小时";
        }
        if (min.longValue() > 0L) {
            str = str + min + "分";
        }
        return str;
    }

    public static Date defineDate(Date date, String time)
            throws Exception
    {
        try
        {
            String[] strs = time.split(":");
            int hour = Integer.parseInt(strs[0]);
            int minute = Integer.parseInt(strs[1]);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(11, hour);
            c.set(12, minute);
            c.set(13, 0);
            c.set(14, 0);
            return c.getTime();
        } catch (Exception e) {
            throw new Exception("给入的时间点参数格式错误！");
        }
    }

    public static List<Date> converFromString(String addition) {
        String[] ds = addition.split(",");
        List dateList = new ArrayList();
        for (String d : ds) {
            d = StringUtils.trimToNull(d);
            if (d == null)
                continue;
            try
            {
                dateList.add(new SimpleDateFormat("yyyyMMdd").parse(d));
            } catch (ParseException e) {
                LOG.error(e);
            }
        }
        Collections.sort(dateList);
        return dateList;
    }

    public static Date convertDateFromMinuteStr(String str) throws ParseException {
        return new SimpleDateFormat(PATTERN_yyyy_MM_dd_HH_mm_ss).parse(str);
    }

    public static long convertTimeStamp(String tsStr) throws ParseException {
        Date date = convertDateFromMinuteStr(tsStr);
        return date.getTime();
    }


    public static String convertDate(Date specDate) {
        return new SimpleDateFormat("yyyyMMdd").format(specDate);
    }

    public static Date converDateFromStr(String str) throws ParseException {
        return new SimpleDateFormat("yyyyMMdd").parse(str);
    }

    public static Date converDateFromStr2(String str) throws ParseException {
        return new SimpleDateFormat("yyyy/MM/dd").parse(str);
    }

    public static Date converDateFromStr3(String str) throws ParseException {
        if (StringUtils.isEmpty(str)) {
            return new Date();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = format.parse(str);
        return date;
    }

    public static String convertDate3(Date specDate)
    {
        String str = "";
        try {
            str = new SimpleDateFormat("yyyy-MM-dd").format(specDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return str;
    }
    public static String convertDate33(Date specDate)
    {
        String str = "";
        try {
            str = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(specDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return str;
    }
    public static String convertDateDian(Date specDate)
    {
        String str = "";
        try {
            str = new SimpleDateFormat("yyyy.MM.dd").format(specDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return str;
    }

    public static Date getBeforeDayBySecond(int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(13, calendar.get(13) - second);
        return calendar.getTime();
    }

    public static List<String> getLimitDateByWeek(String beginDate, String endDate, String weeks) {
        List dates = new ArrayList();
        Date begin = getDateByStr(beginDate, "yyyy-MM-dd");
        Date end = getDateByStr(endDate, "yyyy-MM-dd");
        while (!(begin.after(end))) {
            Calendar c = Calendar.getInstance();
            c.setTime(begin);
            int day = c.get(7) - 1;
            if (weeks.contains("" + day)) {
                dates.add(formatDate(begin, "yyyy-MM-dd"));
            }
            begin = dsDay_Date(begin, 1);
        }
        return dates;
    }

    public static int daysBetween(String smdate, String bdate)
    {
        long between_days = 0L;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / 86400000L;
        } catch (Exception ex) {
            LOG.error(ex);
        }
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Date getDate_Month(Date date, int month)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int monthTest = calendar.get(2);
        calendar.set(2, monthTest + month);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        Date cc = calendar.getTime();
        return cc;
    }

    public static String minutesToDate(Long minutes) {
        if (minutes == null) {
            return "";
        }
        double time = minutes.longValue();
        int day = 0;
        int hour = 0;
        int minute = 0;
        if (time > 0.0D) {
            day = (int) Math.ceil(time / 1440.0D);
            if (time % 1440.0D == 0.0D) {
                hour = 0;
                minute = 0;
            } else {
                hour = (int)(1440.0D - (time % 1440.0D)) / 60;
                minute = (int)(1440.0D - (time % 1440.0D)) % 60;
            }
        } else if (time < 0.0D) {
            time = -time;
            hour = (int)time / 60;
            minute = (int)time % 60;
        }
        String hourStr = "" + hour; String minuteStr = "" + minute;
        if (hour < 10)
            hourStr = "0" + hour;
        if (minute < 10)
            minuteStr = "0" + minute;
        return day + "天" + hourStr + "点" + minuteStr + "分";
    }

    public static String getLogTime(Date date)
    {
        if (date != null) {
            SimpleDateFormat logFormat = new SimpleDateFormat("MM月dd日 HH时mm分ss秒");
            return logFormat.format(date);
        }
        return null;
    }

    public static long getMinutesBetween(Date startDate, Date endDate)
    {
        return ((startDate.getTime() - endDate.getTime()) / 1000L / 60L);
    }

    public static Date getMonthFirstDay(){
        Calendar calender = Calendar.getInstance();    //获取当前日期
        calender.add(Calendar.MONTH, 0);//设置当前月
        calender.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        return calender.getTime();
    }
    public static Date getMonthLastDay(){
    	 Calendar calender = Calendar.getInstance();
    	 calender.set(Calendar.DAY_OF_MONTH, calender.getActualMaximum(Calendar.DAY_OF_MONTH));
         return calender.getTime();
    }


    /**
     * 得到今天开始时间
     *
     * @return 返回今天的开始时间
     */
    public static Date getTodayStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 得到当前日期的明天时间
     *
     * @param date 目标日期
     * @return 返回当前日期的明天开始时间
     */
    public static Date getTomorrowStartTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 得到今天结束时间
     *
     * @return 返回今天的结束时间
     */
    public static Date getTodayEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 判断时间是不是今天
     *
     * @param date
     * @return 是返回true，不是返回false
     */
    public static boolean isToday(Date date) {
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(PATTERN_yyyyMMdd);
        String nowDay = sf.format(now);
        String day = sf.format(date);
        return day.equals(nowDay);
    }

    /**
     * 判断时间是不是同一天
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 是返回true，不是返回false
     */
    public static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat sf = new SimpleDateFormat(PATTERN_yyyyMMdd);
        String nowDay = sf.format(date1);
        String day = sf.format(date2);
        return day.equals(nowDay);
    }
    
}
