package vip.xiaonuo.sys.core.enums;

import lombok.Getter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/26 15:51
 */
@Getter
public enum EpcDeptEnum {
    EPC_DEP_SJB("deptSjb", "设计技术部"),

    EPC_DEP_GCGL("deptGcgl", "工程管理部"),

    EPC_DEP_ZLAQ("deptZlaq", "质量安全部"),

    EPC_DEP_WZZB("deptWzzb", "物资设备部"),

    EPC_DEP_HYYS("deptHyys", "合约预算部"),

    EPC_DEP_ZHGL("deptZhgl", "综合管理部");

    private final String value;

    private final String name;

    EpcDeptEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
