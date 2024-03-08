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
 * 工资系统-工龄工资|生日福利实体
 *
 * @author yong.ye
 * @date  2024/02/26 09:34
 **/
@Getter
@Setter
@TableName("HR_SALARY_PAY")
public class SalaryPay {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 福利大类：工龄工资|员工福利 */
    @ApiModelProperty(value = "福利大类：工龄工资|员工福利", position = 2)
    private String type;

    /** 福利小类：结婚|嘉奖|生日福利 */
    @ApiModelProperty(value = "福利小类：结婚|嘉奖|生日福利", position = 3)
    private String itemType;

    /** 【外键】发放工资员工Id：HR_SALARY_STANDARD.ID */
    @ApiModelProperty(value = "【外键】发放工资员工Id：HR_SALARY_STANDARD.ID", position = 4)
    private String standardId;

    /** 转正工龄 */
    @ApiModelProperty(value = "转正工龄", position = 15)
    private String positiveAge;

    /** 本月工龄工资|生日福利（元） */
    @ApiModelProperty(value = "本月工龄工资|生日福利（元）", position = 17)
    private BigDecimal amount;

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

    /** 留痕备注 */
    @ApiModelProperty(value = "留痕备注", position = 100)
    private String note;
}
