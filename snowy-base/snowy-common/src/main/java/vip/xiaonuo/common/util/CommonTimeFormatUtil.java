/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.common.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间格式化工具类
 *
 * @author xuyuxiang
 * @date 2022/6/24 15:28
 **/
public class CommonTimeFormatUtil {

    private static final long ONE_MINUTE_SECONDS = 60;

    private static final int BEFORE_DAWN_HOUR = 6;

    private static final int MORNING_END_HOUR = 12;

    private static final int NOON_END_HOUR = 13;

    private static final int AFTERNOON_END_HOUR = 18;

    private static final int NIGHT_END_HOUR = 24;

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String DATE_SIMPLE_FORMAT = "yyyyMMdd";

    private static final String HOUR_MIN_S = "HH:mm:ss";


    /**
     * 将日期格式化为仿微信的日期
     *
     * @author xuyuxiang
     * @date 2022/6/24 15:28
     **/
    public static String formatWxPastTime(Date date) {
        if (DateUtil.between(date, DateUtil.date(), DateUnit.SECOND, false) < 0) {
            //今天之后的时间显示年月日时分
            return DateUtil.format(date, DatePattern.NORM_DATETIME_MINUTE_PATTERN);
        } else {
            //如果是今年
            if (DateUtil.thisYear() == DateUtil.year(date)) {
                //如果是今天
                if (DateUtil.isSameDay(date, DateUtil.date())) {
                    //相差分钟数
                    long betweenMinute = DateUtil.between(date, DateUtil.date(), DateUnit.MINUTE);
                    //如果在1小时之内
                    if (betweenMinute < ONE_MINUTE_SECONDS) {
                        //一分钟之内，显示刚刚
                        if (betweenMinute < 1) {
                            return "刚刚";
                        } else {
                            //一分钟之外，显示xx分钟前
                            return betweenMinute + "分钟前";
                        }
                    } else {
                        //一小时之外，显示时分
                        return getTodayHour(date) + " " + DateUtil.format(date, "HH:mm");
                    }
                } else if (DateUtil.isSameDay(date, DateUtil.yesterday())) {
                    //如果是昨天，显示昨天时分
                    return "昨天 " + DateUtil.format(date, "HH:mm");
                } else if (isThisWeek(date)) {
                    //如果是本周
                    String weekday;
                    //获取是本周的第几天
                    int dayOfWeek = DateUtil.dayOfWeek(date) - 1;
                    switch (dayOfWeek) {
                        case 1:
                            weekday = "周一";
                            break;
                        case 2:
                            weekday = "周二";
                            break;
                        case 3:
                            weekday = "周三";
                            break;
                        case 4:
                            weekday = "周四";
                            break;
                        case 5:
                            weekday = "周五";
                            break;
                        case 6:
                            weekday = "周六";
                            break;
                        default:
                            weekday = "周日";
                            break;
                    }
                    //显示本周时分
                    return weekday + " " + DateUtil.format(date, "HH:mm");
                } else {
                    //否则显示月日时分
                    return DateUtil.format(date, "MM-dd HH:mm");
                }
            } else {
                //本年之外显示年月日时分
                return DateUtil.format(date, DatePattern.NORM_DATETIME_MINUTE_PATTERN);
            }
        }
    }

    /**
     * 将秒数格式化为天时分秒
     *
     * @author xuyuxiang
     * @date 2022/6/24 15:29
     **/
    public static String formatSeconds(long secondsParam) {
        String result;
        long days = secondsParam / ( ONE_MINUTE_SECONDS * ONE_MINUTE_SECONDS * NIGHT_END_HOUR);
        long hours = (secondsParam % ( ONE_MINUTE_SECONDS * ONE_MINUTE_SECONDS * NIGHT_END_HOUR)) / (ONE_MINUTE_SECONDS * ONE_MINUTE_SECONDS);
        long minutes = (secondsParam % ( ONE_MINUTE_SECONDS * ONE_MINUTE_SECONDS)) /ONE_MINUTE_SECONDS;
        long seconds = secondsParam % ONE_MINUTE_SECONDS;
        if(days > 0) {
            result = days + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒";
        } else if(hours > 0) {
            result = hours + "小时" + minutes + "分钟" + seconds + "秒";
        } else if(minutes > 0) {
            result = minutes + "分钟" + seconds + "秒";
        } else {
            result = seconds + "秒";
        }
        return result;
    }

