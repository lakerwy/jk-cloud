package vip.xiaonuo.oa.modular.attendance.holiday.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 假期余额管理表实体
 *
 * @author mayike
 * @date  2024/07/05 10:57
 **/
@Getter
@Setter
@TableName("OA_ATTN_HOLIDAY_BALANCE")
public class HolidayBalance {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 员工ID */
    @ApiModelProperty(value = "员工ID", position = 2)
    private String userId;

    @ApiModelProperty(value = "年份", position = 3)
    private int year;

    /** 假期类型ID */
    @ApiModelProperty(value = "假期类型ID", position = 4)
    private String holidayTypeId;

    /** 假期余额 */
    @ApiModelProperty(value = "假期余额", position = 5)
    private BigDecimal balance;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 6)
    private String remark;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 7)
    private String extJson;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 8)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人ID */
    @ApiModelProperty(value = "创建人ID", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
