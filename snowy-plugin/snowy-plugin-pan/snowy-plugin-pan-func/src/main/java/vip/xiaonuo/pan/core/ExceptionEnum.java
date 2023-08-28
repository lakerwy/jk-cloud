package vip.xiaonuo.pan.core;


import lombok.Getter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/8/28 17:12
 */
@Getter
public enum ExceptionEnum {
    SOURCE_FOLD_IS_NOT_EXIT(4001,"文件夹不存在"),

    TARGET_FOLD_IS_NOT_EXIT(4002,"文件夹不存在"),

    FOLD_MOVE_NOT_SUPPORT(4003,"该文件夹移动不被支持");

    private final  Integer code;

    private final String value;
    ExceptionEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
