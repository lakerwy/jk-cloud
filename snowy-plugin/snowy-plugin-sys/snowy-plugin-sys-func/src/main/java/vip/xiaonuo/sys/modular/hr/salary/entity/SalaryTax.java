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
 * 工资系统-专项扣除实体
 *
 * @author yong.ye
 * @date  2024/02/26 09:38
 **/
@Getter
@Setter
@TableName("HR_SALARY_TAX")
public class SalaryTax {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 【外键】发放工资员工Id：HR_SALARY_STANDARD.ID */
    @ApiModelProperty(value = "【外键】发放工资员工Id：HR_SALARY_STANDARD.ID", position = 3)
    private String standardId;

    /** 所属单位 */
    @ApiModelProperty(value = "所属单位", position = 10)
    private String unitName;

    /** 合计 */
    @ApiModelProperty(value = "合计", position = 11)
    private BigDecimal amount;

    /** 赡养老人 */
    @ApiModelProperty(value = "赡养老人", position = 12)
    private BigDecimal taxSy;

    /** 子女教育 */
    @ApiModelProperty(value = "子女教育", position = 13)
    private BigDecimal taxZn;

    /** 房贷利息 */
    @ApiModelProperty(value = "房贷利息", position = 14)
    private BigDecimal taxFd;

    /** 住房租金 */
    @ApiModelProperty(value = "住房租金", position = 15)
    private BigDecimal taxZf;

    /** 继续教育 */
    @ApiModelProperty(value = "继续教育", position = 16)
    private BigDecimal taxJy;

    /** 婴幼儿照护 */
    @ApiModelProperty(value = "婴幼儿照护", position = 17)
    private BigDecimal taxZh;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 18)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 19)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 20)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 21)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 22)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 23)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 24)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 25)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
