package vip.xiaonuo.biz.modular.orgPerf.teamAss.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 班子考核实体
 *
 * @author pzh
 * @date  2025/06/17 16:23
 **/
@Getter
@Setter
@TableName("BIZ_TEAM_ASS")
public class BizTeamAss {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 考核年度（例如：2025） */
    @ApiModelProperty(value = "考核年度（例如：2025）", position = 2)
    private Integer assYear;

    /** 考核人ID */
    @ApiModelProperty(value = "考核人ID", position = 3)
    private String assessorId;

    /** 考核人姓名 */
    @ApiModelProperty(value = "考核人姓名", position = 4)
    private String assessorName;

    /** 加分小计 */
    @ApiModelProperty(value = "加分小计", position = 5)
    private BigDecimal addScore;

    /** 减分小计 */
    @ApiModelProperty(value = "减分小计", position = 6)
    private BigDecimal minusScore;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 8)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 10)
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

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 总分 */
    @ApiModelProperty(value = "总分", position = 13)
    @TableField(exist = false)
    private BigDecimal totalScore;
}
