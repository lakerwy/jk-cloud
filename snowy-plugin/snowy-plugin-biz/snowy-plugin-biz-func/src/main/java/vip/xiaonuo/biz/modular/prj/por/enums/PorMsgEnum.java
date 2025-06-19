package vip.xiaonuo.biz.modular.prj.por.enums;

import lombok.Getter;

/**
 * 项目产值台账 - 收款提醒枚举
 *
 * @author yy
 * @date  2025/06/18 16:13
 **/
@Getter
public enum PorMsgEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PorMsgEnum(String value) {
        this.value = value;
    }
}