    /**
     * 判断日期是否是本周
     *
     * @param date 要判断的日期
     * @return boolean
     * @author xuyuxiang
     * @date 2020/8/6 12:10
     **/
    private static boolean isThisWeek(Date date) {
        //获取本周开始时间
        DateTime beginOfWeek = DateUtil.beginOfWeek(DateUtil.date());
        //获取与本周开始时间相差的天数
        long betweenBegin = DateUtil.between(date, beginOfWeek, DateUnit.DAY, false) + 1;
        //如果是同一天，或相差天数小于0，则是本周
        return DateUtil.isSameDay(date, beginOfWeek) || betweenBegin < 0;
    }

    /**
     * 根据今天日期获取早中晚
     *
     * @author xuyuxiang
     * @date 2020/8/6 14:42
     **/
    private static String getTodayHour(Date date) {
        String result = "";
        int hour = DateUtil.hour(date, true);
        if (hour >= 0 && hour <= BEFORE_DAWN_HOUR) {
            result = "凌晨";
        }
        if (hour > BEFORE_DAWN_HOUR && hour < MORNING_END_HOUR) {
            result = "上午";
        }
        if (hour == MORNING_END_HOUR) {
            result = "中午";
        }
        if (hour >= NOON_END_HOUR && hour <= AFTERNOON_END_HOUR) {
            result = "下午";
        }
        if (hour > AFTERNOON_END_HOUR && hour <= NIGHT_END_HOUR) {
            result = "晚上";
        }
        return result;
    }

