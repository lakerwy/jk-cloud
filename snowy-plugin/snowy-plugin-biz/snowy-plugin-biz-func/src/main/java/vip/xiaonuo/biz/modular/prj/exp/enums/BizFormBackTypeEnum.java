package vip.xiaonuo.biz.modular.prj.exp.enums;

import lombok.Getter;

/**
 * 费用报销（二期）- 归还方式  枚举
 *
 * @author yong.ye
 * @date  2025/02/25 09:15
 **/
@Getter
public enum BizFormBackTypeEnum {

    /** 转账 */
    TURN("转账"),

    /** 现金 */
    CASH("现金");

    private final String value;

    BizFormBackTypeEnum(String value) {
        this.value = value;
    }
}
