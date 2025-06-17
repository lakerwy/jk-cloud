package vip.xiaonuo.biz.modular.orgPerf.product.enums;

import lombok.Getter;

/**
 * 产值情况枚举
 *
 * @author pzh
 * @date  2025/06/17 09:03
 **/
@Getter
public enum BizProductEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizProductEnum(String value) {
        this.value = value;
    }
}
