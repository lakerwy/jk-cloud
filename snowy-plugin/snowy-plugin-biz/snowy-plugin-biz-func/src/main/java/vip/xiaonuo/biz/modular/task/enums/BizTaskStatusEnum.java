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
package vip.xiaonuo.biz.modular.task.enums;

import lombok.Getter;
import vip.xiaonuo.common.enums.FlwStatusEnum;

/**
 * 业务表-绩效考核-任务表枚举
 *
 * @author yong.ye
 * @date  2023/08/04 14:20
 **/
@Getter
public enum BizTaskStatusEnum {

    //参数状态
    ACTIVE_STATUS("0", "审批中"),

    COMPLETED_STATUS("1", "审批通过"),

    REVOKE_STATUS("2", "已撤回"),

    REJECT_STATUS("3", "已拒绝"),

    END_STATUS("4", "已终止"),
    ;

    private final String value;

    private final String name;

    BizTaskStatusEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String fromValue(String value) {
        for (BizTaskStatusEnum enumConstant : BizTaskStatusEnum.values()) {
            if (enumConstant.value.equals(value)) {
                return enumConstant.getName();
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
