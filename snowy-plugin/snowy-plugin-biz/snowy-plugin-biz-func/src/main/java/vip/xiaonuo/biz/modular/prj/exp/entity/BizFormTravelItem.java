package vip.xiaonuo.biz.modular.prj.exp.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 费用报销（二期）- 差旅费用报销单 - 报销明细实体
 *
 * @author yong.ye
 * @date  2025/02/24 15:19
 **/
@Getter
@Setter
@TableName("BIZ_FORM_TRAVEL_ITEM")
public class BizFormTravelItem {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 日期 */
    @ApiModelProperty(value = "日期", position = 2)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date optDate;

    /** 起点 */
    @ApiModelProperty(value = "起点", position = 3)
    private String pointFrom;

    /** 终点 */
    @ApiModelProperty(value = "终点", position = 4)
    private String pointTo;

    /** 交通工具 */
    @ApiModelProperty(value = "交通工具", position = 5)
    private String goType;

    /** 交通费 */
    @ApiModelProperty(value = "交通费", position = 6)
    private BigDecimal jtAmount;

    /** 住宿费 */
    @ApiModelProperty(value = "住宿费", position = 7)
    private BigDecimal zsAmount;

    /** 差旅补贴 */
    @ApiModelProperty(value = "差旅补贴", position = 8)
    private BigDecimal clbtAmount;

    /** 其他 */
    @ApiModelProperty(value = "其他", position = 9)
    private BigDecimal otherAmount;

    /** 差旅费用报销单id(BIZ_FORM_TRAVEL.ID) */
    @ApiModelProperty(value = "差旅费用报销单id(BIZ_FORM_TRAVEL.ID)", position = 10)
    private String formTravelId;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 11)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

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
}
