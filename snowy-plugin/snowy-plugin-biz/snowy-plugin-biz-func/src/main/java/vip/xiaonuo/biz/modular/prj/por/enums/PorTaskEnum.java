package vip.xiaonuo.biz.modular.prj.por.enums;

import lombok.Getter;

/**
 * 项目产值台账 - 项目管理 - 项目计划管理枚举
 *
 * @author yy
 * @date  2025/06/03 09:42
 **/
@Getter
public enum PorTaskEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PorTaskEnum(String value) {
        this.value = value;
    }
}
