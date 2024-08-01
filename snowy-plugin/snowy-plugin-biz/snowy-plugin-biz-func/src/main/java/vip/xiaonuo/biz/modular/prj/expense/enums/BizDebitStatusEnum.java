package vip.xiaonuo.biz.modular.prj.expense.enums;

import lombok.Getter;

/**
 * 财务管理 - 费用报销 - 研发项目信息表枚举
 *
 * @author yong.ye
 * @date  2024/08/01 11:22
 **/
@Getter
public enum BizDebitStatusEnum {

    /** 待归还 */
    PRE_RETURN("0"),

    /** 已归还 */
    RETURN("1");

    private final String value;

    BizDebitStatusEnum(String value) {
        this.value = value;
    }
}
