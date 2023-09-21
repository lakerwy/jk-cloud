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
 * 业务表-员工绩效考核-季度任务表添加参数
 *
 * @author yong.ye
 * @date  2023/09/19 08:47
 **/
@Getter
@Setter
public class BizEmpTaskAddParam {

    /** 用户Id */
    @ApiModelProperty(value = "用户Id", position = 2)
    private String userId;

    /** 用户名 */
    @ApiModelProperty(value = "用户名", position = 3)
    private String userName;

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 4)
    private String orgId;

    /** 年度(如：2023) */
    @ApiModelProperty(value = "年度(如：2023)", position = 5)
    private String year;

    /** 季度(如:1) */
    @ApiModelProperty(value = "季度(如:1)", position = 6)
    private String season;

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 7)
    private String title;

    /** 目标 */
    @ApiModelProperty(value = "目标", position = 8)
    private String goal;

    /** 权重 */
    @ApiModelProperty(value = "权重", position = 9)
    private Double rate;

    /** 完成情况 */
    @ApiModelProperty(value = "完成情况", position = 10)
    private String complete;

    /** 自评分 */
    @ApiModelProperty(value = "自评分", position = 11)
    private Double selfScore;

    /** 评分 */
    @ApiModelProperty(value = "评分", position = 12)
    private Double score;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String memo;

    /** 外键（流程单主键Id） */
    @ApiModelProperty(value = "外键（流程单主键Id）", position = 19)
    private String flwId;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 20)
    private String status;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 6)
    private String dataScopeId;
}
