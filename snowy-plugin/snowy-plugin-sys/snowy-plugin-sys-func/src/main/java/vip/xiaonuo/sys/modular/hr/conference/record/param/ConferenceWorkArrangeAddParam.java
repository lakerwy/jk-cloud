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
package vip.xiaonuo.sys.modular.hr.conference.record.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工作部署添加参数
 *
 * @author ljy
 * @date  2023/11/28 16:31
 **/
@Getter
@Setter
public class ConferenceWorkArrangeAddParam {

    /** 部署事项 */
    @ApiModelProperty(value = "部署事项", position = 2)
    private String workArrange;

    /** 完成时间 */
    @ApiModelProperty(value = "完成时间", position = 3)
    private Date finishTime;

    /** 责任部门ID */
    @ApiModelProperty(value = "责任部门ID", position = 4)
    private String dutyOrgId;

    /** 责任部门名称 */
    @ApiModelProperty(value = "责任部门名称", position = 5)
    private String dutyOrgName;

    /** 责任人ID */
    @ApiModelProperty(value = "责任人ID", position = 6)
    private String dutyUserId;

    /** 责任人名称 */
    @ApiModelProperty(value = "责任人名称", position = 7)
    private String dutyUserName;

    /** 工作进展 */
    @ApiModelProperty(value = "工作进展", position = 8)
    private String workEvolve;

    /** 是否完成,1 完成 */
    @ApiModelProperty(value = "是否完成,0 未完成，1 完成", position = 9)
    private Integer isWorkEnd;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 10)
    private String extJson;

}
