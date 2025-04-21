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
package vip.xiaonuo.sys.modular.company.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 发送年报提醒参数
 *
 * @author pzh
 * @date  2025/04/21 10:04
 **/
@Getter
@Setter
public class SendMsgParam {

    @ApiModelProperty(value = "提醒标题（例如：年报提醒）", required = true, position = 1)
    @NotBlank(message = "提醒标题不能为空")
    private String title;

    @ApiModelProperty(value = "提醒内容", required = true, position = 2)
    @NotBlank(message = "提醒内容不能为空")
    private String content;

    @ApiModelProperty(value = "用户Id集合", required = true,  position = 3)
    @NotEmpty(message = "用户Id集合不能为空")
    private List<String> userIdList;

}
