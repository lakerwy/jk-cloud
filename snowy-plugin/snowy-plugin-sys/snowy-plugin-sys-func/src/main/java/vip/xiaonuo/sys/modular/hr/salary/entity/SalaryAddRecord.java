/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.sys.modular.hr.salary.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工资系统-其他费用扣加-记录实体
 *
 * @author yong.ye
 * @date  2024/02/26 09:24
 **/
@Getter
@Setter
@TableName("HR_SALARY_ADD_RECORD")
public class SalaryAddRecord {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 外键（HR_SALARY_ADD.ID) */
    @ApiModelProperty(value = "外键（HR_SALARY_ADD.ID)", position = 2)
    private String addId;

    /** 字典类型（1 补发、2 补扣） */
    @ApiModelProperty(value = "字典类型（1 补发、2 补扣）", position = 3)
    private String dictType;

    /** 类别：税前|税后 */
    @ApiModelProperty(value = "类别：税前|税后", position = 3)
    private String itemType;

    /** 1 补发、2 补扣 栏目名称 */
    @ApiModelProperty(value = "1 补发、2 补扣 栏目名称", position = 4)
    private String dictName;

    /** 标准金额 */
    @ApiModelProperty(value = "标准金额", position = 5)
    private BigDecimal amount;

    /** 字典栏目描述 */
    @ApiModelProperty(value = "字典栏目描述", position = 5)
    private String dictDescription;

    /** 实际金额 */
    @ApiModelProperty(value = "实际金额", position = 6)
    private BigDecimal realAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 8)
    private String tenantId;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 9)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 33)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
