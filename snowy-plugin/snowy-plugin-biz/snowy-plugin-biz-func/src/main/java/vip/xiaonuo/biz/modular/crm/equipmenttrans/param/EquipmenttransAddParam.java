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
package vip.xiaonuo.biz.modular.crm.equipmenttrans.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CRM-设备转移记录添加参数
 *
 * @author yong.ye
 * @date  2023/06/30 16:39
 **/
@Getter
@Setter
public class EquipmenttransAddParam {

    /** 设备登记ID主键（历史表EquipmentTrans.EqId） */
    @ApiModelProperty(value = "设备登记ID主键（历史表EquipmentTrans.EqId）", position = 2)
    private String eqId;

    /** 登记日期（历史表EquipmentTrans.RegisterDate） */
    @ApiModelProperty(value = "登记日期（历史表EquipmentTrans.RegisterDate）", position = 3)
    private Date registerDate;

    /** 转入部门（历史表EquipmentTrans.Dept） */
    @ApiModelProperty(value = "转入部门（历史表EquipmentTrans.Dept）", position = 4)
    private String dept;

    /** 现使用人（历史表EquipmentTrans.Users） */
    @ApiModelProperty(value = "现使用人（历史表EquipmentTrans.Users）", position = 5)
    private String users;

    /** 转出部门（历史表EquipmentTrans.EDept） */
    @ApiModelProperty(value = "转出部门（历史表EquipmentTrans.EDept）", position = 6)
    private String eDept;

    /** 原使用人（历史表EquipmentTrans.EUsers） */
    @ApiModelProperty(value = "原使用人（历史表EquipmentTrans.EUsers）", position = 7)
    private String eUsers;

    /** 备注（历史表EquipmentTrans.Notes） */
    @ApiModelProperty(value = "备注（历史表EquipmentTrans.Notes）", position = 8)
    private String notes;

}
