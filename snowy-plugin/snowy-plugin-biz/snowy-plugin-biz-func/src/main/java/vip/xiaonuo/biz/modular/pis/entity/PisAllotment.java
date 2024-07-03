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
package vip.xiaonuo.biz.modular.pis.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 生产院及子公司项目拨付款 - 申请流程实体
 *
 * @author yong.ye
 * @date  2024/07/03 15:29
 **/
@Getter
@Setter
@TableName("BIZ_PIS_ALLOTMENT")
public class PisAllotment {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 申请部门Id */
    @ApiModelProperty(value = "申请部门Id", position = 2)
    private String deptId;

    /** 申请部门 */
    @ApiModelProperty(value = "申请部门", position = 3)
    private String dept;

    /** 项目责任人 */
    @ApiModelProperty(value = "项目责任人", position = 4)
    private String prjOwner;

    /** 项目负责人 */
    @ApiModelProperty(value = "项目负责人", position = 5)
    private String prjManager;

    /** 项目性质 */
    @ApiModelProperty(value = "项目性质", position = 6)
    private String prjNature;

    /** 是否我方牵头：是|否 */
    @ApiModelProperty(value = "是否我方牵头：是|否", position = 7)
    private String isHead;

    /** 是否我方牵头：职责 */
    @ApiModelProperty(value = "是否我方牵头：职责", position = 8)
    private String headInfo;

    /** 牵头方单位 */
    @ApiModelProperty(value = "牵头方单位", position = 9)
    private String headUnit;

    /** 申请日期 */
    @ApiModelProperty(value = "申请日期", position = 10)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String infoMemo;

    /** 合同Id */
    @ApiModelProperty(value = "合同Id", position = 12)
    private String htId;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 13)
    private String htProjectName;

    /** 合同编号 */
    @ApiModelProperty(value = "合同编号", position = 14)
    private String htContractNo;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 15)
    private BigDecimal htContractAmount;

    /** 合同盖章日期 */
    @ApiModelProperty(value = "合同盖章日期", position = 16)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date htSealDate;

    /** 投标保证金 */
    @ApiModelProperty(value = "投标保证金", position = 17)
    private BigDecimal bidBondAmount;

    /** 履约保证金 */
    @ApiModelProperty(value = "履约保证金", position = 18)
    private BigDecimal perBondAmount;

    /** 质保金比例 */
    @ApiModelProperty(value = "质保金比例", position = 19)
    private String warrantyRate;

    /** 质保金额 */
    @ApiModelProperty(value = "质保金额", position = 20)
    private BigDecimal warrantyAmount;

    /** 结算金额 */
    @ApiModelProperty(value = "结算金额", position = 21)
    private BigDecimal settleAmount;

    /** 结算资料附件 */
    @ApiModelProperty(value = "结算资料附件", position = 22)
    private String settleAttach;

    /** 总包合同乙方信息（B） - JSON */
    @ApiModelProperty(value = "总包合同乙方信息（B） - JSON", position = 23)
    private String bJson;

    /** 建科所占合同额——产值划分（C） - JSON */
    @ApiModelProperty(value = "建科所占合同额——产值划分（C） - JSON", position = 24)
    private String cJson;

    /** 销售发票明细（D） - JSON */
    @ApiModelProperty(value = "销售发票明细（D） - JSON", position = 25)
    private String dJson;

    /** 项目收款明细（E） - JSON */
    @ApiModelProperty(value = "项目收款明细（E） - JSON", position = 26)
    private String eJson;

    /** 项目前期拨付情况（F） - JSON */
    @ApiModelProperty(value = "项目前期拨付情况（F） - JSON", position = 27)
    private String fJson;

    /** 申请付款信息（G） - JSON */
    @ApiModelProperty(value = "申请付款信息（G） - JSON", position = 28)
    private String gJson;

    /** 资产财务中心审定信息（H） - JSON */
    @ApiModelProperty(value = "资产财务中心审定信息（H） - JSON", position = 29)
    private String hJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 30)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 31)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 32)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 33)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 34)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 35)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 36)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 37)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 38)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 39)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 40)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 41)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 42)
    private String extJson;
}
