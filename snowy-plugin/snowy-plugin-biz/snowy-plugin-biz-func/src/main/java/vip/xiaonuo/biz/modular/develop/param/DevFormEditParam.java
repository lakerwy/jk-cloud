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
package vip.xiaonuo.biz.modular.develop.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.biz.modular.develop.entity.DevFormItem;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 需求单 - 发起流程编辑参数
 *
 * @author yong.ye
 * @date  2024/05/27 16:08
 **/
@Getter
@Setter
public class DevFormEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 申请人单位：集团内部|分公司|外部 */
    @ApiModelProperty(value = "申请人单位：集团内部|分公司|外部", position = 2)
    private String applyUnit;

    /** 申请人部门 */
    @ApiModelProperty(value = "申请人部门", position = 3)
    private String applyDept;

    /** 申请人 */
    @ApiModelProperty(value = "申请人", position = 4)
    private String actionUser;

    /** 需求标题 */
    @ApiModelProperty(value = "需求标题", position = 5)
    private String applyName;

    /** 需求描述 */
    @ApiModelProperty(value = "需求描述", position = 6)
    private String applyDesc;

    /** 需求附件 */
    @ApiModelProperty(value = "需求附件", position = 7)
    private String applyAttach;

    /** UI人数 */
    @ApiModelProperty(value = "UI人数", position = 8)
    private String uiNum;

    /** 产品人数 */
    @ApiModelProperty(value = "产品人数", position = 9)
    private String productNum;

    /** 前端人数 */
    @ApiModelProperty(value = "前端人数", position = 10)
    private String frontEndNum;

    /** 后端人数 */
    @ApiModelProperty(value = "后端人数", position = 11)
    private String backEndNum;

    /** 测试人数 */
    @ApiModelProperty(value = "测试人数", position = 12)
    private String testNum;

    /** 实施人数 */
    @ApiModelProperty(value = "实施人数", position = 13)
    private String implementNum;

    /** 预计开始时间 */
    @ApiModelProperty(value = "预计开始时间", position = 14)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date estimatedStartTime;

    /** 预计天数 */
    @ApiModelProperty(value = "预计天数", position = 15)
    private String daysNum;

    /** 第三方服务 */
    @ApiModelProperty(value = "第三方服务", position = 16)
    private String isNeedThird;

    /** 服务内容 */
    @ApiModelProperty(value = "服务内容", position = 17)
    private String thirdContent;

    /** 服务成本（元） */
    @ApiModelProperty(value = "服务成本（元）", position = 18)
    private BigDecimal thirdCost;

    /** 预估成本（元） */
    @ApiModelProperty(value = "预估成本（元）", position = 19)
    private BigDecimal estimagedCost;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 20)
    private String memo;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 28)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 29)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 30)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 31)
    private String status;


    /** 需求拆解 - 集合 */
    @ApiModelProperty(value = "需求拆解 - 集合", position = 32)
    private List<DevFormItem> formItemList;

}
