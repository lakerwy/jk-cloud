package vip.xiaonuo.sys.modular.hr.emp.enums;

import lombok.Getter;

/**
 * 员工月度考勤(钉钉)枚举
 *
 * @author pzh
 * @date  2025/02/12 15:17
 **/
@Getter
public enum HrEmpAttendDingdingEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    HrEmpAttendDingdingEnum(String value) {
        this.value = value;
    }
}
