package vip.xiaonuo.biz.modular.branch.enums;

import lombok.Getter;

/**
 * 分公司银行账户信息枚举
 *
 * @author pzh
 * @date  2024/08/05 11:46
 **/
@Getter
public enum BranchBankInfoEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BranchBankInfoEnum(String value) {
        this.value = value;
    }
}
