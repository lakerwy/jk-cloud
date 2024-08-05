package vip.xiaonuo.biz.modular.prj.expense.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date 日期处理工具类
 */
public class MyDateUtils {
    /**
     * 获取 年
     * @param tmpDate
     * @return
     */
    public static String getYear(Date tmpDate){
        // 创建 Calendar 实例
        Calendar calendar = Calendar.getInstance();
        // 将 Date 设置到 Calendar 中
        calendar.setTime(tmpDate);
        // 获取年、月、日
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /**
     * 获取 月
     * @param tmpDate
     * @return
     */
    public static String getMonth(Date tmpDate){
        // 创建 Calendar 实例
        Calendar calendar = Calendar.getInstance();
        // 将 Date 设置到 Calendar 中
        calendar.setTime(tmpDate);
        // 获取年、月、日
        return String.valueOf(calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取 日
     * @param tmpDate
     * @return
     */
    public static String getDay(Date tmpDate){
        // 创建 Calendar 实例
        Calendar calendar = Calendar.getInstance();
        // 将 Date 设置到 Calendar 中
        calendar.setTime(tmpDate);
        // 获取年、月、日
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 获取 2024年07月10日 【前导0】
     * @param tmpDate
     * @return
     */
    public static String getYearMonthDay(Date tmpDate){
        // 格式化日期
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        String year = yearFormat.format(tmpDate);
        String month = monthFormat.format(tmpDate);
        String day = dayFormat.format(tmpDate);
        return year+"年"+month+"月"+day+"日";
    }

    /**
     * 获取 2024年7月10日  【不前导0】
     * @param tmpDate
     * @return
     */
    public static String getYearMonthDay2(Date tmpDate){
        // 格式化日期
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("M");
        SimpleDateFormat dayFormat = new SimpleDateFormat("d");
        String year = yearFormat.format(tmpDate);
        String month = monthFormat.format(tmpDate);
        String day = dayFormat.format(tmpDate);
        return year+"年"+month+"月"+day+"日";
    }
}
