package vip.xiaonuo.biz.modular.prj.por.enums;

import lombok.Getter;

/**
 * 项目产值台账 - 项目管理 - 项目业主结算记录枚举
 *
 * @author yy
 * @date  2025/06/03 09:52
 **/
@Getter
public enum PorOwnerSettleEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PorOwnerSettleEnum(String value) {
        this.value = value;
    }
}
