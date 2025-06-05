package vip.xiaonuo.biz.modular.prj.por.enums;

import lombok.Getter;

/**
 * 项目产值台账 - 项目管理 - 责任上缴费用管理枚举
 *
 * @author yy
 * @date  2025/06/03 09:55
 **/
@Getter
public enum PorRpmEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PorRpmEnum(String value) {
        this.value = value;
    }
}
