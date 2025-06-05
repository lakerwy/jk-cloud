package vip.xiaonuo.biz.modular.prj.por.enums;

import lombok.Getter;

/**
 * 项目产值台账 - 项目管理 - 项目现场管理费用枚举
 *
 * @author yy
 * @date  2025/06/03 09:45
 **/
@Getter
public enum PorSiteFeeEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PorSiteFeeEnum(String value) {
        this.value = value;
    }
}
