package vip.xiaonuo.biz.modular.prj.por.enums;

import lombok.Getter;

/**
 * 项目产值台账 - 项目管理 - 项目预警记录枚举
 *
 * @author yy
 * @date  2025/06/03 09:41
 **/
@Getter
public enum PorWarnEnum {

    /** 回款风险预警 */
    HK_FX_WARN("回款风险预警"),

    /** 项目交付延期预警 */
    JF_YQ_WARN("项目交付延期预警");

    private final String value;

    PorWarnEnum(String value) {
        this.value = value;
    }
}