        /**
         *  日期格式化
         *
         * @author ljy
         * @date  2023/9/7
         * @param format  格式
         * @return String
         * */
        public static String dateToStr(Date date, String format) {
            if (date == null) return null;
            // 如果没有指定字符串转换的格式，则用默认格式进行转换
            if (null == format || format.isEmpty()) {
                format = DATETIME_FORMAT;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }

        /**
         *  年月日，时分秒拼接
         *
         * @author ljy
         * @date  2023/9/7
         *
         * @return date
         * */
        public static Date yearAndHourConnect(String yearDate,String hourDate) {
            if (StringUtils.isBlank(yearDate) || StringUtils.isBlank(hourDate)) return null;
            return strToDate(yearDate+" "+hourDate);
        }

        public static String strToStr(String strDate,String formatter) {
            SimpleDateFormat format = new SimpleDateFormat(formatter);
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = format.parse(strDate, pos);
            return dateToStr(strtodate,formatter);
        }

        public static Date strToDate(String strDate,String formatter) {
            SimpleDateFormat format = new SimpleDateFormat(formatter);
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = format.parse(strDate, pos);
            return strtodate;
        }

        public static Date strToDate(String time) {
            if (StringUtils.isBlank(time)) return null;
            SimpleDateFormat formatter;
            int tempPos = time.indexOf("AD");
            time = time.trim();
            formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
            if (tempPos > -1) {
                time = time.substring(0, tempPos) +
                        "公元" + time.substring(tempPos + "AD".length());//china
                formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
            }
            if (time.contains(".")) time = time.replaceAll("\\.", "/");
            if (time.contains("-")) time = time.replaceAll("-", "/");
            if (!time.contains("/") && !time.contains(" ")) {
                formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            } else if (time.contains("/")) {
                if (time.contains("am") || time.contains("pm")) formatter = new SimpleDateFormat("yyyy/MM/dd KK:mm:ss a");
                else if (time.contains(" ")) formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                else {
                    String[] dateArr = time.split("/");
                    if (dateArr.length == 2 || (dateArr.length == 3 && StringUtils.isBlank(dateArr[2])))
                        formatter = new SimpleDateFormat("yyyy/MM");
                    else formatter = new SimpleDateFormat("yyyy/MM/dd");
                }
            }
            ParsePosition pos = new ParsePosition(0);
            return formatter.parse(time, pos);
        }

    /**
     *计算两个时间的秒差值
     * @author ljy
     * @date  2023/9/7
     *
     * @return date
     * */
        public static Long twoTimeDiffSecond(Date beginDate,Date endDate) {
            if(ObjectUtil.isEmpty(beginDate) || ObjectUtil.isEmpty(endDate)){
                return 0L;
            }
            long diff = beginDate.getTime() - endDate.getTime();
            return diff < 0L ? -diff : diff;
        }

        /**
         * date增加多少分钟
         * @author ljy
         * @date  2023/9/12
         * @type 1 增加，2 减少
         * @param minute  传正数
         * @return date
         * */
        public static Date addOrUpMinute(Date date, int minute,int type) {
            if (date == null) return null;
            if(type == 1){
            }else if(type == 2){
                minute = -minute;
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MINUTE, minute);
            return c.getTime();
        }

        /**
         * 获取一个月的开始和结束时间
         * @author ljy
         * @date  2023/9/12
         * @return date
         * */
        public static List<Date> getMonthFirstAndEndDay(Date date) {
            List<Date> result = new ArrayList<>();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号
            result.add(c.getTime());

            c.add(Calendar.MONTH, 1);//加一个月
            c.add(Calendar.MILLISECOND, -1);//减一毫秒

            result.add(c.getTime());
            return result;
        }
        /**
         * 获取一年的开始和结束时间
         * @author ljy
         * @date  2023/9/12
         * @return date
         * */
        public static List<Date> getYearFirstAndEndDay(Date date) {
            List<Date> result = new ArrayList<>();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            result.add(c.getTime());

            c.add(Calendar.YEAR, 1);//加一年
            c.add(Calendar.MILLISECOND, -1);//减一毫秒
            result.add(c.getTime());

            return result;
        }

        /**
         * 获取两个时间中的每一天
         * @param startTime
         * @param endTime
         * @return
         */
        public static List<String> getPerStrDayList(Date startTime,Date endTime ){
            List<String> lDate = new ArrayList<String>(); //定义一个接受时间的集合
            lDate.add(dateToStr(startTime,DATE_FORMAT));
            Calendar calBegin = Calendar.getInstance();
            calBegin.setTime(startTime);// 使用给定的 Date 设置此 Calendar 的时间
            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(endTime);// 使用给定的 Date 设置此 Calendar 的时间

            while (endTime.after(calBegin.getTime()))  {
                calBegin.add(Calendar.DAY_OF_MONTH, 1);// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                lDate.add(dateToStr(calBegin.getTime(),DATE_FORMAT));
            }
            return  lDate;
        }

        /** 是否为周末*/
        public static boolean isWeekend(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
        }


        public static String dayAndSunday(String time) {
            Date date = strToDate(time,DatePattern.NORM_DATE_PATTERN);
            // 创建Calendar类实例
            Calendar instance = Calendar.getInstance();
            // 根据指定日期获取周几
            instance.setTime(date);
            int week = instance.get(Calendar.DAY_OF_WEEK);
            // 周日的编号是1，周六的编号是7
            if (week == Calendar.SUNDAY ) {
                return "日";
            }
            if(week == Calendar.SATURDAY){
                return "六";
            }
            if(week == Calendar.MONDAY){
                return "1";
            }
            if(week == Calendar.TUESDAY){
                return "2";
            }
            if(week == Calendar.WEDNESDAY){
                return "3";
            }
            if(week == Calendar.THURSDAY){
                return "4";
            }
            if(week == Calendar.FRIDAY){
                return "5";
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            return sdf.format(date);
        }

        public static void main(String[] args) {
            Date beginTime = strToDate("2023-10-01",DATE_FORMAT);
            Date endTime = strToDate("2023-10-30",DATE_FORMAT);
            List<String> list = getPerStrDayList(beginTime,endTime);
            System.out.println("");
        }

}
