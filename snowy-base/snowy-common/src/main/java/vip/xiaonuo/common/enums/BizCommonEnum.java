package vip.xiaonuo.common.enums;

public enum BizCommonEnum {
    PROJECT("PROJECT","BIZ_PROJECT","BizProjectService"), //立项申请业务
    PROJECT_REGISTER("PROJECT_REGISTER","BIZ_PROJECT_REGISTER","BizProjectRegisterService"), //信息报备业务
    PROJECT_FUNDS("PROJECT_FUNDS","BIZ_PROJECT_FUNDS","BizProjectFundsService"), //项目拨付款业务
    BIZ_AUDIT_LISTS("BIZ_AUDIT_LISTS","BIZ_AUDIT_LISTS","BizAuditListsServiceImpl"), //工程校审业务
    BIZ_MAP_REGISTER("BIZ_MAP_REGISTER","BIZ_MAP_REGISTER","BizMapRegisterServiceImpl"), //工程出图业务
    PERSONNEL_FILE_MOVES("PERSONNEL_FILE_MOVES","PERSONNEL_FILE_MOVES","BizAuditListsServiceImpl"); //人事异动业务

    String bizModel;
    String bizTable;
    String  resourceService;

    BizCommonEnum(String bizModel, String bizTable, String resourceService) {
        this.bizModel = bizModel;
        this.bizTable = bizTable;
        this.resourceService = resourceService;
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

    public String getResourceService() {
        return resourceService;
    }

    public void setResourceService(String resourceService) {
        this.resourceService = resourceService;
    }

    public static BizCommonEnum findByCommonTable(String tableName) {
        // 遍历所有枚举常量
        for (BizCommonEnum bizCommon : BizCommonEnum.values()) {
            // 通过 name() 方法获取枚举常量名称，与提供的颜色名称进行比较
            if (bizCommon.bizTable.equals(tableName)) {
                // 如果找到了匹配的常量，则返回该常量
                return bizCommon;
            }
        }
        // 如果未找到匹配的常量，则返回 null 或抛出异常，具体取决于您的需求
        return null;
    }
}
