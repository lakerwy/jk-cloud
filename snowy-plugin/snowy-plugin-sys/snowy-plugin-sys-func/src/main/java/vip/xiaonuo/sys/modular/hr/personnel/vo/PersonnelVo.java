package vip.xiaonuo.sys.modular.hr.personnel.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.sys.modular.hr.fileMoves.entity.PersonnelFileMoves;
import vip.xiaonuo.sys.modular.hr.hrCenter.entity.PersonnelFileRegister;
import vip.xiaonuo.sys.modular.hr.hrCenter.entity.PersonnelFileTitle;
import vip.xiaonuo.sys.modular.hr.hrCenter.entity.PersonnelFileCert;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PersonnelVo extends PersonnelListVo{

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 4)
    private String deptId;


    /** 职位ID */
    @ApiModelProperty(value = "职位ID", position = 6)
    private String dutyId;

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

    /** 用工性质 */
    @ApiModelProperty(value = "用工性质", position = 30)
    private String employNature;

    /** 司龄起算日 */
    @ApiModelProperty(value = "司龄起算日", position = 31)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;


    /** 试用期 */
    @ApiModelProperty(value = "试用期", position = 33)
    private String trialPeriod;


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


    /**
     * 第一学历
     */
    @ApiModelProperty(value = "第一学历", position = 49)
    private String firstDegree;

    /**
     * 学历类型
     */
    @ApiModelProperty(value = "第一学历学历类型", position = 50)
    private String firstDegreeClass;

    /**
     * 毕业时间
     */
    @ApiModelProperty(value = "第一学历毕业时间", position = 51)
    private String firstGraduateDate;

    /**
     * 毕业院校
     */
    @ApiModelProperty(value = "第一学历毕业院校", position = 51)
    private String firstGraduateSchool;

    /**
     * 第一学历专业
     */
    @ApiModelProperty(value = "第一学历专业", position = 52)
    private String firstMajor;

    /**
     * 备注
     */
    @ApiModelProperty(value = "第一学历备注", position = 53)
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
    @ApiModelProperty(value = "第二学历备注", position = 59)
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

    /**
     * 备注
     */
    @ApiModelProperty(value = "第三学历备注", position = 65)
    private String thirdNote;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 66)
    private String tenantId;

    @ApiModelProperty(value = "异动记录列表", position = 70)
    private List<PersonnelFileMoves> personnelFileMovesList;

    @ApiModelProperty(value = "职称信息列表", position = 71)
    private List<PersonnelFileTitle> personnelFileTitleList;

    @ApiModelProperty(value = "证书信息列表", position = 72)
    private List<PersonnelFileCert> personnelFileCertList;

    @ApiModelProperty(value = "签约信息列表", position = 73)
    private List<PersonnelFileRegister> personnelFileRegisterList;

    @ApiModelProperty(value = "直属上级", position = 74)
    private String director;

    @ApiModelProperty(value = "部门负责人", position = 75)
    private String leader;

    @ApiModelProperty(value = "账户id", position = 31)
    private String sysUserId;

    @ApiModelProperty(value = "是否第一负责人", position = 32)
    @TableField(exist = false)
    private String isLeader;


    /**
     * 最高职称
     */
    @ApiModelProperty(value = "最高职称", position = 60)
    private String titleLever;

    /**
     * 注册证书
     */
    @ApiModelProperty(value = "注册证书", position = 60)
    private String certType;
}
