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
package vip.xiaonuo.biz.modular.plan.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合约规划 - 申报（项目承包方）-收入&效益实体
 *
 * @author yong.ye
 * @date  2024/04/22 11:43
 **/
@Getter
@Setter
@TableName("BIZ_CONTRACT_UNIT")
public class ContractUnit {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 项目承包方 */
    @ApiModelProperty(value = "项目承包方", position = 2)
    private String prjUnit;

    /** 经营单位划分：内部牵头生产部门|内部协同生产部门 */
    @ApiModelProperty(value = "经营单位划分：内部牵头生产部门|内部协同生产部门", position = 3)
    private String deptType;

    /** 经营单位划分：部门名称 */
    @ApiModelProperty(value = "经营单位划分：部门名称", position = 4)
    private String deptName;

    /** 收入：产值划分(含税，元） */
    @ApiModelProperty(value = "收入：产值划分(含税，元）", position = 5)
    private BigDecimal output;

    /** 收入：产值划分比例（%） */
    @ApiModelProperty(value = "收入：产值划分比例（%）", position = 6)
    private Float outputRate;

    /** 效益：利润额(元） */
    @ApiModelProperty(value = "效益：利润额(元）", position = 7)
    private BigDecimal profit;

    /** 效益：利润率（%） */
    @ApiModelProperty(value = "效益：利润率（%）", position = 8)
    private Float profitRate;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 10)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 11)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 外键：BIZ_CONTRACT_PLAN.ID */
    @ApiModelProperty(value = "外键：BIZ_CONTRACT_PLAN.ID", position = 17)
    private String bizContractPlanId;
}
