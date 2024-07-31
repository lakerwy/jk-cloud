package vip.xiaonuo.biz.modular.prj.expense.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 费用明细实体
 *
 * @author yong.ye
 * @date  2024/07/30 15:31
 **/
@Getter
@Setter
@TableName("BIZ_EXP_ITEM")
public class BizExpItem {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 2)
    private String no;

    /** 明细科目Id */
    @ApiModelProperty(value = "明细科目Id", position = 3)
    private String itemId;

    /** 明细科目 */
    @ApiModelProperty(value = "明细科目", position = 4)
    private String item;

    /** 摘要 */
    @ApiModelProperty(value = "摘要", position = 5)
    private String abstractDesc;

    /** 金额（含税） */
    @ApiModelProperty(value = "金额（含税）", position = 6)
    private String amount;

    /** 出差开始时间 */
    @ApiModelProperty(value = "出差开始时间", position = 7)
    private String startDate;

    /** 出差结束时间 */
    @ApiModelProperty(value = "出差结束时间", position = 8)
    private String endDate;

    /** 地址-起 */
    @ApiModelProperty(value = "地址-起", position = 9)
    private String startPoint;

    /** 地址-止 */
    @ApiModelProperty(value = "地址-止", position = 10)
    private String endPoint;

    /** 交通工具 */
    @ApiModelProperty(value = "交通工具", position = 11)
    private String traffic;

    /** 交通工具金额 */
    @ApiModelProperty(value = "交通工具金额", position = 12)
    private String trafficAmount;

    /** 其他项目 */
    @ApiModelProperty(value = "其他项目", position = 13)
    private String otherItem;

    /** 交通工具金额 */
    @ApiModelProperty(value = "交通工具金额", position = 14)
    private String otherAmount;

    /** 补贴项目 */
    @ApiModelProperty(value = "补贴项目", position = 15)
    private String subsidyItem;

    /** 补贴金额 */
    @ApiModelProperty(value = "补贴金额", position = 16)
    private String subsidyAmount;

    /** 小计 */
    @ApiModelProperty(value = "小计", position = 17)
    private String subTotal;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 18)
    private String memo;

    /** 报销申请单Id-外键 */
    @ApiModelProperty(value = "报销申请单Id-外键", position = 19)
    private String bizExpApplyId;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 20)
    private String attach;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 21)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 22)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 23)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 24)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 25)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 26)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 27)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 28)
    private String extJson;
}
