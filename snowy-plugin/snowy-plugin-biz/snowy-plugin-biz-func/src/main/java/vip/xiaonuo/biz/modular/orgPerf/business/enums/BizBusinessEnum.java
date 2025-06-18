package vip.xiaonuo.biz.modular.orgPerf.business.enums;

import lombok.Getter;

/**
 * 业务情况枚举
 *
 * @author pzh
 * @date  2025/06/17 14:48
 **/
@Getter
public enum BizBusinessEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizBusinessEnum(String value) {
        this.value = value;
    }
}
