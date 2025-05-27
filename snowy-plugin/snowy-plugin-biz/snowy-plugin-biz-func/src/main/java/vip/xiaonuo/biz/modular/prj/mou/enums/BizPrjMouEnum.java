package vip.xiaonuo.biz.modular.prj.mou.enums;

import lombok.Getter;

/**
 * 项目承接备忘录枚举
 *
 * @author yy
 * @date  2025/05/27 10:06
 **/
@Getter
public enum BizPrjMouEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizPrjMouEnum(String value) {
        this.value = value;
    }
}
