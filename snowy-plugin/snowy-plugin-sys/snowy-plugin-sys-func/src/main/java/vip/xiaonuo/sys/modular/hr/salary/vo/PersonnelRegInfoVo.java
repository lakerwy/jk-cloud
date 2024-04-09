package vip.xiaonuo.sys.modular.hr.salary.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.sys.modular.hr.certManage.entity.CertManage;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PersonnelRegInfoVo {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 姓名 - name - userName */
    @ApiModelProperty(value = "姓名 - name - userName", position = 3)
    private String userName;

    /** 部门ID - deptId - orgId */
    @ApiModelProperty(value = "部门ID - deptId - orgId", position = 4)
    private String orgId;

    /** 职位ID - dutyId - positionId */
    @ApiModelProperty(value = "职位ID - dutyId - positionId", position = 6)
    private String positionId;

    /** 就职状态 */
    @ApiModelProperty(value = "就职状态", position = 8)
    private String status;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 9)
    private String sex;

    /** 身份证号 idNumber - idCardNumber */
    @ApiModelProperty(value = "身份证号 idNumber - idCardNumber", position = 10)
    private String idCardNumber;

    /** 出生日期 - birth - birthday */
    @ApiModelProperty(value = "出生日期", position = 11)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;

    /** 手机 */
    @ApiModelProperty(value = "手机", position = 18)
    private String phone;

    /** 入职时间 */
    @ApiModelProperty(value = "入职时间", position = 29)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 转正时间 */
    @ApiModelProperty(value = "转正时间", position = 34)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date positiveTime;

    /**
     * 系统用户Id,SysUser.Id
     */
    @ApiModelProperty(value = "系统用户Id,SysUser.Id", position = 66)
    private String userId;

    /** 离职日期 */
    @ApiModelProperty(value = "离职日期", position = 78)
    private String leaveDate;

    /** 部门 - 翻译字段 */
    @ApiModelProperty(value = "部门 - 翻译字段", position = 100)
    private String orgName;

    /** 职位 - 翻译字段 */
    @ApiModelProperty(value = "职位 - 翻译字段 ", position = 100)
    private String position;

    /** 员工类别：总部|分公司 */
    @ApiModelProperty(value = "员工类别：总部|分公司 ", position = 100)
    private String userType;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 7)
    private String firstOrgName;

    /** 一级部门Id */
    @ApiModelProperty(value = "一级部门Id", position = 8)
    private String firstOrgId;

    //HIGHEST_EDUCATION
    /** 最高学历 */
    @ApiModelProperty(value = "最高学历", position = 9)
    private String highestEducation;

    //GRADUATE_SCHOOL
    /** 毕业院校 */
    @ApiModelProperty(value = "毕业院校", position = 10)
    private String graduateSchool;

    //MAJOR
    /** 专业 */
    @ApiModelProperty(value = "专业", position = 11)
    private String major;

    /*//HIGH_TITLE_LEVER
    *//** 职称等级 *//*
    @ApiModelProperty(value = "职称等级", position = 12)
    private String highTitleLever;

    //HIGH_TITLE_PROFESSION
    *//** 最高职称专业 *//*
    @ApiModelProperty(value = "最高职称专业", position = 13)
    private String highTitleProfession;*/

    @ApiModelProperty(value = "用户职称信息", position = 12)
    private List<PersonnelTitleVo> personnelTitleVoList;

    @ApiModelProperty(value = "已注册证书", position = 13)
    private List<CertManage> certManages;
}
