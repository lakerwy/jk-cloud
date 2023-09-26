package vip.xiaonuo.sys.core.enums;

import lombok.Getter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/26 15:51
 */
@Getter
public enum EpcRoleEnum {
    /** 项目经理 */
    EPC_PROJECT_MANAGER("projectManager", "项目经理"),

    /** 项目副经理 */
    EPC_PROJECT_DEPUTY_MANAGER("projectDeputyManager", "项目副经理"),

    EPC_PROJECT_ENGINEER("projectEngineer", "项目总工"),

    EPC_PROJECT_SJGLY("projectSjgly", "设计管理员"),

    EPC_PROJECT_YLJSY("projectYLSJ", "业内技术员"),

    EPC_PROJECT_ZLY("projectZLY", "资料员"),

    EPC_PROJECT_SGY("projectSGY", "施工员"),

    EPC_PROJECT_CLY("projectCLY", "测量员"),

    EPC_PROJECT_ZHLY("projectZHLY", "质量员"),

    EPC_PROJECT_SYY("projectSYY", "试验员"),

    EPC_PROJECT_AQY("projectAQY", "安全员"),

    EPC_PROJECT_CLIY("projectCLIY", "材料员"),

    EPC_PROJECT_JDSBY("projectJDSBY", "机电设备员"),

    EPC_PROJECT_YSY("projectYSY", "预算员"),

    EPC_PROJECT_XZHQ("projectXZHQ", "行政后勤");


    private final String value;

    private final String name;

    EpcRoleEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
