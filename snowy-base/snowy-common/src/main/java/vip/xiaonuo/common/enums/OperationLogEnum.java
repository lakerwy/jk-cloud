package vip.xiaonuo.common.enums;

import lombok.Getter;

/**
 * 操作日志枚举
 *
 * @author pzh
 * @date  2025/02/13 16:48
 **/
@Getter
public enum OperationLogEnum {

    DINGTALK_ATTEND_INSERTE(OperationModuleEnum.DINGTALK_ATTEND.getValue(),OperationTypeEnum.INSERT.getValue()),
    DINGTALK_ATTEND_UPDATE(OperationModuleEnum.DINGTALK_ATTEND.getValue(),OperationTypeEnum.UPDATE.getValue()),
    DINGTALK_ATTEND_DELETE(OperationModuleEnum.DINGTALK_ATTEND.getValue(),OperationTypeEnum.DELETE.getValue());

    private final String name;
    private final String category;

    OperationLogEnum(String name, String category) {
        this.name = name;
        this.category = category;
    }
}


