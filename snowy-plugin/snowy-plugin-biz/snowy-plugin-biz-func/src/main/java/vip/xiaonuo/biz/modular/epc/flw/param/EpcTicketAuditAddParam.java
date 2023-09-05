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
package vip.xiaonuo.biz.modular.epc.flw.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * EPC-罚单审批添加参数
 *
 * @author yong.ye
 * @date  2023/09/05 15:00
 **/
@Getter
@Setter
public class EpcTicketAuditAddParam {

    /** SN */
    @ApiModelProperty(value = "SN", position = 3)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 4)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 5)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 6)
    private String status;

    /** 项目ID(外键) */
    @ApiModelProperty(value = "项目ID(外键)", position = 7)
    private String projectId;

    /** 处罚单位 */
    @ApiModelProperty(value = "处罚单位", position = 8)
    private String penaltyUnit;

    /** 处罚时间 */
    @ApiModelProperty(value = "处罚时间", position = 9)
    private Date penaltyTime;

    /** 处罚额度 */
    @ApiModelProperty(value = "处罚额度", position = 10)
    private String penaltyAmount;

    /** 处罚内容 */
    @ApiModelProperty(value = "处罚内容", position = 11)
    private String penaltyContent;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

}
