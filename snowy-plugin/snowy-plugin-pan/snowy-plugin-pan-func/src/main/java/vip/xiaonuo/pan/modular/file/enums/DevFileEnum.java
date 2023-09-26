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
package vip.xiaonuo.pan.modular.file.enums;

import lombok.Getter;

/**
 * 文件主表枚举
 *
 * @author mayike
 * @date  2023/08/28 10:09
 **/
@Getter
public enum DevFileEnum {

    /** 顶级根目录 */
    NO_HAVE(0, "文件不存在。---新上传"),

    ING_HAVE(1, "该文件上传了一部分。---续传"),

    IS_HAVE(2, "该文件已上传。---秒传");

    private final  Integer level;

    private final String value;

    DevFileEnum(Integer level, String value) {
        this.level = level;
        this.value = value;
    }
}
