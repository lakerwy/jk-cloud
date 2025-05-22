package vip.xiaonuo.biz.modular.prj.exp.enums;

import lombok.Getter;

/**
 * 费用报销（二期）- 报销单  冲账类型  枚举
 *
 * @author yong.ye
 * @date  2025/02/24 15:12
 **/
@Getter
public enum BizFormOffsetTypeEnum {

    /** 借支单 */
    CASH("借支单"),

    /** 投标费用 */
    BID_FEE("投标费用"),

    /** 不使用单据冲账 */
    NON("不使用单据冲账");

    private final String value;

    BizFormOffsetTypeEnum(String value) {
        this.value = value;
    }
}
