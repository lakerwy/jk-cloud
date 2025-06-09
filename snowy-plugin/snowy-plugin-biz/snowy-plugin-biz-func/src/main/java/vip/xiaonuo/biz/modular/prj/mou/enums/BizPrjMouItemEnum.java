package vip.xiaonuo.biz.modular.prj.mou.enums;

import lombok.Getter;

/**
 * 项目承接备忘录 - 项目生产内部分工枚举
 *
 * @author yy
 * @date  2025/06/06 11:43
 **/
@Getter
public enum BizPrjMouItemEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizPrjMouItemEnum(String value) {
        this.value = value;
    }
}
