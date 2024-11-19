package vip.xiaonuo.sys.modular.hr.techAchive.enums;

import lombok.Getter;

/**
 * 其他（科技成果）枚举
 *
 * @author pzh
 * @date  2024/10/18 09:10
 **/
@Getter
public enum OtherTechAchiveEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    OtherTechAchiveEnum(String value) {
        this.value = value;
    }
}
