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
package vip.xiaonuo.sys.modular.hr.personnel.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-人事档案实体
 *
 * @author mayike
 * @date  2023/07/10 17:28
 **/
@Getter
@Setter
@TableName("PERSONNEL")
public class Personnel {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
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
    private String status;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 9)
    private String sex;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 10)
    private String idNumber;

    /** 出生日期 */
    @ApiModelProperty(value = "出生日期", position = 11)
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
    private Date hireDate;

    /** 用工性质 */
    @ApiModelProperty(value = "用工性质", position = 30)
    private String employNature;

    /** 司龄起算日 */
    @ApiModelProperty(value = "司龄起算日", position = 31)
    private Date startDate;

    /** 入职司龄(自动算) */
    @ApiModelProperty(value = "入职司龄(自动算)", position = 32)
    private String enterage;

    /** 试用期 */
    @ApiModelProperty(value = "试用期", position = 33)
    private String trialPeriod;

    /** 转正时间 */
    @ApiModelProperty(value = "转正时间", position = 34)
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

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 44)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 45)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 46)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 47)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 48)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 49)
    private String tenantId;

    /**
     * 第一学历专业
     */
    @ApiModelProperty(value = "第一学历专业", position = 52)
    private String firstMajor;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", position = 53)
    private String firstNote;

    /**
     * 第二学历
     */
    @ApiModelProperty(value = "第二学历", position = 54)
    private String secondDegree;

    /**
     * 第二学历类型
     */
    @ApiModelProperty(value = "第二学历类型", position = 55)
    private String secondDegreeClass;

    /**
     * 第二学历毕业时间
     */
    @ApiModelProperty(value = "第二学历毕业时间", position = 56)
    private String secondGraduateDate;

    /**
     * 第二学历毕业院校
     */
    @ApiModelProperty(value = "第二学历毕业院校", position = 57)
    private String secondGraduateSchool;

    /**
     * 第二学历专业
     */
    @ApiModelProperty(value = "第二学历专业", position = 58)
    private String secondMajor;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", position = 59)
    private String secondNote;

    /**
     * 第三学历
     */
    @ApiModelProperty(value = "第三学历", position = 60)
    private String thirdDegree;

    /**
     * 第三学历类型
     */
    @ApiModelProperty(value = "第三学历类型", position = 61)
    private String thirdDegreeClass;

    /**
     * 第三学历毕业时间
     */
    @ApiModelProperty(value = "第三学历毕业时间", position = 62)
    private String thirdGraduateDate;

    /**
     * 第三学历毕业院校
     */
    @ApiModelProperty(value = "第三学历毕业院校", position = 63)
    private String thirdGraduateSchool;

    /**
     * 第三学历专业
     */
    @ApiModelProperty(value = "第三学历专业", position = 64)
    private String thirdMajor;
}
