package vip.xiaonuo.biz.modular.prj.expense.enums;

import lombok.Getter;

/**
 * 财务管理 - 费用报销 - 财务收单编码库枚举
 *
 * @author yong.ye
 * @date  2024/08/01 11:21
 **/
@Getter
public enum BizFinCodeEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizFinCodeEnum(String value) {
        this.value = value;
    }
}
