package vip.xiaonuo.biz.modular.recruit.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/8/28 8:18
 */

@Getter
@Setter
public class BizRecruitResumeVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 简历类型社招|校招 */
    @ApiModelProperty(value = "简历类型社招|校招", position = 2)
    private String type;

    /** 简历来源 */
    @ApiModelProperty(value = "简历来源", position = 3)
    private String resumeResource;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 4)
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 5)
    private String gender;

    /** 年龄 */
    @ApiModelProperty(value = "年龄", position = 6)
    private String age;

    /** 婚姻状态 */
    @ApiModelProperty(value = "婚姻状态", position = 7)
    private String maritalStatus;

    /** 出入年月 */
    @ApiModelProperty(value = "出入年月", position = 8)
    private String birthday;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 9)
    private String idCard;

    /** 民族 */
    @ApiModelProperty(value = "民族", position = 10)
    private String race;

    /** 国籍 */
    @ApiModelProperty(value = "国籍", position = 11)
    private String nationality;

    /** 政治面貌 */
    @ApiModelProperty(value = "政治面貌", position = 12)
    private String politStatus;

    /** 应聘职位 */
    @ApiModelProperty(value = "应聘职位", position = 13)
    private String applyJob;

    /** 工作年限 */
    @ApiModelProperty(value = "工作年限", position = 14)
    private String workYear;

    /** 在职状态 */
    @ApiModelProperty(value = "在职状态", position = 15)
    private String workStatus;

    /** 当前薪酬 */
    @ApiModelProperty(value = "当前薪酬", position = 16)
    private String workSalary;

    /** 工作地点 */
    @ApiModelProperty(value = "工作地点", position = 17)
    private String workLocation;

    /** 毕业时间 */
    @ApiModelProperty(value = "毕业时间", position = 18)
    private String gradTime;

    /** 毕业学校 */
    @ApiModelProperty(value = "毕业学校", position = 19)
    private String college;

    /** 毕业学校类型 */
    @ApiModelProperty(value = "毕业学校类型", position = 20)
    private String collegeType;

    /** 毕业院系 */
    @ApiModelProperty(value = "毕业院系", position = 21)
    private String collegeDept;

    /** 所学专业 */
    @ApiModelProperty(value = "所学专业", position = 22)
    private String major;

    /** 学历 */
    @ApiModelProperty(value = "学历", position = 23)
    private String degree;

    /** 是否统招 */
    @ApiModelProperty(value = "是否统招", position = 24)
    private String recruit;

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱", position = 25)
    private String email;

    /** 电话号码 */
    @ApiModelProperty(value = "电话号码", position = 26)
    private String phone;

    /** 期望薪资 */
    @ApiModelProperty(value = "期望薪资", position = 27)
    private String expectSalary;

    /** 到岗时间 */
    @ApiModelProperty(value = "到岗时间", position = 28)
    private String expectTime;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 29)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 30)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 31)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 32)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 33)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 外键（招聘计划主键Id） */
    @ApiModelProperty(value = "外键（招聘计划主键Id）", position = 34)
    private String planId;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 35)
    private String status;

    /** 状态阶段 */
    @ApiModelProperty(value = "状态阶段", position = 35)
    private String statusStage;

    /** 简历信息JSON */
    @ApiModelProperty(value = "简历信息JSON", position = 36)
    private String resumeJson;

    /** 简历附件 */
    @ApiModelProperty(value = "简历附件", position = 37)
    private String resumeAttach;

    /** 简历附件地址 */
    @ApiModelProperty(value = "简历附件地址", position = 38)
    private String resumeUrl;

    /** 简历附件名称（带后缀） */
    @ApiModelProperty(value = "简历附件名称（带后缀）", position = 38)
    private String resumeName;

    /** 应聘登记JSON */
    @ApiModelProperty(value = "应聘登记JSON", position = 39)
    private String registJson;

    /** 第三方解析接口返回JSON */
//    @ApiModelProperty(value = "第三方解析接口返回JSON", position = 40)
//    private String resumeParserJson;

    /** 标签 */
    @ApiModelProperty(value = "标签", position = 40)
    private String tags;

    /** 工作经历文本内容 */
    @ApiModelProperty(value = "工作经历文本内容", position = 40)
    private String contJobExp;

    /** 所获证书文本内容 */
    @ApiModelProperty(value = "所获证书文本内容", position = 40)
    private String contCertificate;

    /** 拒绝OFFER的原因 */
    @ApiModelProperty(value = "拒绝OFFER的原因", position = 40)
    private String reason;




    /** 编号 */
//    @ApiModelProperty(value = "编号", position = 2)
//    private String snPlan;

    /** 招聘岗位 */
    @ApiModelProperty(value = "招聘岗位", position = 3)
    private String postNamePlan;

    /** 所属部门 */
    @ApiModelProperty(value = "所属部门", position = 4)
    private String orgId;

//    /** 计划编制(当时填写) */
//    @ApiModelProperty(value = "计划编制(当时填写)", position = 5)
//    private Integer planNumsPlan;
//
//    /** 实际编制(当时填写) */
//    @ApiModelProperty(value = "实际编制(当时填写)", position = 5)
//    private Integer realNumsPlan;

    /** 需求人数 */
    @ApiModelProperty(value = "需求人数", position = 5)
    private Integer needNumsPlan;

    /** 申请人 */
    @ApiModelProperty(value = "申请人", position = 6)
    private String applyer;

    /** 负责人 */
    @ApiModelProperty(value = "负责人", position = 6)
    private String applyerId;

//    /** 申请时间 */
//    @ApiModelProperty(value = "申请时间", position = 7)
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date applyDatePlan;
//
//    /** 希望到岗时间 */
//    @ApiModelProperty(value = "希望到岗时间", position = 8)
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date arrivalDatePlan;
//
//    /** 需求原因 */
//    @ApiModelProperty(value = "需求原因", position = 9)
//    private String reasonPlan;
//
//    /** 受雇身份 */
//    @ApiModelProperty(value = "受雇身份", position = 10)
//    private String hireTypePlan;
//
//    /** 岗位职责 */
//    @ApiModelProperty(value = "岗位职责", position = 11)
//    private String dutyDescriptionPlan;
//
//    /** 性别 */
//    @ApiModelProperty(value = "性别", position = 12)
//    private String genderPlan;
//
//    /** 资格证书 */
//    @ApiModelProperty(value = "资格证书", position = 13)
//    private String certificatePlan;
//
//    /** 年龄 */
//    @ApiModelProperty(value = "年龄", position = 14)
//    private String agePlan;
//
//    /** 工作经验 */
//    @ApiModelProperty(value = "工作经验", position = 15)
//    private String workExperiencePlan;
//
//    /** 学历 */
//    @ApiModelProperty(value = "学历", position = 16)
//    private String educationPlan;
//
//    /** 专业 */
//    @ApiModelProperty(value = "专业", position = 17)
//    private String professionPlan;
//
//    /** 工作技能 */
//    @ApiModelProperty(value = "工作技能", position = 18)
//    private String skillsPlan;
//
//    /** 素质能力 */
//    @ApiModelProperty(value = "素质能力", position = 19)
//    private String diathesisPlan;

    /** 年度 */
    @ApiModelProperty(value = "年度", position = 30)
    private String yearPlan;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 31)
    private String orgName;
}
