package vip.xiaonuo.biz.modular.crm.prjValueAllocation.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值划分实体
 *
 * @author pzh
 * @date  2025/06/04 16:35
 **/
@Getter
@Setter
@TableName("BIZ_PRJ_VALUE_ALLOCATION")
public class PrjValueAllocation {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 主合同ID */
    @ApiModelProperty(value = "主合同ID", position = 2)
    private String hetongId;

    /** 主合同名称 */
    @ApiModelProperty(value = "主合同名称", position = 3)
    private String contractName;

    /** 主合同编号 */
    @ApiModelProperty(value = "主合同编号", position = 4)
    private String contractNo;

    /** 建科所占金额 */
    @ApiModelProperty(value = "建科所占金额", position = 5)
    private BigDecimal contractAmount;

    /** 行业分类 */
    @ApiModelProperty(value = "行业分类", position = 6)
    private String industryClassification;

    /** 项目类型 */
    @ApiModelProperty(value = "项目类型", position = 7)
    private String projectType;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 9)
    private String attach;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 14)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 15)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 17)
    private String status;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 18)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 19)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 20)
    private String processInstanceId;
}
