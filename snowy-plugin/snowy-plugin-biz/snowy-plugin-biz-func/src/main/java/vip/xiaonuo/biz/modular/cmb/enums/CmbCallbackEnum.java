package vip.xiaonuo.biz.modular.cmb.enums;

import lombok.Getter;

/**
 * 招商银行 - 回调记录枚举
 *
 * @author pzh
 * @date  2025/02/08 10:09
 **/
@Getter
public enum CmbCallbackEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    CmbCallbackEnum(String value) {
        this.value = value;
    }
}
