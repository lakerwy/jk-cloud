package vip.xiaonuo.sys.modular.hr.techAchive.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 查新实体
 *
 * @author pzh
 * @date  2024/10/18 08:44
 **/
@Getter
@Setter
@TableName("NOVELTY_SEARCH")
public class NoveltySearch {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 持有单位 */
    @ApiModelProperty(value = "持有单位", position = 2)
    private String holdingOrg;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 2)
    private String prjName;

    /** 报告编号 */
    @ApiModelProperty(value = "报告编号", position = 3)
    private String reportNo;

    /** 查新目的 */
    @ApiModelProperty(value = "查新目的", position = 4)
    private String purpose;

    /** 委托日期 */
    @ApiModelProperty(value = "委托日期", position = 5)
    private Date entrustDate;

    /** 完成日期 */
    @ApiModelProperty(value = "完成日期", position = 6)
    private Date finishDate;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 7)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 9)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

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

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
