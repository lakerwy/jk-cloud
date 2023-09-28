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
package vip.xiaonuo.biz.modular.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-员工绩效考核-季度评分审批流程编辑参数
 *
 * @author yong.ye
 * @date  2023/09/19 08:45
 **/
@Getter
@Setter
public class BizEmpPerformanceEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
//    @NotBlank(message = "id不能为空")
    private String id;

    /** SN */
    @ApiModelProperty(value = "SN", position = 2)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 3)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 4)
    private String processInstanceId;

    /** 用户Id */
    @ApiModelProperty(value = "用户Id", position = 5)
    private String userId;

    /** 用户名 */
    @ApiModelProperty(value = "用户名", position = 6)
    private String userName;

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 7)
    private String orgId;

    /** 年度(如：2023) */
    @ApiModelProperty(value = "年度(如：2023)", position = 8)
    private String year;

    /** 季度(如：2) */
    @ApiModelProperty(value = "季度(如：2)", position = 9)
    private String season;

    /** 员工季度任务Json */
    @ApiModelProperty(value = "员工季度任务Json", position = 10)
    private String empTaskJson;

    /** 员工季度表现Json */
    @ApiModelProperty(value = "员工季度表现Json", position = 11)
    private String empExpressionJson;

    /** 合计-员工季度任务-自评 */
    @ApiModelProperty(value = "合计-员工季度任务-自评", position = 12)
    private Double empTaskSelfScore;

    /** 合计-员工季度表现-自评 */
    @ApiModelProperty(value = "合计-员工季度表现-自评", position = 13)
    private Double empExpressionSelfScore;

    /** 合计-员工季度任务-评分 */
    @ApiModelProperty(value = "合计-员工季度任务-评分", position = 14)
    private Double empTaskScore;

    /** 合计-员工季度表现-评分 */
    @ApiModelProperty(value = "合计-员工季度表现-评分", position = 15)
    private Double empExpressionScore;

    /** 员工季度-评估得分 */
    @ApiModelProperty(value = "员工季度-评估得分", position = 16)
    private Double empSeasonScore;

    /** 员工季度-绩效等级 */
    @ApiModelProperty(value = "员工季度-绩效等级", position = 17)
    private String empSeasonLevel;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 23)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 24)
    private String extJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 25)
    private String memo;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 6)
    private String dataScopeId;
}
