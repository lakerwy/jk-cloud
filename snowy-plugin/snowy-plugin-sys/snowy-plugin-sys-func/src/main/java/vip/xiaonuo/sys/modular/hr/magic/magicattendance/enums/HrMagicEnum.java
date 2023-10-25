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
package vip.xiaonuo.sys.modular.hr.magic.magicattendance.enums;

import lombok.Getter;

/**
 * 魔点请求参数配置表枚举
 *
 * @author ljy
 * @date  2023/10/18 16:44
 **/
@Getter
public enum HrMagicEnum {

    /** 测试 */
    GET_APP_TOKEN("getAppToken"),
    SUCESS_CODE("0"),
    SUCESS_INFO("操作成功"),
    FAIL_CODE("1"),
    FAIL_INFO("操作失败"),
    MAGIC_MEMBER_CREATE("magicMemberCreate"),
    MAGIC_MENBER_UPDATE("magicMemberUpdate"),
    MAGIC_MEMBER_DELTE("deleteVerifyImg"),
    MAGIC_MEMBER_UPDATE_IMAGE("updateVerifyImage"),
    MAGIC_MEMBER_LIST("magicMemberList"),
    JK_FAIL_CODE("1"),
    JK_FAIL_INFO("建科云图像上传失败！"),
    JK_USER_FAIL_INFO("建科人员信息未同步到魔点，无法上传人脸识别图片！");

    private final String value;

    HrMagicEnum(String value) {
        this.value = value;
    }
}
