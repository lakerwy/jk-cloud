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
package vip.xiaonuo.pan.modular.fold.enums;

import lombok.Getter;

/**
 * 文件主表枚举
 *
 * @author mayike
 * @date  2023/08/28 10:09
 **/
@Getter
public enum DevFoldEnum {

    /** 顶级根目录 */
    ROOT(0, "顶级根目录"),
    SELF_ROOT(1, "个人根目录"),
    GROUP_ROOT(2, "部门根目录"),

    SELF_FOLD_TYPE(0,"个人文件夹"),
    GROUP_FOLD_TYPE(1,"群组文件夹"),

    UNLOCK(0,"未锁定"),
    LOCK(1,"已锁定");

    private final  Integer level;

    private final String value;

    DevFoldEnum(Integer level, String value) {
        this.level = level;
        this.value = value;
    }
}
