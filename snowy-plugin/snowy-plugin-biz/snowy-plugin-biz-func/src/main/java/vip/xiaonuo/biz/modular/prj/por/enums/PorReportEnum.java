package vip.xiaonuo.biz.modular.prj.por.enums;

import lombok.Getter;

/**
 * 项目产值台账 - 项目管理 - 项目周报|月报枚举
 *
 * @author yy
 * @date  2025/06/03 09:44
 **/
@Getter
public enum PorReportEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PorReportEnum(String value) {
        this.value = value;
    }
}
