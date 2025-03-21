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
package vip.xiaonuo.sys.modular.hr.social.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 社招 - （应聘者）简历库信息添加参数
 *
 * @author yong.ye
 * @date  2024/07/01 09:35
 **/
@Getter
@Setter
public class SocialResumeAddParam {

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 2)
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 3)
    private String sex;

    /** 出生年月 */
    @ApiModelProperty(value = "出生年月", position = 4)
    private String birthDay;

    /** 年龄 */
    @ApiModelProperty(value = "年龄", position = 5)
    private String age;

    /** 籍贯 */
    @ApiModelProperty(value = "籍贯", position = 6)
    private String nativePlace;

    /** 政治面貌 */
    @ApiModelProperty(value = "政治面貌", position = 7)
    private String politicalStatus;

    /** 健康状况 */
    @ApiModelProperty(value = "健康状况", position = 8)
    private String healthStatus;

    /** 婚姻状况 */
    @ApiModelProperty(value = "婚姻状况", position = 9)
    private String maritalStatus;

    /** 办公软件 */
    @ApiModelProperty(value = "办公软件", position = 10)
    private String officeAbility;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 11)
    private String idNumber;

    /** E-mail */
    @ApiModelProperty(value = "E-mail", position = 12)
    private String email;

    /** 户口所在地 */
    @ApiModelProperty(value = "户口所在地", position = 13)
    private String placement;

    /** 手机 */
    @ApiModelProperty(value = "手机", position = 14)
    private String phone;

    /** 现居住地址 */
    @ApiModelProperty(value = "现居住地址", position = 15)
    private String address;

    /** 住房情况 */
    @ApiModelProperty(value = "住房情况", position = 16)
    private String houseStatus;

    /** 爱好/特长 */
    @ApiModelProperty(value = "爱好/特长", position = 17)
    private String hobby;

    /** 职称 */
    @ApiModelProperty(value = "职称", position = 18)
    private String professionalTitle;

    /** 职称专业 */
    @ApiModelProperty(value = "职称专业", position = 19)
    private String professionalMajor;

    /** 执业资格 */
    @ApiModelProperty(value = "执业资格", position = 20)
    private String practiceQualification;

    /** 奖励/证书/外语等级 */
    @ApiModelProperty(value = "奖励/证书/外语等级", position = 21)
    private String skillLevel;

    /** 教育信息(由近及远) */
    @ApiModelProperty(value = "教育信息(由近及远)", position = 22)
    private String eduJson;

    /** 工作经历(由近及远) */
    @ApiModelProperty(value = "工作经历(由近及远)", position = 23)
    private String workJson;

    /** 直系亲属之详情情况 */
    @ApiModelProperty(value = "直系亲属之详情情况", position = 24)
    private String relativeJson;

    /** 应聘信息来源 */
    @ApiModelProperty(value = "应聘信息来源", position = 25)
    private String infoResource;

    /** 亲友姓名(如有) */
    @ApiModelProperty(value = "亲友姓名(如有)", position = 26)
    private String friendName;

    /** 部门/职位(如有) */
    @ApiModelProperty(value = "部门/职位(如有)", position = 27)
    private String friendDuty;

    /** 关系(如有) */
    @ApiModelProperty(value = "关系(如有)", position = 28)
    private String friendRelation;

    /** 应聘岗位 */
    @ApiModelProperty(value = "应聘岗位", position = 29)
    private String applyPost;

    /** 需求部门 */
    @ApiModelProperty(value = "需求部门", position = 30)
    private String needDept;

    /** 简历附件 */
    @ApiModelProperty(value = "简历附件", position = 31)
    private String resumeAttach;

    /** 其他附件 */
    @ApiModelProperty(value = "其他附件", position = 32)
    private String otherAttach;

    /** 是否发送短信：0：否 1：是 */
    @ApiModelProperty(value = "是否发送短信：0：否 1：是", position = 33)
    private String isSend;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 34)
    private String memo;

    @ApiModelProperty(value = "最快到岗时间", position = 35)
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String entryDate;

    /** 毕业证扫描件 */
    @ApiModelProperty(value = "毕业证扫描件", position = 40)
    private String graduationCert;

    /** 学业证扫描件 */
    @ApiModelProperty(value = "学业证扫描件", position = 41)
    private String academicCert;

    /** 学籍查询报告 */
    @ApiModelProperty(value = "学籍查询报告", position = 42)
    private String studentReport;


}
