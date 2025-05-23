package vip.xiaonuo.sys.modular.hr.goods.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物资领用申请（附表） - 物资清单+退回清单 实体
 *
 * @author yy
 * @date  2025/05/23 14:35
 **/
@Getter
@Setter
@TableName("HR_GOODS_ITEM")
public class GoodsItem {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 物资清单|退回清单 */
    @ApiModelProperty(value = "物资清单|退回清单", position = 2)
    private String type;

    /** 物资名称|退回物资名称 */
    @ApiModelProperty(value = "物资名称|退回物资名称", position = 3)
    private String goodName;

    /** 数量 */
    @ApiModelProperty(value = "数量", position = 4)
    private BigDecimal goodNum;

    /** 单价 */
    @ApiModelProperty(value = "单价", position = 5)
    private BigDecimal goodPrice;

    /** 总价 */
    @ApiModelProperty(value = "总价", position = 6)
    private BigDecimal goodTotal;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String goodMemo;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 13)
    private String attachs;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 14)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 15)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 物资领用申请id */
    @ApiModelProperty(value = "物资领用申请id", position = 16)
    private String goodsApplyId;
}
