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
package vip.xiaonuo.dev.modular.dingThirdData.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 三方用户编辑参数
 *
 * @author ljy
 * @date  2024/03/08 08:39
 **/
@Getter
@Setter
public class AuthThirdUserEditParam {

    /** ID */
    @ApiModelProperty(value = "ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 三方用户id */
    @ApiModelProperty(value = "三方用户id", position = 3)
    private String thirdId;

    /** 日程组织者的unionId */
    @ApiModelProperty(value = "日程组织者的unionId", position = 4)
    private String unionId;

    /** 系统用户id */
    @ApiModelProperty(value = "系统用户id", position = 5)
    private String userId;

    /** 头像 */
    @ApiModelProperty(value = "头像", position = 6)
    private String avatar;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 7)
    private String name;

    /** 昵称 */
    @ApiModelProperty(value = "昵称", position = 8)
    private String nickname;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 9)
    private String gender;

    /** 分类(DingDing：钉钉用户Id，WeChat_ECard：微信电子名片小程序openId) */
    @ApiModelProperty(value = "分类(DingDing：钉钉用户Id，WeChat_ECard：微信电子名片小程序openId)", position = 10)
    private String category;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 11)
    private String extJson;

}
