package vip.xiaonuo.sys.modular.hr.honorAward.enums;

import lombok.Getter;

/**
 * 企业荣誉枚举
 *
 * @author pzh
 * @date  2024/10/16 11:17
 **/
@Getter
public enum EnterpriseHonorEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    EnterpriseHonorEnum(String value) {
        this.value = value;
    }
}
