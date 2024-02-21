package com.ledao.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ledao.common.utils.http.HttpUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类
 *
 * @author lxz
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM", "yyyy-MM-dd'T'HH:mm:ssX"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseToDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /***
     *
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /***
     * convert Date to cron ,eg.  "0 06 10 15 1 ? 2014"
     * @param date  : 时间点
     * @return
     */
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return parseDateToStr(dateFormat, date);
    }

    /**
     * 比较两个时间 date1，date12均为时间类型
     * date1为当前时间，date2为传过来的时间
     * 判断两个时间是否相差m个月
     */
    public static Boolean timeDifference(Date date1, Date date2, int m) {
        Boolean flag = false;
        int days = 0;
        if (date1.before(date2)) {
            days = differentDays(date1, date2);
        } else if (date2.before(date1)) {
            days = differentDays(date2, date1);
        }
        if (days <= m) {
            flag = true;
        }
        return flag;
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            //同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    //闰年
                    timeDistance += 366;
                } else {
                    //不是闰年
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else {
            //不同年
            return day2 - day1;
        }
    }

    /**
     * 时间添加或减少
     */
    public static Date addTime(Date dateTime, int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.add(Calendar.DAY_OF_MONTH, index);
        return calendar.getTime();

    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || "null".equals(seconds)) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    //13位毫秒时间戳  -->  yyyy-MM-dd HH:mm:ss
    public static String timeToFormat(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return sdf.format(time);
    }

    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }


    public static Date covnDate(String dateTime) {
        DateFormat df2 = null;
        Date date1 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(dateTime);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static Date strToDate(String date) throws ParseException {
        if (date == null) {
            return null;
        }
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = dft.parse(date);
        return d;
    }

    /**
     * 获取系统当前时间
     */
    public static String strTime() {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        return hour + ":" + minute + ":" + second;
    }

    /**
     * @param startDate 当前日期
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @throws ParseException
     */
    public static boolean isInTime(String startDate, String startTime, String endTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (sdf.format(new Date()).equals(startDate)) {
            sdf = new SimpleDateFormat("HH:mm");
            if ("00:00".equals(startTime)) {
                startTime = "24:00";
            }
            if ("00:00".equals(endTime)) {
                endTime = "24:00";
            }
            long current = sdf.parse(sdf.format(new Date())).getTime();  //当前时间
            long start = sdf.parse(startTime).getTime();                 //开始时间
            long end = sdf.parse(endTime).getTime();                     //结束时间
            if (current >= start && current < end) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 计算两个时间之间差多少年
     *
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static Long dayCompare(Date date1, Date date2) throws ParseException {
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(date1);
        aft.setTime(date2);
        int surplus = aft.get(Calendar.DATE) - bef.get(Calendar.DATE);
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int year = aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR);
        if (result < 0) {
            result = 1;
        } else if (result == 0) {
            result = surplus <= 0 ? 0 : 1;
        } else {
            result = 0;
        }
        return Long.valueOf(year + result);
    }

    //计算两个日期相差年数
    public static int yearDateDiff(String startDate, String endDate) {
        Calendar calBegin = Calendar.getInstance(); //获取日历实例
        Calendar calEnd = Calendar.getInstance();
        calBegin.setTime(stringTodate(startDate, "yyyy")); //字符串按照指定格式转化为日期
        calEnd.setTime(stringTodate(endDate, "yyyy"));
        return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
    }

    //字符串按照指定格式转化为日期
    public static Date stringTodate(String dateStr, String formatStr) {
        // 如果时间为空则默认当前时间
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        if (dateStr != null && !"".equals(dateStr)) {
            String time = "";
            try {
                Date dateTwo = format.parse(dateStr);
                time = format.format(dateTwo);
                date = format.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            String timeTwo = format.format(new Date());
            try {
                date = format.parse(timeTwo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 获取时间为上午还是下午
     *
     * @param date
     * @param chinese
     * @return
     */
    public static String getTimeToAmOrPm(Date date, boolean chinese) {
        String time = "";
        if (StringUtils.isNotNull(chinese)) {
            SimpleDateFormat aa = new SimpleDateFormat("yyyy-MM-dd aa", Locale.CHINESE);
            time = aa.format(date);
        } else {
            SimpleDateFormat aa = new SimpleDateFormat("yyyy-MM-dd aa", Locale.ENGLISH);
            time = aa.format(date);
        }
        return time;
    }

    /**
     * 描述:获取下一个月.
     *
     * @return
     */
    public static String getPreMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        String preMonth = dft.format(cal.getTime());
        return preMonth;
    }

    /**
     * 获取当前月后的第几个有的月份
     *
     * @param com
     * @return
     */
    public static String getPreMonthByCount(int com) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, com);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        String preMonth = dft.format(cal.getTime());
        return preMonth;
    }

    /**
     * 计算时间往后多少分钟
     *
     * @param minute
     * @param date
     * @return
     */
    public static Date getMoreMinute(int minute, Date date) {
        long curren = date.getTime();
        curren += minute * 60 * 1000;
        Date da = new Date(curren);
        return da;
    }

    /**
     * 判断date2是否在date1后面
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isTimeEaily(Date date1, Date date2) {
        return date2.getTime() - date1.getTime() > 0;
    }

    //获取周末和节假日
    public static Set<String> JJR(String year, String month) {
        Set<String> monthWekDay = new HashSet<>();
        Map jjr = new HashMap<>();
        if (StringUtils.isNotEmpty(month)) {
            monthWekDay = getWeekendInMonth(year, month);
            jjr = getJjr(year, month);
        } else {
            monthWekDay = getWeekdaysInYear(year);
            jjr = getJjr(year, "");
        }
        Integer code = (Integer) jjr.get("code");
        if (code != 0) {
            return monthWekDay;
        }
        Map<String, Map<String, Object>> holiday = (Map<String, Map<String, Object>>) jjr.get("holiday");
        Set<String> strings = holiday.keySet();
        for (String str : strings) {
            Map<String, Object> stringObjectMap = holiday.get(str);
            Integer wage = (Integer) stringObjectMap.get("wage");
            String date = (String) stringObjectMap.get("date");
            //筛选掉补班
            if (wage.equals(1)) {
                monthWekDay.remove(date);
            } else {
                monthWekDay.add(date);
            }
        }
        return monthWekDay;
    }

    //获取节假日不含周末
    private static Map getJjr(String year, String month) {
        String url = "";
        if (StringUtils.isNotEmpty(month)) {
            url = "https://timor.tech/api/holiday/year/" + year + "-" + month;
        } else {
            url = "https://timor.tech/api/holiday/year/" + year;
        }
        //解密数据
        String rspStr = HttpUtils.sendGet(url, "");
        return JSONObject.parseObject(rspStr, Map.class);
    }


    public static Set<String> JJRForThAndFri(String year, String month) {
        Set<String> monthWekDay = new HashSet<>();
        Map jjr = new HashMap<>();
        if (StringUtils.isNotEmpty(month)) {
            monthWekDay = getWeekendInMonthForThAndFri(year, month);
            jjr = getJjr(year, month);
        } else {
            monthWekDay = getWeekdaysInYearForThAndFri(year);
            jjr = getJjr(year, "");
        }
        Integer code = (Integer) jjr.get("code");
        if (code != 0) {
            return monthWekDay;
        }
        Map<String, Map<String, Object>> holiday = (Map<String, Map<String, Object>>) jjr.get("holiday");
        Set<String> strings = holiday.keySet();
        for (String str : strings) {
            Map<String, Object> stringObjectMap = holiday.get(str);
            Integer wage = (Integer) stringObjectMap.get("wage");
            String date = (String) stringObjectMap.get("date");
            //筛选掉补班
            if (wage.equals(1)) {
                monthWekDay.remove(date);
            } else {
                monthWekDay.add(date);
            }
        }
        return monthWekDay;
    }

    /***
     * 获取全年的非周四周五日期
     * @param year
     * @return
     */
    public static Set<String> getWeekdaysInYearForThAndFri(String year) {
        Set<String> dateList = new HashSet<String>();
        SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar(Integer.valueOf(year), 0, 1);
        int i = 1;
        while (calendar.get(Calendar.YEAR) < Integer.valueOf(year) + 1) {
            calendar.set(Calendar.WEEK_OF_YEAR, i++);
            //周末
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            if (calendar.get(Calendar.YEAR) == Integer.valueOf(year)) {
                dateList.add(simdf.format(calendar.getTime()));
            }
            //周一
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            if (calendar.get(Calendar.YEAR) == Integer.valueOf(year)) {
                dateList.add(simdf.format(calendar.getTime()));
            }
            //周二
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            if (calendar.get(Calendar.YEAR) == Integer.valueOf(year)) {
                dateList.add(simdf.format(calendar.getTime()));
            }
            //周三
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            if (calendar.get(Calendar.YEAR) == Integer.valueOf(year)) {
                dateList.add(simdf.format(calendar.getTime()));
            }
            //周六
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            if (calendar.get(Calendar.YEAR) == Integer.valueOf(year)) {
                dateList.add(simdf.format(calendar.getTime()));
            }
        }
        return dateList;
    }

    /**
     * 获取当月的所有周末
     *
     * @param year
     * @param month
     * @return
     */

    public static Set<String> getWeekendInMonthForThAndFri(String year, String month) {
        Set<String> dateList = new HashSet<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.valueOf(year));
        calendar.set(Calendar.MONTH, Integer.valueOf(month) - 1);
        // 设置为当月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // 当月最大天数
        int daySize = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < daySize - 1; i++) {
            String days = "";
            //在第一天的基础上加1
            calendar.add(Calendar.DATE, 1);
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            // 1代表周日，7代表周六 判断这是一个星期的第几天从而判断是否是周末
            if (week == Calendar.SATURDAY || week == Calendar.SUNDAY || week == Calendar.MONDAY || week == Calendar.TUESDAY || week == Calendar.WEDNESDAY) {
                int ct = calendar.get(Calendar.DAY_OF_MONTH);
                if (ct < 10) {
                    days = "0" + ct;
                } else {
                    days = String.valueOf(ct);
                }
                // 得到当天是一个月的第几天
                dateList.add(year + "-" + month + "-" + days);
            }
        }
        return dateList;
    }

    /**
     * 获取当月的所有周末
     *
     * @param year
     * @param month
     * @return
     */

    public static Set<String> getWeekendInMonth(String year, String month) {
        Set<String> dateList = new HashSet<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.valueOf(year));
        calendar.set(Calendar.MONTH, Integer.valueOf(month) - 1);
        // 设置为当月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // 当月最大天数
        int daySize = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < daySize - 1; i++) {
            String days = "";
            //在第一天的基础上加1
            calendar.add(Calendar.DATE, 1);
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            // 1代表周日，7代表周六 判断这是一个星期的第几天从而判断是否是周末
            if (week == Calendar.SATURDAY || week == Calendar.SUNDAY) {
                int ct = calendar.get(Calendar.DAY_OF_MONTH);
                if (ct < 10) {
                    days = "0" + ct;
                } else {
                    days = String.valueOf(ct);
                }
                // 得到当天是一个月的第几天
                dateList.add(year + "-" + month + "-" + days);
            }
        }
        return dateList;
    }

    /**
     * 计算两个时间之间的每一天
     *
     * @param begintTime
     * @param endTime
     * @return
     */
    public static List<String> findDaysStr(String begintTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = null;
        Date dEnd = null;
        try {
            dBegin = sdf.parse(begintTime);
            dEnd = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> daysStrList = new ArrayList<String>();
        daysStrList.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (dEnd.after(calBegin.getTime())) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            String dayStr = sdf.format(calBegin.getTime());
            daysStrList.add(dayStr);
        }
        return daysStrList;
    }

    /**
     * 获取全年周末
     *
     * @param year
     * @return
     */
    public static Set<String> getWeekdaysInYear(String year) {
        Set<String> dateList = new HashSet<String>();
        SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar(Integer.valueOf(year), 0, 1);
        int i = 1;
        while (calendar.get(Calendar.YEAR) < Integer.valueOf(year) + 1) {
            calendar.set(Calendar.WEEK_OF_YEAR, i++);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            if (calendar.get(Calendar.YEAR) == Integer.valueOf(year)) {
                dateList.add(simdf.format(calendar.getTime()));
            }
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            if (calendar.get(Calendar.YEAR) == Integer.valueOf(year)) {
                dateList.add(simdf.format(calendar.getTime()));
            }
        }
        return dateList;
    }

    /**
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author sunran   判断当前时间在时间区间内
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static List<Date> getDatesBetween(String startDate, String endDate) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        System.out.println(parseDate(startDate.trim()) + "======");
        calendar.setTime(parseDate(startDate.trim()));

        while (!calendar.getTime().after(parseDate(endDate.trim()))) {
            Date currentDate = calendar.getTime();
            dates.add(currentDate);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static void main(String[] args) {
        System.out.println(parseDate("2023-11-29"));
    }
}
