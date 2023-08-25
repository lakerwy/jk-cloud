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
package vip.xiaonuo.biz.modular.recruit.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 业务表-招聘-部门年度计划表编辑参数
 *
 * @author yong.ye
 * @date  2023/08/23 09:02
 **/
@Getter
@Setter
public class BizRecruitPlanEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 编号 */
    @ApiModelProperty(value = "编号", position = 2)
    private String sn;

    /** 招聘岗位 */
    @ApiModelProperty(value = "招聘岗位", position = 3)
    private String postName;

    /** 所属部门 */
    @ApiModelProperty(value = "所属部门", position = 4)
    private String orgId;

    /** 计划编制 */
    @ApiModelProperty(value = "计划编制", position = 5)
    private Integer planNums;

    /** 实际编制 */
    @ApiModelProperty(value = "实际编制", position = 5)
    private Integer realNums;

    /** 需求人数 */
    @ApiModelProperty(value = "需求人数", position = 5)
    private Integer needNums;

    /** 申请人 */
    @ApiModelProperty(value = "申请人", position = 6)
    private String applyer;

    /** 申请时间 */
    @ApiModelProperty(value = "申请时间", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;

    /** 希望到岗时间 */
    @ApiModelProperty(value = "希望到岗时间", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date arrivalDate;

    /** 需求原因 */
    @ApiModelProperty(value = "需求原因", position = 9)
    private String reason;

    /** 受雇身份 */
    @ApiModelProperty(value = "受雇身份", position = 10)
    private String hireType;

    /** 岗位职责 */
    @ApiModelProperty(value = "岗位职责", position = 11)
    private String dutyDescription;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 12)
    private String gender;

    /** 资格证书 */
    @ApiModelProperty(value = "资格证书", position = 13)
    private String certificate;

    /** 年龄 */
    @ApiModelProperty(value = "年龄", position = 14)
    private String age;

    /** 工作经验 */
    @ApiModelProperty(value = "工作经验", position = 15)
    private String workExperience;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 16)
    private String education;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 17)
    private String profession;

    /** 工作技能 */
    @ApiModelProperty(value = "工作技能", position = 18)
    private String skills;

    /** 素质能力 */
    @ApiModelProperty(value = "素质能力", position = 19)
    private String diathesis;

    /** 用人需求部门意见 */
    @ApiModelProperty(value = "用人需求部门意见", position = 20)
    private String yrOpinion;

    /** 人力资源中心意见 */
    @ApiModelProperty(value = "人力资源中心意见", position = 21)
    private String hrOpinion;

    /** 公司董事长/总经理意见 */
    @ApiModelProperty(value = "公司董事长/总经理意见", position = 22)
    private String bossOpinion;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 23)
    private String status;

    /** 外键（流程单主键Id） */
    @ApiModelProperty(value = "外键（流程单主键Id）", position = 29)
    private String flwId;

    /** 年度 */
    @ApiModelProperty(value = "年度", position = 30)
    private String year;

}
