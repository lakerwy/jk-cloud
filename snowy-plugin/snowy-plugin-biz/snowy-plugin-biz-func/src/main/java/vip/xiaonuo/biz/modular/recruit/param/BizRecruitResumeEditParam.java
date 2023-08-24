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

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-招聘-简历库编辑参数
 *
 * @author yong.ye
 * @date  2023/08/23 17:08
 **/
@Getter
@Setter
public class BizRecruitResumeEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
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

    /** 外键（招聘计划主键Id） */
    @ApiModelProperty(value = "外键（招聘计划主键Id）", position = 34)
    private String planId;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 35)
    private String status;

    /** 简历信息JSON */
    @ApiModelProperty(value = "简历信息JSON", position = 36)
    private String resumeJson;

    /** 简历附件 */
    @ApiModelProperty(value = "简历附件", position = 37)
    private String resumeAttach;

    /** 简历附件地址 */
    @ApiModelProperty(value = "简历附件地址", position = 38)
    private String resumeUrl;

    /** 应聘登记JSON */
    @ApiModelProperty(value = "应聘登记JSON", position = 39)
    private String registJson;

    /** 第三方解析接口返回JSON */
    @ApiModelProperty(value = "第三方解析接口返回JSON", position = 40)
    private String resumeParserJson;
}
