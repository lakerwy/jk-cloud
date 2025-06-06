package vip.xiaonuo.biz.modular.prj.fin.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务数据汇总台账 - 转入签字费实体
 *
 * @author yy
 * @date  2025/06/05 17:27
 **/
@Getter
@Setter
@TableName("FIN_DATA_SIGN")
public class FinDataSign {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 合同Id */
    @ApiModelProperty(value = "合同Id", position = 2)
    private String contractId;

    /** 合同编码 */
    @ApiModelProperty(value = "合同编码", position = 3)
    private String contractNo;

    /** 转款单位id */
    @ApiModelProperty(value = "转款单位id", position = 4)
    private String unitId;

    /** 转款单位 */
    @ApiModelProperty(value = "转款单位", position = 5)
    private String unitName;

    /** 转款日期 */
    @ApiModelProperty(value = "转款日期", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date zkDate;

    /** 转款金额 */
    @ApiModelProperty(value = "转款金额", position = 7)
    private BigDecimal zkAmount;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 8)
    private String content;

    /** 留痕 */
    @ApiModelProperty(value = "留痕", position = 9)
    private String extJson;

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

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 14)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 15)
    private String attach;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 16)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 17)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 18)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
