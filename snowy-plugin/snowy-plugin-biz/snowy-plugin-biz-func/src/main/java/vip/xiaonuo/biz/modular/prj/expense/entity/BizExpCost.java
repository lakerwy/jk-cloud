package vip.xiaonuo.biz.modular.prj.expense.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 成本一览表实体
 *
 * @author yong.ye
 * @date  2024/07/30 15:32
 **/
@Getter
@Setter
@TableName("BIZ_EXP_COST")
public class BizExpCost {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 2)
    private String no;

    /** 编号与合同名称 */
    @ApiModelProperty(value = "编号与合同名称", position = 2)
    private String prjNameNo;

    /** 项目编号 */
    @ApiModelProperty(value = "项目编号", position = 3)
    private String prjNo;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 4)
    private String prjName;

    /** 明细科目Id */
    @ApiModelProperty(value = "明细科目Id", position = 5)
    private String itemId;

    /** 明细科目 */
    @ApiModelProperty(value = "明细科目", position = 6)
    private String item;

    /** 摘要 */
    @ApiModelProperty(value = "摘要", position = 7)
    private String abstractDesc;

    /** 金额（含税） */
    @ApiModelProperty(value = "金额（含税）", position = 8)
    private String amount;

    /** 进项税额 */
    @ApiModelProperty(value = "进项税额", position = 9)
    private String tax;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

    /** 报销申请单Id-外键 */
    @ApiModelProperty(value = "报销申请单Id-外键", position = 11)
    private String bizExpApplyId;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 12)
    private String attach;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 13)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 14)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 18)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 19)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 20)
    private String extJson;
}
