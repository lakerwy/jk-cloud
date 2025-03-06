package vip.xiaonuo.sys.modular.hr.emp.enums;

import lombok.Getter;

/**
 * 钉钉考勤-员工白名单类型枚举
 *
 * @author pzh
 * @date  2025/03/06 11:34
 **/
@Getter
public enum AttendWhiteListTypeEnum {

    LEADER("高管"),
    REHIRE("返聘");

    private final String value;

    AttendWhiteListTypeEnum(String value) {
        this.value = value;
    }
}
