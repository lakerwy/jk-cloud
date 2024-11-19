package vip.xiaonuo.sys.modular.hr.techAchive.enums;

import lombok.Getter;

/**
 * 专利信息枚举
 *
 * @author pzh
 * @date  2024/10/17 10:22
 **/
@Getter
public enum PatentInfoEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PatentInfoEnum(String value) {
        this.value = value;
    }
}
