package vip.xiaonuo.biz.modular.crm.equipmenttrans.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/7/3 16:59
 */
@Getter
@Setter
public class EquipmenttransInfoPageParam {
    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 设备登记ID主键（历史表EquipmentTrans.EqId） */
    @ApiModelProperty(value = "设备登记ID主键（历史表EquipmentTrans.EqId）")
    private String eqId;

    /** 登记日期（历史表EquipmentTrans.RegisterDate）开始 */
    @ApiModelProperty(value = "登记日期（历史表EquipmentTrans.RegisterDate）开始")
    private String startRegisterDate;

    /** 登记日期（历史表EquipmentTrans.RegisterDate）结束 */
    @ApiModelProperty(value = "登记日期（历史表EquipmentTrans.RegisterDate）结束")
    private String endRegisterDate;

    /** 转入部门（历史表EquipmentTrans.Dept） */
    @ApiModelProperty(value = "转入部门（历史表EquipmentTrans.Dept）")
    private String dept;

    /** 现使用人（历史表EquipmentTrans.Users） */
    @ApiModelProperty(value = "现使用人（历史表EquipmentTrans.Users）")
    private String users;

    /** 转出部门（历史表EquipmentTrans.EDept） */
    @ApiModelProperty(value = "转出部门（历史表EquipmentTrans.EDept）")
    private String eDept;

    /** 原使用人（历史表EquipmentTrans.EUsers） */
    @ApiModelProperty(value = "原使用人（历史表EquipmentTrans.EUsers）")
    private String eUsers;

    /**
     *  设备信息（携带）
     */

    /** SN 设备编号 */
    @ApiModelProperty(value = "SN 设备编号")
    private String SN;

    /** 未知（历史数据存储同SN） */
    @ApiModelProperty(value = "未知（历史数据存储同SN）")
    private String EqNum;

    /** 配置信息 */
    @ApiModelProperty(value = "配置信息")
    private String Configure;

    /** 设备类型 */
    @ApiModelProperty(value = "设备类型")
    private String EqType;

    /** 状态 */
    @ApiModelProperty(value = "状态")
    private String Status;

}
