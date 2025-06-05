package vip.xiaonuo.biz.modular.prj.por.enums;

import lombok.Getter;

/**
 * 项目产值台账 - 项目管理 - 项目预警记录枚举
 *
 * @author yy
 * @date  2025/06/03 09:41
 **/
@Getter
public enum PorWarnEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PorWarnEnum(String value) {
        this.value = value;
    }
}
