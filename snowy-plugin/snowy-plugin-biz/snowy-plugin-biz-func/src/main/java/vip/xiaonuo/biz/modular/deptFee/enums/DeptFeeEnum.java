package vip.xiaonuo.biz.modular.deptFee.enums;

import lombok.Getter;

/**
 * 部门费用管理枚举
 *
 * @author pzh
 * @date  2025/06/05 14:56
 **/
@Getter
public enum DeptFeeEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    DeptFeeEnum(String value) {
        this.value = value;
    }
}
