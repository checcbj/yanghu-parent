package com.yanghu.manage.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * 日期操作工具类
 */
public class DateUtils {


    public static String getStringTimeByAsia(String type){
        if (StringUtils.isEmpty(type)){
            type="yyyy-MM-dd HH:mm:ss";
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String format = sdf.format(date);
        return format;
    }


    /**
     * 比较两个字符串日期的大小
     * @param beginTime 起始日期
     * @param nowstart 终止日期
     * @return 大的日期
     * @throws Exception
     */
    public static String maxByToStringTime(String beginTime, String nowstart) throws Exception {
        Date begin = parseString2Date(beginTime, "yyyy-MM-dd");
        Date start = parseString2Date(nowstart, "yyyy-MM-dd");
        if (begin.before(start)) {
            return nowstart;
        }
        return beginTime;
    }

    public static String minByToStringTime(String beginTime, String nowstart) throws Exception {

        Date begin = parseString2Date(beginTime, "yyyy-MM-dd");
        Date start = parseString2Date(nowstart, "yyyy-MM-dd");
        if (begin.before(start)) {
            return beginTime;
        }
        return nowstart;
    }

    /**
     * 获得一个日期的开始时间和结束时间   根据时间类型  日   月    年
     */
    public static HashMap<String, String> getStartAndEndTime(String time, String timeType) {
        HashMap<String, String> map = new HashMap<>(16);
        String start = "";
        String end = "";

        if ("day".equals(timeType)) {
            start = time + " 00:00:00";
            end = time + " 23:59:59";
        }
        if ("month".equals(timeType)) {
            start = time + "-01 00:00:00";
            String[] split = time.split("-");
            String lastDayOfMonth1 = getLastDayOfMonth1(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
            end = lastDayOfMonth1 + " 23:59:59";
        }
        if ("year".equals(timeType)) {
            start = time + "-01-01 00:00:00";
            end = time + "-12-31 23:59:59";
        }
        map.put("start", start);
        map.put("end", end);
        return map;
    }

    /**
     * 获取秒数
     * @param localDateTime localDateTime格式日期对象
     * @return 秒数
     */
    public static Long getSecond(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.of("+8"));
    }

    /**
     * 获取毫秒数
     * @param localDateTime localDateTime格式日期对象
     * @return 毫秒数
     */
    public static Long getMilliSecond(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 日期转换-  String -> Date
     *
     * @param dateString 字符串时间
     * @return Date类型信息
     * @throws Exception 抛出异常
     */
    public static Date parseString2Date(String dateString) throws Exception {
        if (dateString == null) {
            return null;
        }
        return parseString2Date(dateString, "yyyy-MM-dd");
    }

    /**
     * 日期转换-  String -> Date
     *
     * @param dateString 字符串时间
     * @param pattern    格式模板
     * @return Date类型信息
     * @throws Exception 抛出异常
     */
    public static Date parseString2Date(String dateString, String pattern) throws Exception {
        if (dateString == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateString);
    }

    /**
     * 日期转换   String -> String
     *
     * @param dateString 字符串时间对象
     * @param oleType    旧时间格式  不传默认为 yyyy-MM-dd
     * @param newType    新时间格式  不传默认为 yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static String parseString2String(String dateString, String oleType, String newType) throws Exception {
        if (dateString == null) {
            return null;
        }
        if (StringUtils.isEmpty(oleType)) {
            oleType = "yyyy-MM-dd";
        }
        if (StringUtils.isEmpty(newType)) {
            newType = "yyyy-MM-dd";
        }
        Date date = parseString2Date(dateString, oleType);
        return  parseDate2String(date, newType);
    }

    /**
     * 日期转换 Date -> String
     *
     * @param date Date类型信息
     * @return 字符串时间
     * @throws Exception 抛出异常
     */
    public static String parseDate2String(Date date) throws Exception {
        if (date == null) {
            return null;
        }
        return parseDate2String(date, "yyyy-MM-dd");
    }

    /**
     * 日期转换 Date -> String
     *
     * @param date    Date类型信息
     * @param pattern 格式模板
     * @return 字符串时间
     * @throws Exception 抛出异常
     */
    public static String parseDate2String(Date date, String pattern) throws Exception {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return sdf.format(date);
    }

    /**
     * 排序一个字符串数组  根据指定时间格式
     */
    public static void sortStringTimeList(List<String> times, String fomat) {
        Collections.sort(times, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                long time1 = 0;
                long time2 = 0;
                try {
                    time1 = DateUtils.parseString2Date(o1, fomat).getTime();
                    time2 = DateUtils.parseString2Date(o2, fomat).getTime();
                } catch (Exception e) {
                    e.getMessage();
                }
                int i = 0;

                if ((time1 - time2) > 0) {
                    return i = 1;
                }
                if ((time1 - time2) == 0) {
                    return i = 0;
                }
                if ((time1 - time2) < 0) {
                    return i = -1;
                }
                return i;
            }
        });
    }


