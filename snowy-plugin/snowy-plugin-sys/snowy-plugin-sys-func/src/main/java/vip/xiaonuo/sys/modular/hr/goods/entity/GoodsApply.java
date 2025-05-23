package vip.xiaonuo.sys.modular.hr.goods.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 物资领用申请实体
 *
 * @author yy
 * @date  2025/05/23 14:34
 **/
@Getter
@Setter
@TableName("HR_GOODS_APPLY")
public class GoodsApply {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 领用部门Id */
    @ApiModelProperty(value = "领用部门Id", position = 2)
    private String deptId;

    /** 领用部门 */
    @ApiModelProperty(value = "领用部门", position = 3)
    private String deptName;

    /** 领用人Id */
    @ApiModelProperty(value = "领用人Id", position = 4)
    private String optId;

    /** 领用人 */
    @ApiModelProperty(value = "领用人", position = 5)
    private String optName;

    /** 申请时间 */
    @ApiModelProperty(value = "申请时间", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optTime;

    /** 领用事由 */
    @ApiModelProperty(value = "领用事由", position = 7)
    private String descMemo;

    /** 合计-总价 */
    @ApiModelProperty(value = "合计-总价", position = 8)
    private BigDecimal totalPrice;

    /** 领取确认 */
    @ApiModelProperty(value = "领取确认", position = 9)
    private String confirmType;

    /** 实际领用日期 */
    @ApiModelProperty(value = "实际领用日期", position = 10)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date confirmTime;

    /** 退回人Id */
    @ApiModelProperty(value = "退回人Id", position = 11)
    private String backId;

    /** 退回人 */
    @ApiModelProperty(value = "退回人", position = 12)
    private String backName;

    /** 核收人Id */
    @ApiModelProperty(value = "核收人Id", position = 13)
    private String verifyId;

    /** 核收人 */
    @ApiModelProperty(value = "核收人", position = 14)
    private String verifyName;

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

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 19)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 20)
    private String attachs;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 21)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 22)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 23)
    private String dataScopeId;

    /** SN */
    @ApiModelProperty(value = "SN", position = 24)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 25)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 26)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 27)
    private String status;

    /** 物资清单JSON */
    @ApiModelProperty(value = "物资清单JSON", position = 200)
    private String goodsApplyJson;

    /** 物资退回清单JSON */
    @ApiModelProperty(value = "物资退回清单JSON", position = 200)
    private String goodsBackJson;


    /** 申领物资名称 */
    @ApiModelProperty(value = "申领物资名称", position = 100)
    @TableField(exist = false)
    private String goodsApplyStr;

    /** 退回物资名称 */
    @ApiModelProperty(value = "退回物资名称", position = 100)
    @TableField(exist = false)
    private String goodsBackStr;
}
