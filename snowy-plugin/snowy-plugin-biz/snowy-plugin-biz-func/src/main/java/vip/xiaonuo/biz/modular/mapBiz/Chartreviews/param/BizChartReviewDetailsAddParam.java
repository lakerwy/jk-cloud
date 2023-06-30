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
package vip.xiaonuo.biz.modular.mapBiz.Chartreviews.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-图审详情记录表添加参数
 *
 * @author mayike
 * @date  2023/06/30 16:39
 **/
@Getter
@Setter
public class BizChartReviewDetailsAddParam {

    /** 图审ID */
    @ApiModelProperty(value = "图审ID", position = 2)
    private String reviewId;

    /** OREDER_INDEX */
    @ApiModelProperty(value = "OREDER_INDEX", position = 3)
    private Integer orederIndex;

    /** 专业类别 */
    @ApiModelProperty(value = "专业类别", position = 4)
    private String majorName;

    /** 校审人 */
    @ApiModelProperty(value = "校审人", position = 5)
    private String leader;

    /** 审定次数 */
    @ApiModelProperty(value = "审定次数", position = 6)
    private Integer counts;

    /** 终审时间 */
    @ApiModelProperty(value = "终审时间", position = 7)
    private Date auditTiem;

    /** 当前状态 */
    @ApiModelProperty(value = "当前状态", position = 8)
    private String status;

}
