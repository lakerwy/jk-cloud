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
package vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 考勤班次组配置表编辑参数
 *
 * @author ljy
 * @date  2023/09/21 15:55
 **/
@Getter
@Setter
public class HrUserAttendanceConfigGroupEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 班次名称 */
    @ApiModelProperty(value = "班次名称", position = 2)
    private String attendanceTypeName;

    /** 负责人ID */
    @ApiModelProperty(value = "负责人ID", position = 3)
    private String leadId;

    /** 负责人名称 */
    @ApiModelProperty(value = "负责人名称", position = 4)
    private String leadName;

    /** 班次应用人数 */
    @ApiModelProperty(value = "班次应用人数", position = 5)
    private Integer attendanceUserNum;

    /** 额外信息 */
    @ApiModelProperty(value = "额外信息", position = 6)
    private String extJson;

}
