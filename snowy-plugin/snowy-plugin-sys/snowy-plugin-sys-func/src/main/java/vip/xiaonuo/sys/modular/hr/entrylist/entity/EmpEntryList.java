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
package vip.xiaonuo.sys.modular.hr.entrylist.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * 入职名单实体
 *
 * @author yong.ye
 * @date  2023/07/31 09:25
 **/
@Getter
@Setter
@TableName("EMP_ENTRY_LIST")
public class EmpEntryList {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 2)
    private String tenantId;

    /** 登记日期 */
    @ApiModelProperty(value = "登记日期", position = 3)
    private Date optDate;

    /** 入职员工 */
    @ApiModelProperty(value = "入职员工", position = 4)
    private String emp;

    /** 入职部门 */
    @ApiModelProperty(value = "入职部门", position = 5)
    private String dept;

    /** 入职职位 */
    @ApiModelProperty(value = "入职职位", position = 6)
    private String position;

    /** 入职日期 */
    @ApiModelProperty(value = "入职日期", position = 7)
    private Date hireDate;

    /** 联系方式 */
    @ApiModelProperty(value = "联系方式", position = 8)
    private String phone;

    /** 办公室|工位 */
    @ApiModelProperty(value = "办公室|工位", position = 9)
    private String office;

    /** 办公设备 */
    @ApiModelProperty(value = "办公设备", position = 10)
    private String officeEquipment;

    /** 办公用品 */
    @ApiModelProperty(value = "办公用品", position = 11)
    private String officeSupply;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 12)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
