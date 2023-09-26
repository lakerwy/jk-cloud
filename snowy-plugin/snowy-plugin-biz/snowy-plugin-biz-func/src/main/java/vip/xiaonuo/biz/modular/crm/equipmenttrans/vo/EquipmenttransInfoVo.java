package vip.xiaonuo.biz.modular.crm.equipmenttrans.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/7/3 16:51
 */

@Getter
@Setter
public class EquipmenttransInfoVo {

    /** 主键ID（历史表EquipmentTrans.Id） */
    @ApiModelProperty(value = "主键ID（历史表EquipmentTrans.Id）", position = 1)
    private String id;

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


    /**
     *  设备信息（携带）
     */

    /** SN 设备编号 */
    @ApiModelProperty(value = "SN 设备编号", position = 14)
    private String sn;

    /** 未知（历史数据存储同SN） */
    @ApiModelProperty(value = "未知（历史数据存储同SN）", position = 15)
    private String eqNum;

    /** 配置信息 */
    @ApiModelProperty(value = "配置信息", position = 16)
    private String configure;

    /** 设备类型 */
    @ApiModelProperty(value = "设备类型", position = 17)
    private String eqType;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 18)
    private String status;

    /** 采购日期 */
    @ApiModelProperty(value = "采购日期", position = 19)
    private Date purchaseDate;

    /** 采购金额 */
    @ApiModelProperty(value = "采购金额", position = 20)
    private String purchaseAmount;

}
