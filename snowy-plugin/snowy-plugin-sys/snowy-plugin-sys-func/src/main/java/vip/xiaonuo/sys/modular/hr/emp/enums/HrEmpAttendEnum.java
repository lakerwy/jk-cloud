package vip.xiaonuo.sys.modular.hr.emp.enums;

import lombok.Getter;

/**
 * 员工月度考勤表 - 单独 - 非工资系统枚举
 *
 * @author yong.ye
 * @date  2024/11/06 16:53
 **/
@Getter
public enum HrEmpAttendEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    HrEmpAttendEnum(String value) {
        this.value = value;
    }
}
