package vip.xiaonuo.sys.modular.hr.personnel.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonnelResumeVo {

    /** 简历来源 */
    @ApiModelProperty(value = "简历来源（校招|社招）", position = 1)
    private String resumeSource;


    /** 入职流程流水号SN */
    @ApiModelProperty(value = "入职流程流水号SN", position = 2)
    private String interviewSn;

    /** =========================简历信息同步字段===========================*/

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 10)
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 12)
    private String sex;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 14)
    private String idNumber;

    /** 年龄 */
    @ApiModelProperty(value = "年龄", position = 16)
    private String age;

    /** 政治面貌 */
    @ApiModelProperty(value = "政治面貌", position = 18)
    private String politicsStatus;

    /** 籍贯 */
    @ApiModelProperty(value = "籍贯", position = 20)
    private String origo;

    /** 户籍所在地 -> 户口所在地 */
    @ApiModelProperty(value = "户籍所在地", position = 22)
    private String census;

    /** 手机 */
    @ApiModelProperty(value = "手机", position = 24)
    private String phone;

    /** E-mail */
    @ApiModelProperty(value = "E-mail", position = 26)
    private String email;

    /** 婚姻状况 */
    @ApiModelProperty(value = "婚姻状况", position = 28)
    private String maritalStatus;

    /** 现居住地址 */
    @ApiModelProperty(value = "现居住地址", position = 30)
    private String address;

    /** 工作经历 */
    @ApiModelProperty(value = "工作经历", position = 32)
    private String workExperience;

    /** 最高学历 */
    @ApiModelProperty(value = "最高学历", position = 34)
    private String highestEducation;

    /** 毕业院校 */
    @ApiModelProperty(value = "毕业院校", position = 36)
    private String graduateSchool;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 38)
    private String major;

    /** 第一学历 */
    @ApiModelProperty(value = "第一学历", position = 49)
    private String firstDegree;

    /** 第一学历学历类型 */
    @ApiModelProperty(value = "第一学历学历类型", position = 50)
    private String firstDegreeClass;

    /** 第一学历毕业时间 */
    @ApiModelProperty(value = "第一学历毕业时间", position = 51)
    private String firstGraduateDate;

    /** 第一学历毕业院校 */
    @ApiModelProperty(value = "第一学历毕业院校", position = 51)
    private String firstGraduateSchool;

    /** 第一学历专业 */
    @ApiModelProperty(value = "第一学历专业", position = 52)
    private String firstMajor;

    /** 第一学历备注 */
    @ApiModelProperty(value = "第一学历备注", position = 53)
    private String firstNote;


    /** 第二学历 */
    @ApiModelProperty(value = "第二学历", position = 54)
    private String secondDegree;

    /** 第二学历类型 */
    @ApiModelProperty(value = "第二学历类型", position = 55)
    private String secondDegreeClass;

    /** 第二学历毕业时间 */
    @ApiModelProperty(value = "第二学历毕业时间", position = 56)
    private String secondGraduateDate;

    /** 第二学历毕业院校 */
    @ApiModelProperty(value = "第二学历毕业院校", position = 57)
    private String secondGraduateSchool;

    /** 第二学历专业 */
    @ApiModelProperty(value = "第二学历专业", position = 58)
    private String secondMajor;

    /** 第二学历备注 */
    @ApiModelProperty(value = "第二学历备注", position = 59)
    private String secondNote;


    /** 第三学历 */
    @ApiModelProperty(value = "第三学历", position = 60)
    private String thirdDegree;

    /** 第三学历类型 */
    @ApiModelProperty(value = "第三学历类型", position = 61)
    private String thirdDegreeClass;

    /** 第三学历毕业时间 */
    @ApiModelProperty(value = "第三学历毕业时间", position = 62)
    private String thirdGraduateDate;

    /** 第三学历毕业院校 */
    @ApiModelProperty(value = "第三学历毕业院校", position = 63)
    private String thirdGraduateSchool;

    /** 第三学历专业 */
    @ApiModelProperty(value = "第三学历专业", position = 64)
    private String thirdMajor;

    /** 第三学历备注 */
    @ApiModelProperty(value = "第三学历备注", position = 65)
    private String thirdNote;

}
