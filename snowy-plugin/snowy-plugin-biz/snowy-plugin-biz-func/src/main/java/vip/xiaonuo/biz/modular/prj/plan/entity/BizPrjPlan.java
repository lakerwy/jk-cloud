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
package vip.xiaonuo.biz.modular.prj.plan.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-项目计划付款单实体
 *
 * @author yong.ye
 * @date  2023/09/28 11:25
 **/
@Getter
@Setter
@TableName("BIZ_PRJ_PLAN")
public class BizPrjPlan {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 合作方/分公司编码 */
    @ApiModelProperty(value = "合作方/分公司编码", position = 2)
    private String hzfCode;

    /** 合作方/分公司名称 */
    @ApiModelProperty(value = "合作方/分公司名称", position = 3)
    private String hzfName;

    /** 申请日期 */
    @ApiModelProperty(value = "申请日期", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;

    /** 计划付款单编号 */
    @ApiModelProperty(value = "计划付款单编号", position = 5)
    private String planNo;

    /** 合同编号 */
    @ApiModelProperty(value = "合同编号", position = 6)
    private String contractNo;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 7)
    private BigDecimal contractAmount;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 8)
    private String contractName;

    /** 合同签订日期 */
    @ApiModelProperty(value = "合同签订日期", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sealDate;

    /** 合同归档日期 */
    @ApiModelProperty(value = "合同归档日期", position = 10)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date filingDate;

    /** 开工日期 */
    @ApiModelProperty(value = "开工日期", position = 11)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 完工日期 */
    @ApiModelProperty(value = "完工日期", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 总包合同对分包的限制条款 */
    @ApiModelProperty(value = "总包合同对分包的限制条款", position = 13)
    private String clause;

    /** 客户编码 */
    @ApiModelProperty(value = "客户编码", position = 14)
    private String ctmCode;

    /** 客户名称 */
    @ApiModelProperty(value = "客户名称", position = 15)
    private String ctmName;

    /** 合作期限 */
    @ApiModelProperty(value = "合作期限", position = 16)
    private String termLimit;

    /** 项目所在县市 */
    @ApiModelProperty(value = "项目所在县市", position = 17)
    private String prjAddress;

    /** 竣工结算金额 */
    @ApiModelProperty(value = "竣工结算金额", position = 18)
    private BigDecimal completeAmount;

    /** 竣工结算资料说明 */
    @ApiModelProperty(value = "竣工结算资料说明", position = 19)
    private String completeMemo;

    /** 竣工结算资料上传 */
    @ApiModelProperty(value = "竣工结算资料上传", position = 20)
    private String completeAttach;

    /** 合同归档率 */
    @ApiModelProperty(value = "合同归档率", position = 21)
    private String filingRate;

    /** 工程总承包事业部意见 */
    @ApiModelProperty(value = "工程总承包事业部意见", position = 31)
    private String gczcIdea;

    /** 财务审定付款金额 */
    @ApiModelProperty(value = "财务审定付款金额", position = 32)
    private BigDecimal cwFkAmount;

    /** 资产财务中心意见-审定意见 */
    @ApiModelProperty(value = "资产财务中心意见-审定意见", position = 33)
    private String cwIdea;

    /** SN */
    @ApiModelProperty(value = "SN", position = 34)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 35)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 36)
    private String processInstanceId;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 37)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 38)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 39)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 40)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 41)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 42)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 43)
    private String extJson;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 44)
    private String dataScopeId;
}
