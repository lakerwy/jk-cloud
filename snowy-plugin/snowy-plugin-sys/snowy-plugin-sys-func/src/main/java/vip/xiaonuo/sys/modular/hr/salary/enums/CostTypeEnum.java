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
 * 工资系统 - 成本划分 - 月度个人统计枚举
 *
 * @author yong.ye
 * @date  2024/06/19 15:06
 **/
@Getter
public enum CostTypeEnum {

    /** 各类费用 - 成本归属配置 类别 **/
    /** 工资 */
    GZ("工资"),
    /** 社保 */
    INS("社保"),
    /** 公积金 */
    GJJ("公积金"),
    /** 注册补贴 */
    REGISTER("注册补贴"),
    /** 员工福利 */
    WELFARE("员工福利"),
    /** 外业补贴 */
    OUTER("外业补贴"),

    /** 成本归属分析： 统计维度 */
    /** 汇总维度 */
    REPORT_PER("汇总维度"),

    /** 社保维度 */
    REPORT_INS("社保维度"),

    /** 公积金维度 */
    REPORT_GJJ("公积金维度");

    private final String value;

    CostTypeEnum(String value) {
        this.value = value;
    }
}
