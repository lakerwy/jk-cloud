package vip.xiaonuo.biz.modular.crm.parameters.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.crm.parameters.entity.BizCrmRecPlan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *  合同参数同时携带合同信息实体
 *
 * @author yong.ye
 * @date 2023/7/3 11:51
 */

@Getter
@Setter
public class ParametersInfoVo {
    /** 主键ID（历史表Parameters.Id） */
    @ApiModelProperty(value = "主键ID（历史表Parameters.Id）", position = 1)
    private String id;

    /** 登记人（历史表Parameters.Opt） */
    @ApiModelProperty(value = "登记人（历史表Parameters.Opt）", position = 2)
    private String opt;

    /** 登记日期（历史表Parameters.OptDate） */
    @ApiModelProperty(value = "登记日期（历史表Parameters.OptDate）", position = 3)
    private Date optDate;

    /** SN（历史表Parameters.SN） */
    @ApiModelProperty(value = "SN（历史表Parameters.SN）", position = 4)
    private String sn;

    /** 合同ID主键（历史表Parameters.ProjectId） */
    @ApiModelProperty(value = "合同ID主键（历史表Parameters.ProjectId）", position = 5)
    private String projectId;

    /** 建筑面积（历史表Parameters.CoveredArea） */
    @ApiModelProperty(value = "建筑面积（历史表Parameters.CoveredArea）", position = 6)
    private String coveredArea;

    /** 建筑层数（历史表Parameters.BuildStorey） */
    @ApiModelProperty(value = "建筑层数（历史表Parameters.BuildStorey）", position = 7)
    private String buildStorey;

    /** 钢结构单跨跨度（历史表Parameters.SteelSpan） */
    @ApiModelProperty(value = "钢结构单跨跨度（历史表Parameters.SteelSpan）", position = 8)
    private String steelSpan;

    /** 水处理规模（历史表Parameters.WaterScale） */
    @ApiModelProperty(value = "水处理规模（历史表Parameters.WaterScale）", position = 9)
    private String waterScale;

    /** 远期水处理规模（历史表Parameters.WaterFar） */
    @ApiModelProperty(value = "远期水处理规模（历史表Parameters.WaterFar）", position = 10)
    private String waterFar;

    /** 最大管径（历史表Parameters.PDMax） */
    @ApiModelProperty(value = "最大管径（历史表Parameters.PDMax）", position = 11)
    private String pdMax;

    /** 景观面积（历史表Parameters.LandscapeArea） */
    @ApiModelProperty(value = "景观面积（历史表Parameters.LandscapeArea）", position = 12)
    private String landscapeArea;

    /** 垃圾处理规模（历史表Parameters.GarbageDScale） */
    @ApiModelProperty(value = "垃圾处理规模（历史表Parameters.GarbageDScale）", position = 13)
    private String garbageDScale;

    /** 管廊分类及登记（历史表Parameters.PipeType） */
    @ApiModelProperty(value = "管廊分类及登记（历史表Parameters.PipeType）", position = 14)
    private String pipeType;

    /** 管廊断面尺寸 - 宽 */
    @ApiModelProperty(value = "管廊断面尺寸 - 宽", position = 15)
    private String pipeSize;

    /** 管廊断面尺寸 - 高 */
    @ApiModelProperty(value = "管廊断面尺寸 - 高", position = 15)
    private String pipeSizeH;

    /** 桥梁单跨跨度（历史表Parameters.BridgeSpan） */
    @ApiModelProperty(value = "桥梁单跨跨度（历史表Parameters.BridgeSpan）", position = 16)
    private String bridgeSpan;

    /** 桥梁总长度（历史表Parameters.BridgeTLength） */
    @ApiModelProperty(value = "桥梁总长度（历史表Parameters.BridgeTLength）", position = 17)
    private String bridgeTLength;

    /** 道路/公路等级（历史表Parameters.RoadLevels） */
    @ApiModelProperty(value = "道路/公路等级（历史表Parameters.RoadLevels）", position = 18)
    private String roadLevels;

    /** 道路总长度（历史表Parameters.RoadTLength） */
    @ApiModelProperty(value = "道路总长度（历史表Parameters.RoadTLength）", position = 19)
    private String roadTLength;

    /** 道路红线宽度（历史表Parameters.RoadRedWidth） */
    @ApiModelProperty(value = "道路红线宽度（历史表Parameters.RoadRedWidth）", position = 20)
    private String roadRedWidth;

    /** 隧道断面尺寸 - 隧道净宽限界(m) */
    @ApiModelProperty(value = "隧道断面尺寸 - 隧道净宽限界(m)", position = 21)
    private String tunnelSSize;

    /** 隧道断面尺寸 - 隧道建筑净空限界(m) */
    @ApiModelProperty(value = "隧道断面尺寸 - 隧道建筑净空限界(m)", position = 21)
    private String tunnelSSizeJ;

    /** 电力容量（历史表Parameters.Power） */
    @ApiModelProperty(value = "电力容量（历史表Parameters.Power）", position = 22)
    private String power;

    /** 供热面积（历史表Parameters.HeatsupplyArea） */
    @ApiModelProperty(value = "供热面积（历史表Parameters.HeatsupplyArea）", position = 23)
    private String heatsupplyArea;

    /** 热源规模（历史表Parameters.HeatsorceSize） */
    @ApiModelProperty(value = "热源规模（历史表Parameters.HeatsorceSize）", position = 24)
    private String heatsorceSize;

    /** 压力等级（历史表Parameters.PressurRate） */
    @ApiModelProperty(value = "压力等级（历史表Parameters.PressurRate）", position = 25)
    private String pressurRate;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 26)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 27)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 28)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 29)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 30)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;


    /**
     * 携带的合同信息
     */

    /** 工程类别 */
    @ApiModelProperty(value = "工程类别", position = 31)
    private String mainSecondLevel;

    /** 合同编号 */
    @ApiModelProperty(value = "合同编号", position = 32)
    private String mainContractNo;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 33)
    private String mainProjectName;

    /** 甲方 */
    @ApiModelProperty(value = "甲方", position = 34)
    private String mainCounterparty;

    /** 乙方 */
    @ApiModelProperty(value = "乙方", position = 35)
    private String mainSecondparty;

    /** 责任单位 */
    @ApiModelProperty(value = "责任单位", position = 36)
    private String mainCompanyOrDepartment;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 37)
    private String mainOwner;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 38)
    private BigDecimal mainContractAmount;

    /** 投资规模 */
    @ApiModelProperty(value = "投资规模", position = 39)
    private String mainInvestScale;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 40)
    private String mainStatus;

    /** 项目规模描述 */
    @ApiModelProperty(value = "项目规模描述", position = 41)
    private String mainDescription;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 42)
    private String mainNote;

    /** 中标-负责人 */
    @ApiModelProperty(value = "中标-负责人", position = 43)
    private String mainBidOwner;

    /** 项目负责人 */
    @ApiModelProperty(value = "项目负责人", position = 44)
    private String mainProjectLeader;

    /** 合同类型 */
    @ApiModelProperty(value = "合同类型", position = 45)
    private String contractType;

    /** 税率（%） */
    @ApiModelProperty(value = "税率（%）", position = 50)
    private BigDecimal taxRate;

    /** 收费标准及付费方式合同原文 */
    @ApiModelProperty(value = "收费标准及付费方式合同原文", position = 51)
    private String recPayText;

    /** 合同收款计划 */
    @ApiModelProperty(value = "合同收款计划", position = 55)
    private List<BizCrmRecPlan> recPlanList;

}
