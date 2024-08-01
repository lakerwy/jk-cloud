package vip.xiaonuo.biz.modular.prj.expense.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务管理 - 费用报销 - 财务收单编码库实体
 *
 * @author yong.ye
 * @date  2024/08/01 11:21
 **/
@Getter
@Setter
@TableName("BIZ_FIN_CODE")
public class BizFinCode {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 公司 */
    @ApiModelProperty(value = "公司", position = 2)
    private String company;

    /** 公司码 */
    @ApiModelProperty(value = "公司码", position = 3)
    private String codePre;

    /** 年 */
    @ApiModelProperty(value = "年", position = 4)
    private String codeYear;

    /** 月 */
    @ApiModelProperty(value = "月", position = 5)
    private String codeMonth;

    /** 单据顺序号 */
    @ApiModelProperty(value = "单据顺序号", position = 6)
    private String codeNo;

    /** 最终形成的编码 */
    @ApiModelProperty(value = "最终形成的编码", position = 7)
    private String code;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 8)
    private String tenantId;

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

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 15)
    private String extJson;
}
