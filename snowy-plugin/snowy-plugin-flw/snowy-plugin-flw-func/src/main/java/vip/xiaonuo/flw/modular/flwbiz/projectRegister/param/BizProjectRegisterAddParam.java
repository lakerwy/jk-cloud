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
package vip.xiaonuo.flw.modular.flwbiz.projectRegister.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务表-立项报备添加参数
 *
 * @author mayike
 * @date  2023/06/19 15:26
 **/
@Getter
@Setter
public class BizProjectRegisterAddParam {

    /** sn值 */
    @ApiModelProperty(value = "sn值", position = 2)
    private String sn;

    /** 是否立项 */
    @ApiModelProperty(value = "是否立项", position = 3)
    private String isApproval;

    /** 客户名称 */
    @ApiModelProperty(value = "客户名称", position = 4)
    private String customerName;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 5)
    private String entryName;

    /** 项目模式 */
    @ApiModelProperty(value = "项目模式", position = 6)
    private String projectMode;

    /** 建设地点 */
    @ApiModelProperty(value = "建设地点", position = 7)
    private String location;

    /** 建设规模 */
    @ApiModelProperty(value = "建设规模", position = 8)
    private String scale;

    /** 是否联合体 */
    @ApiModelProperty(value = "是否联合体", position = 9)
    private String isConsortium;

    /** 是否协同 */
    @ApiModelProperty(value = "是否协同", position = 10)
    private String isCooperate;

    /** 协同部门 */
    @ApiModelProperty(value = "协同部门", position = 11)
    private String dept;

    /** 是否分包 */
    @ApiModelProperty(value = "是否分包", position = 12)
    private String isSub;

    /** 分包比例 */
    @ApiModelProperty(value = "分包比例", position = 13)
    private String subRate;

    /** 分包单位 */
    @ApiModelProperty(value = "分包单位", position = 14)
    private String unit;

    /** 归属部门 */
    @ApiModelProperty(value = "归属部门", position = 15)
    private String belongDept;

    /** 经营状态 */
    @ApiModelProperty(value = "经营状态", position = 16)
    private String businessStatus;

    /** 信息成功率 */
    @ApiModelProperty(value = "信息成功率", position = 17)
    private String successRate;

    /** 预估合同额(万元) */
    @ApiModelProperty(value = "预估合同额(万元)", position = 18)
    private String amount;

    /** 联合体单位 */
    @ApiModelProperty(value = "联合体单位", position = 19)
    private String designUnit1;

    /** 所属行业 */
    @ApiModelProperty(value = "所属行业", position = 20)
    private String industry1;

    /** 客户联系人 */
    @ApiModelProperty(value = "客户联系人", position = 21)
    private String contact1;

    /** 职务 */
    @ApiModelProperty(value = "职务", position = 22)
    private String duties1;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 23)
    private String consortiumNote1;

    /** 联合体单位 */
    @ApiModelProperty(value = "联合体单位", position = 24)
    private String designUnit2;

    /** 所属行业 */
    @ApiModelProperty(value = "所属行业", position = 25)
    private String industry2;

    /** 客户联系人 */
    @ApiModelProperty(value = "客户联系人", position = 26)
    private String contact2;

    /** 职务 */
    @ApiModelProperty(value = "职务", position = 27)
    private String duties2;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 28)
    private String consortiumNote2;

    /** 联合体单位 */
    @ApiModelProperty(value = "联合体单位", position = 29)
    private String designUnit3;

    /** 所属行业 */
    @ApiModelProperty(value = "所属行业", position = 30)
    private String industry3;

    /** 客户联系人 */
    @ApiModelProperty(value = "客户联系人", position = 31)
    private String contact3;

    /** 职务 */
    @ApiModelProperty(value = "职务", position = 32)
    private String duties3;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 33)
    private String consortiumNote3;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 34)
    private String note;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 35)
    private String attachment;

    /** 摘要详情 */
    @ApiModelProperty(value = "摘要详情", position = 36)
    private String summary;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 38)
    private String status;

    /** 模型id */
    @ApiModelProperty(value = "模型id", position = 39)
    private String modelId;

    /** 重要程度 */
    @ApiModelProperty(value = "重要程度", position = 43)
    private String impotence;

    @ApiModelProperty(value = "流程实例ID", position = 60)
    private String processInstanceId;

    /** 申请人 */
    @ApiModelProperty(value = "申请人", position = 70)
    private String applyUser;

    /** 申请时间 */
    @ApiModelProperty(value = "申请时间", position = 71)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private String applyTime;

    /** 项目地址 */
    @ApiModelProperty(value = "项目地址", position = 60)
    private String projectAddress;

    /** 投资额 */
    @ApiModelProperty(value = "投资额", position = 61)
    private String investment;

    @ApiModelProperty(value = "区域负责人", position = 62)
    private String areaLeader;
}
