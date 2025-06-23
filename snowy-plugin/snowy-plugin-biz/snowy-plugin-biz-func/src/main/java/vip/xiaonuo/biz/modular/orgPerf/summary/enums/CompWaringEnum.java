package vip.xiaonuo.biz.modular.orgPerf.summary.enums;

import lombok.Getter;

/**
 * 完成值预警枚举
 *
 * @author pzh
 * @date  2025/06/18 10:02
 **/
@Getter
public enum CompWaringEnum {

    RED_LIGHT("红灯预警"),

    YELLOW_LIGHT("黄灯预警"),
    ;

    private final String value;

    CompWaringEnum(String value) {
        this.value = value;
    }
}
