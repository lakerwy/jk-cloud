package vip.xiaonuo.biz.modular.prj.expense.enums;

import lombok.Getter;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 申请流程枚举
 *
 * @author yong.ye
 * @date  2024/07/30 15:26
 **/
@Getter
public enum BizExpTypeEnum {

    /** 费用报销单 */
    FEE("FEE"),

    /** 差旅费用报销单 */
    TRAFFIC_FEE("TRAFFIC"),

    /** 借支单 */
    DEBIT("DEBIT");

    private final String value;

    BizExpTypeEnum(String value) {
        this.value = value;
    }
}
