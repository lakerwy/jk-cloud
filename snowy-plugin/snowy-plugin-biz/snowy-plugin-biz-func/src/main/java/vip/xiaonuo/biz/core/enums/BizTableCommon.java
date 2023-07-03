package vip.xiaonuo.biz.core.enums;

public enum BizTableCommon {
    BIZ_CHART_REVIEWS("BIZ_CHART_REVIEWS","BIZ_CHART_REVIEWS","TS","图审受理业务"), //图审受理业务
    BIZ_AUDIT_LISTS("BIZ_AUDIT_LISTS","BIZ_AUDIT_LISTS","JS","设计校审业务"), //设计校审业务
    BIZ_MAP_REGISTER("BIZ_MAP_REGISTER","BIZ_MAP_REGISTER","CT","出图登记业务"); //出图登记业务

    String bizModel;
    String bizTable;
    String  snPrefix;
    String desc;

    BizTableCommon(String bizModel, String bizTable, String snPrefix, String desc) {
        this.bizModel = bizModel;
        this.bizTable = bizTable;
        this.snPrefix = snPrefix;
        this.desc = desc;
    }

    public String getBizModel() {
        return bizModel;
    }

    public void setBizModel(String bizModel) {
        this.bizModel = bizModel;
    }

    public String getBizTable() {
        return bizTable;
    }

    public void setBizTable(String bizTable) {
        this.bizTable = bizTable;
    }


    public String getSnPrefix() {
        return snPrefix;
    }

    public void setSnPrefix(String snPrefix) {
        this.snPrefix = snPrefix;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static BizTableCommon findByCommonTable(String tableName) {
        // 遍历所有枚举常量
        for (BizTableCommon bizCommon : BizTableCommon.values()) {
            // 通过 name() 方法获取枚举常量名称，与提供的名称进行比较
            if (bizCommon.bizTable.equals(tableName)) {
                // 如果找到了匹配的常量，则返回该常量
                return bizCommon;
            }
        }
        // 如果未找到匹配的常量，则返回 null 或抛出异常，具体取决于您的需求
        return null;
    }
}
