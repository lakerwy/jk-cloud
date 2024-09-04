package vip.xiaonuo.sys.modular.hr.school.util;

import vip.xiaonuo.sys.modular.hr.school.enums.SchoolResumeTypeEnum;

import java.time.LocalDate;

/**
 * 校招 - 工具类
 */
public class SchoolUtils {

    /**
     * 获取当前年份 2024
     * @return
     */
    public static String getCurrentYear(){
        return String.valueOf(LocalDate.now().getYear());
    }

    /**
     * 根据当前日期，获取是 春招|秋招
     * @return
     */
    public static String getTypeEnum(){
        LocalDate today = LocalDate.now();
        return today.getMonthValue() < 7 ? SchoolResumeTypeEnum.SPRING.getValue() : SchoolResumeTypeEnum.AUTUMN.getValue();
    }
}
