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
package vip.xiaonuo.bp.modular.recruit.supplier.enums;

import lombok.Getter;

/**
 * 招采管理 - 来源类型 来源类型 自行申报=2,内部推荐=1 枚举
 *
 * @author yong.ye
 * @date  2024/05/07 16:36
 **/
@Getter
public enum BpSourceEnum {

    /** 自行申报 */
    VOLUNTARILY("2"),
    /** 内部推荐 */
    INNER("1");

    private final String value;

    BpSourceEnum(String value) {
        this.value = value;
    }
}
