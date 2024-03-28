package vip.xiaonuo.common.enums;

public enum BizCommonEnum {
    PROJECT("PROJECT","BIZ_PROJECT","BizProjectService"), //立项申请业务
    PROJECT_REGISTER("PROJECT_REGISTER","BIZ_PROJECT_REGISTER","BizProjectRegisterService"), //信息报备业务
    PROJECT_FUNDS("PROJECT_FUNDS","BIZ_PROJECT_FUNDS","BizProjectFundsService"), //项目拨付款业务
    BIZ_AUDIT_LISTS("BIZ_AUDIT_LISTS","BIZ_AUDIT_LISTS","BizAuditListsServiceImpl"), //工程校审业务
    BIZ_MAP_REGISTER("BIZ_MAP_REGISTER","BIZ_MAP_REGISTER","BizMapRegisterServiceImpl"), //工程出图业务
    PERSONNEL_FILE_MOVES("PERSONNEL_FILE_MOVES","PERSONNEL_FILE_MOVES","BizAuditListsServiceImpl"), //人事异动业务
    BIZ_TASK_FLW("BIZ_TASK_FLW","BIZ_TASK_FLW","BizTaskFlwServiceImpl"), //任务中心-部门绩效业务-流程申请单
    BIZ_TASK("BIZ_TASK","BIZ_TASK","BizTaskServiceImpl"), //任务中心-部门绩效业务-实际业务表（1-n）
    BIZ_CRM_HETONG("BIZ_CRM_HETONG","BIZ_CRM_HETONG","HetongServiceImpl"), //CRM-收入合同业务
    BIZ_CRM_SUBCONTRACT("BIZ_CRM_SUBCONTRACT","BIZ_CRM_SUBCONTRACT","SubcontractServiceImpl"), //CRM-分包合同业务
    BIZ_CRM_PURSALECONTRACT("BIZ_CRM_PURSALECONTRACT","BIZ_CRM_PURSALECONTRACT","PursalecontractServiceImpl"), //CRM-购销合同业务
    BIZ_RECRUIT_FLW("BIZ_RECRUIT_FLW","BIZ_RECRUIT_FLW","BizRecruitFlwServiceImpl"), //招聘-部门计划-流程单
    BIZ_RECRUIT_PLAN("BIZ_RECRUIT_PLAN","BIZ_RECRUIT_PLAN","BizRecruitPlanServiceImpl"), //招聘-部门计划-计划表（1-n）

    BIZ_EMP_TASK_FLW("BIZ_EMP_TASK_FLW","BIZ_EMP_TASK_FLW","BizEmpTaskFlwServiceImpl"), //员工绩效-季度计划-流程单
    BIZ_EMP_TASK("BIZ_EMP_TASK","BIZ_EMP_TASK","BizEmpTaskServiceImpl"), //员工绩效-季度计划-计划表（1-n）
    BIZ_EMP_PERFORMANCE("BIZ_EMP_PERFORMANCE","BIZ_EMP_PERFORMANCE","BizEmpPerformanceServiceImpl"), //员工绩效-季度考核分（1-1）

    BIZ_PRJ_PLAN("BIZ_PRJ_PLAN","BIZ_PRJ_PLAN","BizPrjPlanServiceImpl"),  //项目计划付款单
    BIZ_PRJ_SETTLE("BIZ_PRJ_SETTLE","BIZ_PRJ_SETTLE","BizPrjSettleServiceImpl"),  //项目款结算单
    BIZ_PRJ_ALLOTMENT("BIZ_PRJ_ALLOTMENT","BIZ_PRJ_ALLOTMENT","BizPrjAllotmentServiceImpl"),  //项目拨付款申请单
    HR_APPLY_ATTENDANCE("HR_USER_APPLY_ATTENDANCE","HR_USER_APPLY_ATTENDANCE","HrUserApplyAttendanceServiceImpl"),//申请补卡
    HR_USER_APPLY_HOLIDAY("HR_USER_APPLY_HOLIDAY","HR_USER_APPLY_HOLIDAY","HrUserApplyHolidayServiceImpl"),//请假
    HR_APPLY_WORK_OT("HR_APPLY_WORK_OT","HR_APPLY_WORK_OT","HrApplyWorkOtServiceImpl"),//申请加班
    HR_USER_APPLY_GOOUT("HR_USER_APPLY_GOOUT","HR_USER_APPLY_GOOUT","HrUserApplyGooutServiceImpl"),//申请出差
    APPLY_REG_CERT_DATA_FLOW("APPLY_REG_CERT_DATA_FLOW","APPLY_REG_CERT_DATA_FLOW","ApplyRegCertDataFlowServiceImpl"),//申请补贴

    BIZ_VISIT("BIZ_VISIT","BIZ_VISIT","BizVisitServiceImpl"),  //董事办-外访记录

    HR_USER_BUSINESS_TRIP_FLW("HR_USER_BUSINESS_TRIP_FLW","HR_USER_BUSINESS_TRIP_FLW","HrUserBusinessTripFlwServiceImpl"), //申请出差流程表
    HR_USER_BUSINESS_TRIP("HR_USER_BUSINESS_TRIP","HR_USER_BUSINESS_TRIP","HrUserBusinessTripServiceImpl"), //申请出差主体表（1-n）
    HR_SUB_PERSONNEL("HR_SUB_PERSONNEL","HR_SUB_PERSONNEL","SubPersonnelServiceImpl"),  //分公司人事管理
    HR_SUB_SALARY("HR_SUB_SALARY","HR_SUB_SALARY","SubSalaryServiceImpl"),  //分公司人事管理-工资条

    HR_SUB_TRANSFER("HR_SUB_TRANSFER","HR_SUB_TRANSFER","HrSubTransferServiceImpl"),  //分公司人事管理-工资社保转入|转出总部

    BIZ_INVOICE_COST("BIZ_INVOICE_COST","BIZ_INVOICE_COST","InvoiceCostServiceImpl"),  //财务系统-成本发票-申请
    BIZ_INVOICE_SALE("BIZ_INVOICE_SALE","BIZ_INVOICE_SALE","InvoiceSaleServiceImpl"),  //财务系统-销售发票-申请
    BIZ_INVOICE_SALE_MOVE("BIZ_INVOICE_SALE_MOVE","BIZ_INVOICE_SALE_MOVE","InvoiceSaleMoveServiceImpl"),  //销售发票-作废|红冲-异动申请
    BIZ_INVOICE_PRJ_REFUND("BIZ_INVOICE_PRJ_REFUND","BIZ_INVOICE_PRJ_REFUND","InvoicePrjRefundServiceImpl");  //财务系统-项目回款录入-申请

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
