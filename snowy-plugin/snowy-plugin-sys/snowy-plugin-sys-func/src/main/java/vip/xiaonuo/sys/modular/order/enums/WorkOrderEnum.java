package vip.xiaonuo.sys.modular.order.enums;

import lombok.Getter;

/**
 * 事件处理单枚举
 *
 * @author yong.ye
 * @date  2025/01/13 15:34
 **/
@Getter
public enum WorkOrderEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    WorkOrderEnum(String value) {
        this.value = value;
    }
}
