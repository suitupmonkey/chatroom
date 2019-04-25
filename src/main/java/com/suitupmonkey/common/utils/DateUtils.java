package com.suitupmonkey.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


/**
 * @Description: 日期处理类， 线程安全且多线程高效
 * @date: 2019.4.22
 * @author: zhangpan
 * @version: 1.0.0
 */
public final class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
    /**
     *
     */
    public static final TimeZone GMT = TimeZone.getTimeZone("GMT");
    /**
     *
     */
    public static final String FORMAT = "yyyy-MM-dd";
    /**
     *
     */
    public static final String FORMAT1 = "yyyy/MM/dd";
    /**
     *
     */
    public static final String YYYYMMDDTHHMMSSSSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    /**
     *
     */
    public static final String DATE_FORMATE = "yyyy-MM-dd HH:mm:ss";
    /**
     *
     */
    public static final String YMD = "yyyyMMdd";
    /**
     *
     */
    public static final String YMDH = "yyyyMMddHH";
    /**
     *
     */
    public static final String YMDHM = "yyyyMMddHHmm";
    /**
     *
     */
    public static final String YMDHMS = "yyyyMMddHHmmss";
    /**
     *
     */
    public static final String YM = "yyyy年MM月";
    /**
     *
     */
    public static final String YEAR = "yyyy";
    /**
     *
     */
    public static final String MONTH = "MM";
    /**
     *
     */
    public static final String DATE = "dd";
    /**
     *
     */
    public static final String HOUR = "HH";
    /**
     *
     */
    public static final String MINUTE = "mm";
    /**
     *
     */
    public static final String YMDZ = "yyyy年MM月dd日";

    /**
     * 将字符串转为Date类型
     *
     * @param dateValue  字符串日期
     * @param dateFormat 格式
     * @return Date
     * @throws ParseException ParseException
     */
    public static Date parseDate(String dateValue, String dateFormat) throws ParseException {
        SimpleDateFormat dateParser = DateFormatHolder.formatFor(dateFormat);
        return dateParser.parse(dateValue);
    }

    /**
     * 格式化指定日期的字符串
     *
     * @param date 日期
     * @return String
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return formatDate(date, FORMAT);
    }

    /**
     * 格式化自定日期的字符串
     *
     * @param date    日期
     * @param pattern 格式
     * @return String
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        }

        SimpleDateFormat formatter = DateFormatHolder.formatFor(pattern);
        return formatter.format(date);
    }

    /**
     * @return java.util.Date
     * @Description: 获取当前日期
     */
    public static Date getCurrentDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    /**
     * @author axue016
     * @ClassName: DateFormatHolder
     * @Description: 日期格式化帮助类
     * @date: Jul 7, 2017
     */
    static final class DateFormatHolder {
        /**
         *
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> THREADLOCAL_FORMATS = new ThreadLocal() {
            protected SoftReference<Map<String, SimpleDateFormat>> initialValue() {
                return new SoftReference(new HashMap());
            }
        };

        /**
         * 获取SimpleDateFormat对象
         *
         * @param pattern 格式
         * @return SimpleDateFormat
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        public static SimpleDateFormat formatFor(String pattern) {
            SoftReference ref = (SoftReference) THREADLOCAL_FORMATS.get();
            Map formats = (Map) ref.get();
            if (formats == null) {
                formats = new HashMap();
                THREADLOCAL_FORMATS.set(new SoftReference(formats));
            }

            SimpleDateFormat format = (SimpleDateFormat) formats.get(pattern);
            if (format == null) {
                format = new SimpleDateFormat(pattern);
                formats.put(pattern, format);
            }
            return format;
        }
    }

    /**
     * 获取某个日期的开始时间
     *
     * @param d d
     * @return Timestamp
     */
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
                0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取本周的开始时间
     *
     * @return Date
     */
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        Timestamp dayStartTime = getDayStartTime(cal.getTime());
        return dayStartTime;
    }

    /**
     * 获取本周的结束时间
     *
     * @return Date
     */
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    /**
     * 获取某个日期的结束时间
     *
     * @param d d
     * @return Timestamp
     */
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
                59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取某个日期的结束时间精确到秒
     *
     * @param d d
     * @return Timestamp
     */
    public static Date getDayEndTimeSecond(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
                59, 59);
        return calendar.getTime();
    }


    /**
     * 获取当天日期的开始时间
     *
     * @return Timestamp
     */
    public static Timestamp getStartDateOfDay() {
        long current = System.currentTimeMillis();
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();

        return new Timestamp(zero);
    }

    /**
     * 获取当天日期的开始时间
     *
     * @return Timestamp
     */
    public static Timestamp getEndDateOfDay() {
        long current = System.currentTimeMillis();
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;

        return new Timestamp(twelve);
    }

    /**
     * 获取本月的开始时间
     *
     * @return Date
     */
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    /**
     * 获取三个月内的开始时间
     *
     * @return Date
     */
    public static Date getBeginDayOf3MonthAgao() {
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(dBefore);// 把当前时间赋给日历
        calendar.add(calendar.MONTH, -3);// 设置为前3月
        return getDayStartTime(calendar.getTime());
    }


    /**
     * 获取本月的结束时间
     *
     * @return Date
     */
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }


    /**
     * @return 获取本月是哪一月
     */
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    /**
     * @return 获取当前是本月的哪一天
     */

    public static int getNowDayOfMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(5);
    }

    /**
     * 获取今年是哪一年
     *
     * @return Integer
     */

    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    /**
     * @param date 日期
     * @return 获取哪一月
     */
    public static int getMonth(Date date) {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    /**
     * @param date 日期
     * @return 获取哪一天
     */
    public static int getDayOfMonth(Date date) {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(5);
    }

    /**
     * 获取哪一年
     *
     * @param date 日期
     * @return Integer
     */

    public static int getYear(Date date) {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    /**
     * @param date 日期
     * @return 一年多少天
     */

    public static BigDecimal getDaysOfOneYear(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        BigDecimal days = null;
        int year = cal.get(Calendar.YEAR);

        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
            days = new BigDecimal(366);
        } else {
            days = new BigDecimal(365);
        }

        return days;
    }

    /**
     * 日期转换
     *
     * @param date date
     * @return XMLGregorianCalendar
     */

    public static XMLGregorianCalendar date2Gregorian(Date date) {
        if (date == null) {
            return null;
        }
        DatatypeFactory dataTypeFactory;
        try {
            dataTypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(date.getTime());
        return dataTypeFactory.newXMLGregorianCalendar(gc);
    }

    /**
     * 判断两个日期是否相等
     *
     * @param a 日期a
     * @param b 日期b
     * @return 是否相等
     */
    public static int getCeilMonthsBetween(Date a, Date b) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(a);
        calendar2.setTime(b);
        int year = calendar1.get(1) - calendar2.get(1);
        int month = calendar1.get(2) - calendar2.get(2);
        month += year * 12;
        if (calendar1.get(5) > calendar2.get(5)) {
            ++month;
        } else if (calendar1.get(5) == calendar2.get(5)) {
            if (calendar1.get(Calendar.HOUR) == calendar2.get(Calendar.HOUR)
                    && calendar1.get(Calendar.MINUTE) == calendar2.get(Calendar.MINUTE)
                    && calendar1.get(Calendar.SECOND) == calendar2.get(Calendar.SECOND)) {
                return month;
            } else { // 7月8 0.0.0  8月8 0.0.1  月份 +1
                Calendar tm1 = Calendar.getInstance();
                tm1.set(Calendar.HOUR, calendar1.get(Calendar.HOUR));
                tm1.set(Calendar.MINUTE, calendar1.get(Calendar.MINUTE));
                tm1.set(Calendar.SECOND, calendar1.get(Calendar.SECOND));
                Calendar tm2 = Calendar.getInstance();
                tm2.set(Calendar.HOUR, calendar2.get(Calendar.HOUR));
                tm2.set(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));
                tm2.set(Calendar.SECOND, calendar2.get(Calendar.SECOND));
                int lo = getDaysBetween(tm1.getTime(), tm2.getTime());
                if (lo > 0) {
                    ++month;
                }
            }
        }

        return month;
    }

    /**
     * 取得日期a和b间隔的天数。
     *
     * @param a the a
     * @param b the b
     * @return days between
     */
    public static int getDaysBetween(Date a, Date b) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(a);
        calendar2.setTime(b);
        long mSeconds = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
        int days = (int) (mSeconds / 86400000);
        if (mSeconds % 86400000 > 0) {
            days++;
        }
        return days;
    }

    /**
     * 由String转换为Date
     *
     * @param a      String类型的日期
     * @param format 为格式“yyyy-MM-dd HH:mm:ss”由自己定义,若精确到日，则剩余的时分秒为00
     * @return 转换成固定格式的Date类型的日期
     * @throws ParseException 解析异常
     */
    public static Date stringTimeToDate(String a, String format) throws ParseException {
        boolean realFlag = false;
        Date date = new Date();
        if (a != null && !"".equals(a)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(a);
            realFlag = true;
        }
        return realFlag ? date : null;
    }

    /**
     * bigDecimal转String保留两位小数
     *
     * @param bigDecimal bigDecimal
     * @return String
     * @Description: bigDecimal转String保留两位小数
     */
    public static String bigDecimalToString2(BigDecimal bigDecimal) {
        if (null != bigDecimal) {
            return String.format("%.2f", bigDecimal);
        } else {
            return "";
        }
    }

    /**
     * bigDecimal转String保留六位小数
     *
     * @param bigDecimal bigDecimal
     * @return String
     * @Description: bigDecimal转String保留六位小数
     */
    public static String bigDecimalToString6(BigDecimal bigDecimal) {
        if (null != bigDecimal) {
            return String.format("%.6f", bigDecimal);
        } else {
            return "";
        }

    }

    /**
     * 科学计数法，保留两位小数
     *
     * @param bigDecimal bigDecimal
     * @return String
     * @Description: 科学计数法，保留两位小数
     */
    public static String bigDecimalToString(BigDecimal bigDecimal) {
        if (null != bigDecimal) {
            return new DecimalFormat("#,##0.00").format(bigDecimal);
        } else {
            return "";
        }
    }

    /**
     * 获取两个日期天数之差(跨一年情况)
     *
     * @param beforeDate 起始时间
     * @param afterDate  结束时间
     * @return int  天数
     * @Description: 获取两个日期天数之差(跨一年情况)
     */
    public static int getDaysValueOverYear(Date beforeDate, Date afterDate) {
        Calendar aCalendar = Calendar.getInstance();
        Calendar bCalendar = Calendar.getInstance();
        aCalendar.setTime(beforeDate);
        bCalendar.setTime(afterDate);
        int days = 0;
        while (aCalendar.before(bCalendar)) {
            days++;
            aCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return days;
    }

    /**
     * 获取两个日期天数之差(同一年情况)
     *
     * @param beforeDate 起始时间
     * @param afterDate  结束时间
     * @return int  天数
     * @Description: 获取两个日期天数之差(同一年情况)
     */
    public static int getDaysValueSameYear(Date beforeDate, Date afterDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(beforeDate);
        int dayFrom = cal.get(Calendar.DAY_OF_YEAR);
        cal.setTime(afterDate);
        int dayTo = cal.get(Calendar.DAY_OF_YEAR);
        return dayTo - dayFrom;
    }

    /**
     * 获取 days 天数之后的日期
     *
     * @param dateFrom 起始时间
     * @param days     天数
     * @return Date  新日期
     * @Description: 获取 days 天数之后的日期
     */
    public static Date getDateAfterDays(Date dateFrom, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFrom);
        // 调用Calendar类中的add()，增加时间量
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 获取 month 月数之后的日期
     *
     * @param dateFrom 起始时间
     * @param month    月数
     * @return Date  新日期
     * @Description: 获取 days 天数之后的日期
     */
    public static Date getMonthAfterMonth(Date dateFrom, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFrom);
        // 调用Calendar类中的add()，增加时间量
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    /**
     * 获取 year 年数之后的日期
     *
     * @param dateFrom 起始时间
     * @param year     年数
     * @return Date  新日期
     * @Description: 获取 year 年数之后的日期
     */
    public static Date getDateAfterYear(Date dateFrom, int year) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFrom);
        // 调用Calendar类中的add()，增加时间量
        cal.add(Calendar.YEAR, year);
        return cal.getTime();
    }

    /**
     * 功能：传入时间按所需格式返回时间字符串
     *
     * @param date   java.util.Date格式
     * @param format yyyy-MM-dd HH:mm:ss | yyyy年MM月dd日 HH时mm分ss秒
     * @return 返回
     */
    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date == null) {
                date = new Date();// 如果时间为空,则默认为当前时间
            }
            if (StringUtils.isBlank(format)) {
                format = "yyyy-MM-dd";
            }
            DateFormat df = new SimpleDateFormat(format);
            result = df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取第二天零点零时零分
     *
     * @return 返回
     */
    public static Date getNextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date nextDay = calendar.getTime();
        return nextDay;
    }

    /**
     * 获取某个 日期的前一天
     *
     * @param date   date
     * @param format format
     * @return String
     */
    public static String beforeDate(Date date, String format) {
        if (null != date) {
            SimpleDateFormat dft = new SimpleDateFormat(format);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
            return dft.format(calendar.getTime());
        } else {
            return "";
        }
    }

    /**
     * 获取某个 日期的后一天
     *
     * @param date   date
     * @param format format
     * @return String
     */
    public static String afterDate(Date date, String format) {
        if (null != date) {
            SimpleDateFormat dft = new SimpleDateFormat(format);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
            return dft.format(calendar.getTime());
        } else {
            return "";
        }
    }


    /**
     * 取当前时间之前的天数或之后天数的日期
     *
     * @param day 之前或之后天数
     * @return anbDay
     */
    public static Date getDayAfterAndBefore(int day) {

        Date nowTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowTime);
        calendar.add(Calendar.DAY_OF_MONTH, day);// day：N天
        Date anbDay = calendar.getTime();

        return anbDay;

    }

    /**
     * 获取任意时间之前的天数或之后天数的日期
     *
     * @param date 任意日期
     * @param day  之前或之后天数
     * @return anbDay
     */
    public static Date getDayAfterAndBeforeAny(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);// day：N天
        Date anbDay = calendar.getTime();
        return anbDay;
    }

    /**
     * 获取当前时间下一个整点后的hours个小时的时间
     *
     * @param hours 小时数
     * @return 返回
     */
    public static Date getNextHour(int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 1 + hours);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date resultDate = calendar.getTime();
        return resultDate;
    }


    /**
     * 获取当前月份的X个月后的月的最大天数, 欲获取下个月的天数,则传0
     *
     * @param monthCount X个月
     * @return maxDay
     */
    public static int getNextMonthMaxDay(int monthCount) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.MONTH, getNowMonth() + monthCount);// 设置月份
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDay = a.get(Calendar.DATE);
        return maxDay;
    }

    /**
     * 获取某个日期的开始时间
     *
     * @param d d
     * @return Timestamp
     */
    public static Date getDayStartDate(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
                0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 判断起止时间是否包含2月29日，返回整年时间
     *
     * @param startDate 起始日期
     * @param endDate   终止日期
     * @return 返回整年时间
     */
    public static int getOneYear(Date startDate, Date endDate) {
        try {
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(startDate);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(startDate);
            endCal.add(Calendar.MONTH, 12);
            endDate = endCal.getTime();

            int startYear = startCal.get(Calendar.YEAR);
            int endYear = endCal.get(Calendar.YEAR);
            Date day;
            if (startYear % 4 == 0 && startYear % 100 != 0 || startYear % 400 == 0) {
                day = DateUtils.parseDate(startYear + "0229000000", "yyyyMMddHHmmss");
                if (startDate.getTime() < day.getTime()) {
                    return 366;
                }
            } else {
                if (endYear % 4 == 0 && endYear % 100 != 0 || endYear % 400 == 0) {
                    day = DateUtils.parseDate(endYear + "0229000000", "yyyyMMddHHmmss");
                    if (endDate.getTime() > day.getTime()) {
                        return 366;
                    }
                }
            }
        } catch (Exception e) {
            // 时间的格式不对
            logger.error("时间的格式不对", e);
        }
        return 365;
    }
}
