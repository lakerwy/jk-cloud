package vip.xiaonuo.sys.modular.order.enums;

import lombok.Getter;

/**
 * 运维工单枚举
 *
 * @author yong.ye
 * @date  2025/01/13 16:13
 **/
@Getter
public enum OmOrderEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    OmOrderEnum(String value) {
        this.value = value;
    }
}
