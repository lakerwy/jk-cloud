package vip.xiaonuo.sys.modular.hr.techAchive.enums;

import lombok.Getter;

/**
 * 查新枚举
 *
 * @author pzh
 * @date  2024/10/18 08:44
 **/
@Getter
public enum NoveltySearchEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    NoveltySearchEnum(String value) {
        this.value = value;
    }
}
