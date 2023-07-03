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
package vip.xiaonuo.biz.modular.crm.major.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CRM-图审专业编辑参数
 *
 * @author yong.ye
 * @date  2023/06/30 16:42
 **/
@Getter
@Setter
public class MajorEditParam {

    /** 主键ID（历史表Major.Id） */
    @ApiModelProperty(value = "主键ID（历史表Major.Id）", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 专业名称（历史表Major.MajorName） */
    @ApiModelProperty(value = "专业名称（历史表Major.MajorName）", position = 2)
    private String majorName;

    /** 专业编号（历史表Major.MajorNo） */
    @ApiModelProperty(value = "专业编号（历史表Major.MajorNo）", position = 3)
    private String majorNo;

}
