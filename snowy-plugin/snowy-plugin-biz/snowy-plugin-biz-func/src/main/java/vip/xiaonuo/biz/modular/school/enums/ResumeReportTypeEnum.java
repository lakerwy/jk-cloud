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
package vip.xiaonuo.biz.modular.school.enums;

import lombok.Getter;

/**
 * 系统字典-自定义枚举
 *
 * @author yong.ye
 * @date  2023/11/15 08:51
 **/
@Getter
public enum ResumeReportTypeEnum {

    /** 简历收集-柱状图 */
    collect("1"),

    /** 专业类别分析-饼状图 */
    classification("2"),

    /** 有效简历学历层次分析-柱状图 */
    levels("3"),

    /** 有效简历&初筛合格-柱状图 */
    filter("4"),

    /** 到面率&通过率-折线图 */
    passing("5"),

    /** offer拒绝率-折线图 */
    refuse("6"),

    /** 面试通过人数及接受offer人数-柱状图 */
    passPerson("7"),

    /** offer拒绝原因-饼状图 */
    refuseReson("8");

    private final String value;

    ResumeReportTypeEnum(String value) {
        this.value = value;
    }
}
