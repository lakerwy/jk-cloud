package vip.xiaonuo.biz.modular.prj.exp.enums;

import lombok.Getter;

/**
 * 费用报销（二期）- 归还状态  枚举
 *
 * @author yong.ye
 * @date  2025/02/25 09:15
 **/
@Getter
public enum BizFormCashBackStatusEnum {

    /** 未归还 */
    NO_BACK("未归还"),

    /** 已归还 */
    BACK("已归还"),

    /** 已逾期 */
    OVERDUE("已逾期");

    private final String value;

    BizFormCashBackStatusEnum(String value) {
        this.value = value;
    }
}
