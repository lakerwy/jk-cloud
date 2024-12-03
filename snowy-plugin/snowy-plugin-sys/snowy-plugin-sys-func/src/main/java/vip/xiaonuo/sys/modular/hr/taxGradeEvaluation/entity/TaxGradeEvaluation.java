package vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 纳税等级评价实体
 *
 * @author pzh
 * @date  2024/11/27 14:45
 **/
@Getter
@Setter
@TableName("TAX_GRADE_EVALUATION")
public class TaxGradeEvaluation {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    /** 纳税人名称 */
    @ApiModelProperty(value = "纳税人名称", position = 3)
    private String taxpayerName;

    /** 统一社会信用码（纳税人识别号） */
    @ApiModelProperty(value = "统一社会信用码（纳税人识别号）", position = 4)
    private String unifiedSocialCreditCode;

    /** 纳税信用评价得分 */
    @ApiModelProperty(value = "纳税信用评价得分", position = 5)
    private String taxCreditScore;

    /** 年度评价结果 */
    @ApiModelProperty(value = "年度评价结果", position = 6)
    private String annualEvaluationResult;

    /** 出具时间 */
    @ApiModelProperty(value = "出具时间", position = 7)
    private Date issueDate;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 8)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 10)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 15)
    private String tenantId;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
