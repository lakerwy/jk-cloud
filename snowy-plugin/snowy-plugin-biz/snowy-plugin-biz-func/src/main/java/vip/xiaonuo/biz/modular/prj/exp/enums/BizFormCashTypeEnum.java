package vip.xiaonuo.biz.modular.prj.exp.enums;

import lombok.Getter;

/**
 * 费用报销（二期）- 借支单  借支类型  枚举
 *
 * @author yong.ye
 * @date  2025/02/24 15:12
 **/
@Getter
public enum BizFormCashTypeEnum {

    /** 预付款 */
    YFK("预付款"),

    /** 押金 */
    YJ("押金"),

    /** 备用金 */
    BYJ("备用金");

    private final String value;

    BizFormCashTypeEnum(String value) {
        this.value = value;
    }
}
