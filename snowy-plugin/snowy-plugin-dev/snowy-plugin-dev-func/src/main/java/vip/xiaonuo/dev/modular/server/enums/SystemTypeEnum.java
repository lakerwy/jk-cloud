package vip.xiaonuo.dev.modular.server.enums;

import lombok.Getter;
import vip.xiaonuo.common.consts.RabbitConstant;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/16 19:37
 * 子系统配置
 */
@Getter
public enum SystemTypeEnum {
    EPC("epc","EPC_CONFIG_ROLE_DATA", RabbitConstant.ROUTING_EPC_KEY),
    SUPERV("superv","SUPERV_CONFIG_ROLE_DATA",RabbitConstant.ROUTING_SUPERV_KEY)
    ;

    private final String typeName; //子系统名称
    private final String typeConfigValue;  //子系统配置名称
    private final String routingKey;

    SystemTypeEnum(String typeName, String typeConfigValue, String routingKey) {
        this.typeName = typeName;
        this.typeConfigValue = typeConfigValue;
        this.routingKey = routingKey;
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

    // 通过 typeName 获取 typeConfigValue
    public static String getRoutingKeyByTypeName(String typeName) {
        for (SystemTypeEnum typeEnum : SystemTypeEnum.values()) {
            if (typeEnum.getTypeName().equals(typeName)) {
                return typeEnum.getRoutingKey();
            }
        }
        return null; // 或者可以抛出异常
    }
}
