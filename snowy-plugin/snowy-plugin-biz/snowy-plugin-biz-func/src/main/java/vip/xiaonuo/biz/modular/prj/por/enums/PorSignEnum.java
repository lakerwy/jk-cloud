package vip.xiaonuo.biz.modular.prj.por.enums;

import lombok.Getter;

/**
 * 项目产值台账 - 项目管理 - 签字审图费管理枚举
 *
 * @author yy
 * @date  2025/06/03 09:54
 **/
@Getter
public enum PorSignEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    PorSignEnum(String value) {
        this.value = value;
    }
}
