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
package vip.xiaonuo.sys.modular.hr.sub.param;

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
 * HR-分公司-人事档案编辑参数
 *
 * @author yong.ye
 * @date  2023/11/21 11:14
 **/
@Getter
@Setter
public class SubPersonnelEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 档案号 */
    @ApiModelProperty(value = "档案号", position = 2)
    private String fileNo;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 3)
    private String name;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 4)
    private String deptId;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 5)
    private String dept;

    /** 职位ID */
    @ApiModelProperty(value = "职位ID", position = 6)
    private String dutyId;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 7)
    private String duty;

    /** 就职状态 */
    @ApiModelProperty(value = "就职状态", position = 8)
    private String hrStatus;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 9)
    private String sex;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 10)
    private String idNumber;

    /** 出生日期 */
    @ApiModelProperty(value = "出生日期", position = 11)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    /** 年龄 */
    @ApiModelProperty(value = "年龄", position = 12)
    private Integer age;

    /** 身份证有效期 */
    @ApiModelProperty(value = "身份证有效期", position = 13)
    private String idInvalid;

    /** 民族 */
    @ApiModelProperty(value = "民族", position = 14)
    private String nation;

    /** 政治面貌 */
    @ApiModelProperty(value = "政治面貌", position = 15)
    private String politicsStatus;

    /** 籍贯 */
    @ApiModelProperty(value = "籍贯", position = 16)
    private String origo;

    /** 户籍所在地 */
    @ApiModelProperty(value = "户籍所在地", position = 17)
    private String census;

    /** 手机 */
    @ApiModelProperty(value = "手机", position = 18)
    private String phone;

    /** E-Mail */
    @ApiModelProperty(value = "E-Mail", position = 19)
    private String email;

    /** 婚姻状况 */
    @ApiModelProperty(value = "婚姻状况", position = 20)
    private String maritalStatus;

    /** 生育情况 */
    @ApiModelProperty(value = "生育情况", position = 21)
    private String fertilityStatus;

    /** 紧急联系人 */
    @ApiModelProperty(value = "紧急联系人", position = 22)
    private String emergency;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", position = 23)
    private String contact;

    /** 现居住地址 */
    @ApiModelProperty(value = "现居住地址", position = 24)
    private String address;

    /** 最高学历 */
    @ApiModelProperty(value = "最高学历", position = 25)
    private String highestEducation;

    /** 毕业院校 */
    @ApiModelProperty(value = "毕业院校", position = 26)
    private String graduateSchool;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 27)
    private String major;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 28)
    private String notes;

    /** 入职时间 */
    @ApiModelProperty(value = "入职时间", position = 29)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 用工性质 */
    @ApiModelProperty(value = "用工性质", position = 30)
    private String employNature;

    /** 司龄起算日 */
    @ApiModelProperty(value = "司龄起算日", position = 31)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 入职司龄(自动算) */
    @ApiModelProperty(value = "入职司龄(自动算)", position = 32)
    private String enterage;

    /** 试用期 */
    @ApiModelProperty(value = "试用期", position = 33)
    private String trialPeriod;

    /** 转正时间 */
    @ApiModelProperty(value = "转正时间", position = 34)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date positiveTime;

    /** 合同续签次数（自动算） */
    @ApiModelProperty(value = "合同续签次数（自动算）", position = 35)
    private Integer renewTime;

    /** 首次合同起止 */
    @ApiModelProperty(value = "首次合同起止", position = 36)
    private String firstContract;

    /** 签约单位 */
    @ApiModelProperty(value = "签约单位", position = 37)
    private String contractUnit;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 38)
    private String attachments;

    /** 图像 */
    @ApiModelProperty(value = "图像", position = 39)
    private String image;

    /** 转正司龄(自动算) */
    @ApiModelProperty(value = "转正司龄(自动算)", position = 40)
    private String positiveAge;

    /** 工作经历 */
    @ApiModelProperty(value = "工作经历", position = 41)
    private String workExperience;

    /** 异动记录 */
    @ApiModelProperty(value = "异动记录", position = 42)
    private String moveRecord;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 43)
    private String extJson;

    /** 社保单位 */
    @ApiModelProperty(value = "社保单位", position = 50)
    private String insUnit;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 51)
    private String dataScopeId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 52)
    private String status;

    /** SN */
    @ApiModelProperty(value = "SN", position = 53)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 54)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 55)
    private String processInstanceId;

    /** 最高职称专业 */
    @ApiModelProperty(value = "最高职称专业", position = 55)
    private String highTitleProfession;
    /** 最高职称等级 */
    @ApiModelProperty(value = "流程实例Id", position = 55)
    private String highTitleLever;
    /** 最高职称资格证 */
    @ApiModelProperty(value = "最高职称资格证", position = 55)
    private String highTitleNo;
}
