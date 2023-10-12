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
package vip.xiaonuo.sys.modular.hr.userApplyHoliday.enums;

import lombok.Getter;

/**
 * 请假申请表枚举
 *
 * @author ljy
 * @date  2023/09/22 17:05
 **/
@Getter
public enum HrUserApplyHolidayEnum {

    /** 申请类型：1 事假，2 调休，3 年假 */
    NOT_LIMIT("-1"),/** 无限制假期 */
    PERSONAL_LEAVE("1"),
    COMPENSATORY_LEAVE("2"),
    NOT_DATA("1"),//无假期数据，请先添加！
    NOT_COMPENSATORY_LEAVE_DATA("2"),//申请时间超过剩余调休时间，请重新申请！
    ANNUAL_LEAVE("3"),
    NOT_ANNUAL_LEAVE_DATA("3"),//申请时间超过剩余年假时间，请重新申请！
    SUCCESS("0"),/** 成功 */
    FAIL("1");/** 失败 */

    private final String value;

    HrUserApplyHolidayEnum(String value) {
        this.value = value;
    }
}
