/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.sys.modular.hr.salary.enums;

import lombok.Getter;

/**
 * 工资系统-相关数据导入临时表枚举 - 导入类型
 *
 * @author yong.ye
 * @date  2024/02/26 09:31
 **/
@Getter
public enum SalaryTypeEnum {

    /** 月工资标准 */
    STANDARD("月工资标准"),
    /** 月度考勤 */
    ATTEND("月度考勤"),
    /** 注册补贴 */
    REGISTER_SUBSIDY("注册补贴"),
    /** 值班补贴 */
    DUTY_SUBSIDY("值班补贴"),
    /** 讲师津贴 */
    TEACHER_SUBSIDY("讲师津贴"),
    /** 外业补助 */
    OUTER_SUBSIDY("外业补助"),
    /** 社保 */
    INS("社保"),
    /** 公积金 */
    GJJ("公积金"),
    /** 专项扣除 */
    TAX("专项扣除"),
    /** 工龄工资 */
    SENIORITY("工龄工资"),
    /** 生日福利 */
    BIRTHDAY("生日福利"),
    /** 员工福利 */
    EMP_BONUS("员工福利"),
    /** 已确认的工资 - 员工工资 */
    EMP_SALARY("员工工资");

    private final String value;

    SalaryTypeEnum(String value) {
        this.value = value;
    }
}
