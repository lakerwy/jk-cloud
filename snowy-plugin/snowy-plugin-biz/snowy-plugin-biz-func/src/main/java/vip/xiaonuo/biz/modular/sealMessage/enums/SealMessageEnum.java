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
package vip.xiaonuo.biz.modular.sealMessage.enums;

import lombok.Getter;

/**
 * 印章基础信息表枚举
 *
 * @author hxw
 * @date  2024/05/17 08:55
 **/
@Getter
public enum SealMessageEnum {

    /** 获取token--ip+端口 */
    GETTOKENIPPOST("http://10.0.33.81:10006/api/token/getToken"),

    /** 账号key */
    GETTOKENACCOUNTKEY("restname"),

    /** 账号key */
    GETTOKENACCOUNTVALUE("qunje"),

    /** 账号key */
    GETTOKENPASSWORDKEY("password"),

    /** 取消部门推荐 */
    GETTOKENPASSWORDVALUE("qunje@123"),

    /** 授权-ip+端口 */
    SEALAUTHORIZEIPPOST("http://10.0.33.81:10006/api/item/sealAuthorize"),

    /** 获取印章信息--ip+端口 */
    SEALBASEIPPOST("http://10.0.33.81:10006/api/seal/queryAllSealBase"),

    SEALMESSAGETOKENKEY("SEALMESSAGE_");

    private final String value;

    SealMessageEnum(String value) {
        this.value = value;
    }
}