    /**
     * 计算两个LocalDateTime的时间计算对象
     */
    public static Duration sub2LocalDateTime(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end);
    }

    /**
     * 将java8 的 java.time.LocalDateTime 转换为 String，默认时区为东8区
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, String pattern) throws Exception {
        Date date = localDateTimeConvertToDate(localDateTime);
        return parseDate2String(date, pattern);
    }

    /**
     * 将java8 的 java.time.LocalDateTime 转换为 String，默认时区为东8区
     */
    public static String localDateTimeToString(LocalDateTime localDateTime) throws Exception {
        Date date = localDateTimeConvertToDate(localDateTime);
        return parseDate2String(date);
    }

    /**
     * 计算两个字符串时间经历了几天
     */
    public static long stringTimeToDayNum(String start, String end) throws Exception {
        Date startDate = parseString2Date(start);
        Date endDate = parseString2Date(end);
        long startDateTime = startDate.getTime();
        long endDateTime = endDate.getTime();
        long overtime = endDateTime - startDateTime;
        if (overtime == 0) {
            return 1;
        } else {
            long dayNum = overtime / 1000 / 60 / 60 / 24;
            return dayNum + 1;
        }
    }

    /**
     * 将java.util.Date 转换为java8 的java.time.LocalDateTime,默认时区为东8区
     */
    public static LocalDateTime dateConvertToLocalDateTime(Date date) {
        return date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
    }


    /**
     * 将java8 的 java.time.LocalDateTime 转换为 java.util.Date，默认时区为东8区
     */
    public static Date localDateTimeConvertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
    }

    /**
     * 获取指定年月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth1(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * 日期转换 LocalDateTime -> String
     *
     * @param localDateTime LocalDateTime类型信息
     * @param pattern       格式模板
     * @return 字符串时间
     * @throws Exception 抛出异常
     */
    public static String parseLocalDateTime2String(LocalDateTime localDateTime, String pattern) throws Exception {
        if (localDateTime == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(localDateTime);
    }

    /**
     * 获取当前日期的本周一是几号
     *
     * @return 本周一的日期
     */
    public static Date getThisWeekMonday() {
        Calendar cal = Calendar.getInstance();
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获取当前日期周的最后一天
     *
     * @return 当前日期周的最后一天
     */
    public static Date getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        c.add(Calendar.DATE, -dayOfWeek + 7);
        return c.getTime();
    }

    /**
     * 根据日期区间获得日期列表
     *
     * @param minDate 开始时间
     * @param maxDate 结束时间
     * @return 日期列表
     * @throws Exception
     */
    public static List<String> getDayBetween(String minDate, String maxDate, String format) throws Exception {
        ArrayList<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), min.get(Calendar.DAY_OF_MONTH));
        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), max.get(Calendar.DAY_OF_MONTH));

        SimpleDateFormat sdf2 = new SimpleDateFormat(format);
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf2.format(curr.getTime()));
            curr.add(Calendar.DAY_OF_MONTH, 1);
        }

        return result;
    }

    /**
     * 根据日期区间获取月份列表
     *
     * @param minDate 开始时间
     * @param maxDate 结束时间
     * @return 月份列表
     * @throws Exception
     */
    public static List<String> getMonthBetween(String minDate, String maxDate, String format) throws Exception {
        ArrayList<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        SimpleDateFormat sdf2 = new SimpleDateFormat(format);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf2.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 根据日期区间获取年份列表
     *
     * @param minDate 开始时间
     * @param maxDate 结束时间
     * @return 年份列表
     * @throws Exception
     */
    public static List<String> getYearBetween(String minDate, String maxDate, String format) throws Exception {
        ArrayList<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), 0, 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), 1, 2);
        SimpleDateFormat sdf2 = new SimpleDateFormat(format);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf2.format(curr.getTime()));
            curr.add(Calendar.YEAR, 1);
        }

        return result;
    }


    /**
     * 根据日期获取年度中的周索引
     *
     * @param date 日期
     * @return 周索引
     * @throws Exception
     */
    public static Integer getWeekOfYear(String date) throws Exception {
        Date useDate = parseString2Date(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(useDate);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 根据年份获取年中周列表
     *
     * @param year 年分
     * @return 周列表
     * @throws Exception
     */
    public static Map<Integer, String> getWeeksOfYear(String year) throws Exception {
        Date useDate = parseString2Date(year, "yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(useDate);
        //获取年中周数量
        int weeksCount = cal.getWeeksInWeekYear();
        Map<Integer, String> mapWeeks = new HashMap<>(55);
        for (int i = 0; i < weeksCount; i++) {
            cal.get(Calendar.DAY_OF_YEAR);
            mapWeeks.put(i + 1, parseDate2String(getFirstDayOfWeek(cal.get(Calendar.YEAR), i)));
        }
        return mapWeeks;
    }

    /**
     * 获取某年的第几周的开始日期
     *
     * @param year 年分
     * @param week 周索引
     * @return 开始日期
     * @throws Exception
     */
    public static Date getFirstDayOfWeek(int year, int week) throws Exception {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * 获取某年的第几周的结束日期
     *
     * @param year 年份
     * @param week 周索引
     * @return 结束日期
     * @throws Exception
     */
    public static Date getLastDayOfWeek(int year, int week) throws Exception {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 获取当前时间所在周的开始日期
     *
     * @param date 当前时间
     * @return 开始时间
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }

    /**
     * 获取当前时间所在周的结束日期
     *
     * @param date 当前时间
     * @return 结束日期
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }

    /**
     * 获得上周一的日期
     * @param date 指定日期时间
     * @return 上周一的日期
     */
    public static Date geLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    /**
     * 获得本周一的日期
     * @param date 指定日期时间
     * @return 本周一的日期
     */
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获得下周一的日期
     * @param date 指定日期时间
     * @return 下周一的日期
     */
    public static Date getNextWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    /**
     * 获得今天日期
     * @return 今天日期
     */
    public static Date getToday() {
        return new Date();
    }

    /**
     * 获得本月一日的日期
     * @return 本月一日的日期
     */
    public static Date getFirstDay4ThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获得本月最后一天的日期
     * @return 本月最后一天的日期
     */
    public static Date getLastDay4ThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        try {
            System.out.println("今日日期" + parseDate2String(new Date(),"yyyy-MM-dd HH:mm:ss"));
            System.out.println("本周一" + parseDate2String(getThisWeekMonday()));
            System.out.println("本周日：" + parseDate2String(getSundayOfThisWeek()));
            System.out.println("本月一日" + parseDate2String(getFirstDay4ThisMonth()));
            System.out.println("本月最后一天" + parseDate2String(getLastDay4ThisMonth()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
