package vip.xiaonuo.dev.modular.server.enums;

import lombok.Getter;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/16 19:37
 * 子系统配置
 */
@Getter
public enum SystemTypeEnum {
    EPC("epc","EPC_CONFIG_ROLE_DATA"),
    SUPERV("superv","SUPERV_CONFIG_ROLE_DATA")
    ;

    private String typeName; //子系统名称
    private String typeConfigValue;  //子系统配置名称

    SystemTypeEnum(String typeName, String typeConfigValue) {
        this.typeName = typeName;
        this.typeConfigValue = typeConfigValue;
    }

    // 通过 typeName 获取 typeConfigValue
    public static String getConfigByTypeName(String typeName) {
        for (SystemTypeEnum typeEnum : SystemTypeEnum.values()) {
            if (typeEnum.getTypeName().equals(typeName)) {
                return typeEnum.getTypeConfigValue();
            }
        }
        return null; // 或者可以抛出异常
    }
}
