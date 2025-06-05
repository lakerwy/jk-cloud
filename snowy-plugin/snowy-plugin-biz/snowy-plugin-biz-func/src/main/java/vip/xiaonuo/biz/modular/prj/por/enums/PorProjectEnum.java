package vip.xiaonuo.biz.modular.prj.por.enums;

import lombok.Getter;

/**
 * 项目产值台账 - 项目管理枚举
 *
 * @author yy
 * @date  2025/06/03 09:39
 **/
@Getter
public enum PorProjectEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PorProjectEnum(String value) {
        this.value = value;
    }
}
