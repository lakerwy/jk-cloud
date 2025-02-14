package vip.xiaonuo.common.enums;

import lombok.Getter;

/**
 * 操作类型枚举
 *
 * @author pzh
 * @date  2025/02/13 16:48
 **/
@Getter
public enum OperationTypeEnum {
    SELECT("查询"),
    INSERT("添加"),
    UPDATE("修改"),
    DELETE("删除");

    private final String value;

    OperationTypeEnum(String value) {
        this.value = value;
    }
}
