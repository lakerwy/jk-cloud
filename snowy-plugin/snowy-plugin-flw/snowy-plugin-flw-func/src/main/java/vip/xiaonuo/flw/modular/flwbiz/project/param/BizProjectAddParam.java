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
package vip.xiaonuo.flw.modular.flwbiz.project.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-立项申请添加参数
 *
 * @author mayike
 * @date  2023/06/21 14:38
 **/
@Getter
@Setter
public class BizProjectAddParam {

    /** sn值 */
    @ApiModelProperty(value = "sn值", position = 2)
    private String sn;

    /** 立项编号 */
    @ApiModelProperty(value = "立项编号", position = 3)
    private String projectSn;

    /** 公司类型 */
    @ApiModelProperty(value = "公司类型", position = 4)
    private String isHead;

    /** 信息编号 */
    @ApiModelProperty(value = "信息编号", position = 5)
    private String infoNo;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 6)
    private String name;

    /** 是否EPC */
    @ApiModelProperty(value = "是否EPC", position = 7)
    private String isEpc;

    /** 是否EPC+F */
    @ApiModelProperty(value = "是否EPC+F", position = 8)
    private String isEpcf;

    /** 是否牵头 */
    @ApiModelProperty(value = "是否牵头", position = 9)
    private String isLead;

    /** 项目类型 */
    @ApiModelProperty(value = "项目类型", position = 10)
    private String type;

    /** 项目地址 */
    @ApiModelProperty(value = "项目地址", position = 11)
    private String projectAddress;

    /** 项目属性 */
    @ApiModelProperty(value = "项目属性", position = 12)
    private String source;

    /** 项目来源 */
    @ApiModelProperty(value = "项目来源", position = 13)
    private String head;

    /** 立项编号 */
    @ApiModelProperty(value = "立项编号", position = 14)
    private String approvalNo;

    /** 项目介绍 */
    @ApiModelProperty(value = "项目介绍", position = 15)
    private String introduce;

    /** 附件上传 */
    @ApiModelProperty(value = "附件上传", position = 16)
    private String attachment;

    /** 开标日期 */
    @ApiModelProperty(value = "开标日期", position = 17)
    private String startDate;

    /** 项目负责人 */
    @ApiModelProperty(value = "项目负责人", position = 18)
    private String projectHeader;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 19)
    private String dept;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 20)
    private String leader;

    /** 区域负责人 */
    @ApiModelProperty(value = "区域负责人", position = 21)
    private String areaLeader;

    /** 是否主投 */
    @ApiModelProperty(value = "是否主投", position = 22)
    private String isMain;

    /** 投标保证金 */
    @ApiModelProperty(value = "投标保证金", position = 23)
    private String bond;

    /** 投标限价 */
    @ApiModelProperty(value = "投标限价", position = 24)
    private String priceLimit;

    /** 招标代理机构 */
    @ApiModelProperty(value = "招标代理机构", position = 25)
    private String biddingAgenct;

    /** 联系人 */
    @ApiModelProperty(value = "联系人", position = 26)
    private String biddingHeader;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", position = 27)
    private String biddingPhone;

    /** 建设单位 */
    @ApiModelProperty(value = "建设单位", position = 28)
    private String project;

    /** 联系人 */
    @ApiModelProperty(value = "联系人", position = 29)
    private String projectContacts;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", position = 30)
    private String projectPhone;

    /** 设计单位 */
    @ApiModelProperty(value = "设计单位", position = 31)
    private String designUnit;

    /** 监理单位 */
    @ApiModelProperty(value = "监理单位", position = 32)
    private String controlUnit;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 33)
    private String note;

    /** 计划附件 */
    @ApiModelProperty(value = "计划附件", position = 34)
    private String subAttachment;

    /** 中标情况 */
    @ApiModelProperty(value = "中标情况", position = 35)
    private String states;

    /** 机会(可选) */
    @ApiModelProperty(value = "机会(可选)", position = 36)
    private String chance;

    /** 机会编号 */
    @ApiModelProperty(value = "机会编号", position = 37)
    private String chanceId;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 38)
    private String status;

    /** 模型id */
    @ApiModelProperty(value = "模型id", position = 44)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 45)
    private String processInstanceId;

    /** 市场调查结果 */
    @ApiModelProperty(value = "市场调查结果", position = 46)
    private String surveyResults;

    /** 总师办意见 */
    @ApiModelProperty(value = "总师办意见", position = 47)
    private String finalAdvice;

    /** 计划附件 */
    @ApiModelProperty(value = "计划附件", position = 48)
    private String planAttachment;

    /** 申请人 */
    @ApiModelProperty(value = "申请人", position = 50)
    private String applyUser;

    /** 申请时间 */
    @ApiModelProperty(value = "申请时间", position = 51)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private String applyTime;

    /** 建设地点 */
    @ApiModelProperty(value = "建设地点", position = 60)
    private String location;

    /** 建设规模 */
    @ApiModelProperty(value = "建设规模", position = 61)
    private String scale;

    /** 投资额 */
    @ApiModelProperty(value = "投资额", position = 62)
    private String investment;

    /** 是否联合体 */
    @ApiModelProperty(value = "是否联合体", position = 63)
    private String isConsortium;

    /** 是否协同 */
    @ApiModelProperty(value = "是否协同", position = 64)
    private String isCooperate;

    /** 协同部门 */
    @ApiModelProperty(value = "协同部门", position = 65)
    private String cooperateDept;

    /** 所属大区 */
    @ApiModelProperty(value = "所属大区", position = 66)
    private String region;

    /** 行业分类 */
    @ApiModelProperty(value = "行业分类", position = 67)
    private String industryClassification;

    /** 投标专员 */
    @ApiModelProperty(value = "投标专员", position = 70)
    private String bidManger;

    /**
     * 合作引进人信息
     */
    @ApiModelProperty(value = "引进人所在部门ID", position = 71)
    private String deptId;

    @ApiModelProperty(value = "引进人所在部门名称", position = 72)
    private String deptName;

    @ApiModelProperty(value = "引进人用户ID", position = 73)
    private String userId;

    @ApiModelProperty(value = "引进人用户姓名", position = 74)
    private String userName;

    @ApiModelProperty(value = "是否为零星", position = 75)
    private String isSporadic;

    @ApiModelProperty(value = "引进人所在院ID", position = 76)
    private String orgId;

    @ApiModelProperty(value = "引进人所在院名称", position = 77)
    private String orgName;

    /** 区域负责人 */
    @ApiModelProperty(value = "区域负责人Id", position = 78)
    private String areaLeaderId;

    /** 所属单位 */
    @ApiModelProperty(value = "所属单位", position = 200)
    private String unit;

    /** 经营部门 */
    @ApiModelProperty(value = "经营部门", position = 200)
    private String manageDept;

    /** 是否为重大项目 */
    @ApiModelProperty(value = "是否为重大项目", position = 210)
    private String isVital;

    /** 项目技术负责人 */
    @ApiModelProperty(value = "项目技术负责人", position = 211)
    private String projectTechHeader;

    /** 项目技术负责人Id */
    @ApiModelProperty(value = "项目技术负责人Id", position = 212)
    private String projectTechHeaderId;

}
