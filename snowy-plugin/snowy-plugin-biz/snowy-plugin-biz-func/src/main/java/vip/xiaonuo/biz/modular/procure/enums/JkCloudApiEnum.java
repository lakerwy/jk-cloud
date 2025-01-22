package vip.xiaonuo.biz.modular.procure.enums;

import lombok.Getter;

/**
 * 建科云接口枚举
 *
 * @author pzh
 * @date  2024/10/16 09:20
 **/
@Getter
public enum JkCloudApiEnum {

    /**
     * 查询人员信息（总部人员）
     */
    PERSONNEL_PAGE_LIST("/api/webapp/openApi/sys/personnel/pageList"),

    /**
     * 查询人员详情（总部人员）
     */
    PERSONNEL_PAGE_DETAIL("/api/webapp/openApi/sys/personnel/detail"),

    /**
     * 获取组织分页
     */
    ORG_PAGE_LIST("/api/webapp/openApi/sys/org/page"),

    /**
     * 获取字典树（包括行业分类、项目类型等）
     */
    DICT_TREE("/api/webapp/dev/dict/tree"),

    /**
     * 获取业务表-CRM-合同分页
     */
    HETONG_PAGE("/api/bizapp/openApi/biz/crm/hetong/page"),

    /**
     * 获取资质证照分页
     */
//    ZIZHI_PAGE("/api/webapp/openApi/sys/zizhiCertificate/page"),


    /**
     * 获取资质证照分页
     */
    BIZ_ZIZHI_PAGE("/api/bizapp/openApi/biz/ziZhi/page"),

    /**
     * 获取建科资产管理表分页
     */
    ASSET_MANAGE_FLOW_PAGE("/api/webapp/openApi/sys/assetManageFlow/page"),

    /**
     * 获取业务表-CRM-合同分页
     */
    ATTACHMENT_INFO("/api/webapp/pan/attachment/info"),

    /**
     * 获取企业荣誉分页
     */
    ENTERPRISE_HONOR_PAGE("/api/webapp/openApi/sys/enterpriseHonor/page"),

    /**
     * 获取荣誉奖项分页
     */
    HONOR_AWARD_PAGE("/api/webapp/openApi/sys/honorAward/page"),

    /**
     * 获取专利信息分页
     */
    PATENT_INFO_PAGE("/api/webapp/openApi/sys/techAchive/patentInfo/page"),

    /**
     * 获取软著信息分页
     */
    SOFTWARE_COPYRIGHT_PAGE("/api/webapp/openApi/sys/techAchive/softwareCopyright/page"),

    /**
     * 获取标准规范分页
     */
    STANDARD_PAGE("/api/webapp/openApi/sys/techAchive/standard/page"),

    /**
     * 获取查新分页
     */
    NOVELTY_SEARCH_PAGE("/api/webapp/openApi/sys/techAchive/noveltySearch/page"),

    /**
     * 获取其他（科技成果）分页
     */
    OTHER_TECH_ACHIVE_PAGE("/api/webapp/openApi/sys/techAchive/otherTechAchive/page"),

    /**
     * 获取纳税等级评价分页
     */
    TAX_GRADE_EVALUATION_PAGE("/api/webapp/openApi/sys/taxGradeEvaluation/page"),

    /**
     * 获取认证体系分页
     */
    CERTIFICATION_SYSTEM_PAGE("/api/webapp/openApi/sys/certificationSystem/page"),


    /**
     * 获取信誉情况分页
     */
    REPUTATION_PAGE("/api/webapp/openApi/sys/reputation/page"),

    /**
     * 获取信用评价分页
     */
    CREDIT_EVALUATION_PAGE("/api/webapp/openApi/sys/creditEvaluation/page"),

    /**
     * 获取财务审计报告分页
     */
    FINANCIAL_AUDITREPORT_PAGE("/api/webapp/openApi/sys/financialAuditReport/page"),

    /**
     * 获取财务状况表分页
     */
    FINANCIAL_SHEET_PAGE("/api/webapp/openApi/sys/financialSheet/page"),
    ;

    private final String value;

    JkCloudApiEnum(String value) {
        this.value = value;
    }
}
