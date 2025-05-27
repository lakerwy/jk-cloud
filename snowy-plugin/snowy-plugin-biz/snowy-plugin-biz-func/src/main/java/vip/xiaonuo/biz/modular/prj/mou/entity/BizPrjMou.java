package vip.xiaonuo.biz.modular.prj.mou.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目承接备忘录实体
 *
 * @author yy
 * @date  2025/05/27 10:06
 **/
@Getter
@Setter
@TableName("BIZ_PRJ_MOU")
public class BizPrjMou {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** （立项）项目Id */
    @ApiModelProperty(value = "（立项）项目Id", position = 2)
    private String prjId;

    /** （立项）项目名称 */
    @ApiModelProperty(value = "（立项）项目名称", position = 3)
    private String prjName;

    /** 申请日期 */
    @ApiModelProperty(value = "申请日期", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyDate;

    /** 项目概要 */
    @ApiModelProperty(value = "项目概要", position = 5)
    private String prjMemo;

    /** 项目类别 */
    @ApiModelProperty(value = "项目类别", position = 6)
    private String prjType;

    /** 建设单位 */
    @ApiModelProperty(value = "建设单位", position = 7)
    private String devUnit;

    /** 分公司/合作单位类别 */
    @ApiModelProperty(value = "分公司/合作单位类别", position = 8)
    private String partnerType;

    /** 分公司/合作单位Id */
    @ApiModelProperty(value = "分公司/合作单位Id", position = 9)
    private String partnerId;

    /** 分公司/合作单位 */
    @ApiModelProperty(value = "分公司/合作单位", position = 10)
    private String partnerUnit;

    /** 分公司/合作单位联系人 */
    @ApiModelProperty(value = "分公司/合作单位联系人", position = 11)
    private String partnerContact;

    /** 分公司/合作单位联系方式 */
    @ApiModelProperty(value = "分公司/合作单位联系方式", position = 12)
    private String partnerTel;

    /** 备案摘要 */
    @ApiModelProperty(value = "备案摘要", position = 13)
    private String registMemo;

    /** 预估签约合同额（含税） */
    @ApiModelProperty(value = "预估签约合同额（含税）", position = 14)
    private BigDecimal htAmount;

    /** 预估投标时间 */
    @ApiModelProperty(value = "预估投标时间", position = 15)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bidDate;

    /** 预估合同签订时间 */
    @ApiModelProperty(value = "预估合同签订时间", position = 16)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date signDate;

    /** 经办部门Id */
    @ApiModelProperty(value = "经办部门Id", position = 17)
    private String deptId;

    /** 经办部门 */
    @ApiModelProperty(value = "经办部门", position = 18)
    private String deptName;

    /** 备忘录详细说明 */
    @ApiModelProperty(value = "备忘录详细说明", position = 19)
    private String prjDesc;

    /** 分公司/合作人 */
    @ApiModelProperty(value = "分公司/合作人", position = 20)
    private String prjPartner;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 21)
    private String prjAttach;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 22)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 23)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 24)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 25)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 26)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 27)
    private String attachs;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 28)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 29)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 30)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** SN */
    @ApiModelProperty(value = "SN", position = 31)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 32)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 33)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 34)
    private String status;
}
