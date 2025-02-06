package vip.xiaonuo.sys.modular.hr.sub.enums;

import lombok.Getter;

/**
 * 分公司-五险一金 - 社保|公积金明细枚举
 *
 * @author yong.ye
 * @date  2025/02/06 09:13
 **/
@Getter
public enum SubFundItemTypeEnum {

    /** 社保 */
    INS("社保"),
    /** 公积金 */
    GJJ("公积金");

    private final String value;

    SubFundItemTypeEnum(String value) {
        this.value = value;
    }
}
