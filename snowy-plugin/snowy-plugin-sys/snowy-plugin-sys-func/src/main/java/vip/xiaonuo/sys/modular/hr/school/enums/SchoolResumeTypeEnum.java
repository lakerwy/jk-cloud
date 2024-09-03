package vip.xiaonuo.sys.modular.hr.school.enums;

import lombok.Getter;

/**
 * 校招 - （应聘者）简历库信息枚举
 *
 * @author yong.ye
 * @date  2024/09/03 10:44
 **/
@Getter
public enum SchoolResumeTypeEnum {

    /** 春招 */
    SPRING("春招"),

    /** 秋招 */
    AUTUMN("秋招");

    private final String value;

    SchoolResumeTypeEnum(String value) {
        this.value = value;
    }
}
