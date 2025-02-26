package vip.xiaonuo.biz.modular.prj.exp.enums;

import lombok.Getter;

/**
 * 费用报销（二期）- 费用报销单 - 成本明细枚举
 *
 * @author yong.ye
 * @date  2025/02/24 15:15
 **/
@Getter
public enum BizFormCashPayTypeEnum {

    /** 银行 */
    BANK("银行"),

    /** 现金 */
    CASH("现金");

    private final String value;

    BizFormCashPayTypeEnum(String value) {
        this.value = value;
    }
}
