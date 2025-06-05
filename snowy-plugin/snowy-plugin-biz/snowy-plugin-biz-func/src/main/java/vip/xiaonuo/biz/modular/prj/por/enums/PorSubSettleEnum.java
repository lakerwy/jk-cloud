package vip.xiaonuo.biz.modular.prj.por.enums;

import lombok.Getter;

/**
 * 项目产值台账 - 项目管理 - 项目分包结算记录枚举
 *
 * @author yy
 * @date  2025/06/03 09:53
 **/
@Getter
public enum PorSubSettleEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PorSubSettleEnum(String value) {
        this.value = value;
    }
}
