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
package vip.xiaonuo.oa.modular.attendance.leave.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请假记录表添加参数
 *
 * @author mayike
 * @date  2024/06/24 16:55
 **/
@Getter
@Setter
public class LeaveAddParam {

    /** 请假人ID */
    @ApiModelProperty(value = "请假人ID", position = 3)
    private String userId;

    /** 请假开始日期 */
    @ApiModelProperty(value = "请假开始日期", position = 4)
    private Date startDate;

    /** 请假结束日期 */
    @ApiModelProperty(value = "请假结束日期", position = 5)
    private Date endDate;

    /** 请假类型 */
    @ApiModelProperty(value = "请假类型", position = 6)
    private String leaveType;

    /** 请假原因 */
    @ApiModelProperty(value = "请假原因", position = 7)
    private String reason;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 8)
    private String attments;

}
