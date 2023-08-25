package vip.xiaonuo.biz.modular.recruit.param.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/8/23 9:23
 *
 * 流程中 添加dto
 */

@Getter
@Setter
public class RecruitPlanFlwAddDto {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
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

    /** 计划编制(当时填写) */
    @ApiModelProperty(value = "计划编制(当时填写)", position = 5)
    private Integer planNums;

    /** 实际编制(当时填写) */
    @ApiModelProperty(value = "实际编制(当时填写)", position = 5)
    private Integer realNums;

    /** 年度 */
    @ApiModelProperty(value = "年度", position = 30)
    private String year;
}
