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
package vip.xiaonuo.biz.modular.epc.safety.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * EPC-安全巡查编辑参数
 *
 * @author yong.ye
 * @date  2023/09/05 14:59
 **/
@Getter
@Setter
public class EpcSafetyPatrolEditParam {

    /** 主键Id */
    @ApiModelProperty(value = "主键Id", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 项目ID(外键) */
    @ApiModelProperty(value = "项目ID(外键)", position = 3)
    private String projectId;

    /** 巡检部位 */
    @ApiModelProperty(value = "巡检部位", position = 4)
    private String position;

    /** 巡检时间 */
    @ApiModelProperty(value = "巡检时间", position = 5)
    private Date patrolTime;

    /** 图片 */
    @ApiModelProperty(value = "图片", position = 6)
    private String images;

    /** 分部分项 */
    @ApiModelProperty(value = "分部分项", position = 7)
    private String items;

    /** 问题等级 */
    @ApiModelProperty(value = "问题等级", position = 8)
    private String level;

    /** 截止整改时间 */
    @ApiModelProperty(value = "截止整改时间", position = 9)
    private Date expirationTime;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

}
