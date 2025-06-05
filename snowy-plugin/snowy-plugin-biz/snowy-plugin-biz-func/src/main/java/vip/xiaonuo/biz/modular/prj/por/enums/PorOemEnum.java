package vip.xiaonuo.biz.modular.prj.por.enums;

import lombok.Getter;

/**
 * 项目产值台账 - 项目管理 - 经营费管理枚举
 *
 * @author yy
 * @date  2025/06/03 09:56
 **/
@Getter
public enum PorOemEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PorOemEnum(String value) {
        this.value = value;
    }
}
