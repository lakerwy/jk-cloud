package vip.xiaonuo.pan.modular.share.enums;

import lombok.Getter;

/**
 * @author yong.ye
 * @date 2023/9/8 9:22
 */

@Getter
public enum PanShareTypeEnum {

    //参数状态
    PUBLIC(0, "公共"),

    PRIVATE(1, "私密"),

    FRIEND(2, "好友");

    private final Integer value;

    private final String name;

    PanShareTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
