package vip.xiaonuo.sys.modular.hr.icManage.enums;

import lombok.Getter;

/**
 * 工商信息新增|变更|注销--流程申请枚举
 *
 * @author yy
 * @date  2025/04/21 15:58
 **/
@Getter
public enum IcManageApplyEnum {

    /** 新增 */
    ADD("新增"),

    /** 变更 */
    CHANGE("变更"),

    /** 注销 */
    CANCEL("注销");

    private final String value;

    IcManageApplyEnum(String value) {
        this.value = value;
    }
}
