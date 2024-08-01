package vip.xiaonuo.biz.modular.prj.expense.enums;

import lombok.Getter;

/**
 * 财务管理 - 费用报销 - 研发项目信息表枚举
 *
 * @author yong.ye
 * @date  2024/08/01 11:22
 **/
@Getter
public enum BizExpDevEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizExpDevEnum(String value) {
        this.value = value;
    }
}
