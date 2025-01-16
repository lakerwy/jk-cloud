package vip.xiaonuo.sys.modular.hr.reg.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 总部，分公司 花名册信息
 */
@Getter
@Setter
public class PersonnelInfoVo {

    /** 花名册 - 主键ID */
    @ApiModelProperty(value = "花名册 - 主键ID", position = 1)
    private String id;

    /** 花名册 - 姓名 - name */
    @ApiModelProperty(value = "花名册 - 姓名 - name ", position = 2)
    private String name;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 3)
    private String firstOrgName;

    /** 一级部门 - id */
    @ApiModelProperty(value = "一级部门 -id", position = 3)
    private String firstOrgId;

    /** 部门ID - orgId */
    @ApiModelProperty(value = "二级部门ID  - orgId", position = 4)
    private String orgId;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 4)
    private String orgName;

    /** 职位ID - positionId */
    @ApiModelProperty(value = "职位ID - positionId", position = 5)
    private String positionId;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 5)
    private String position;

    /** 就职状态 */
    @ApiModelProperty(value = "就职状态", position = 6)
    private String status;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 7)
    private String sex;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 8)
    private String idCardNumber;

    /** 入职时间 */
    @ApiModelProperty(value = "入职时间", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 最高学历 */
    @ApiModelProperty(value = "最高学历", position = 10)
    private String education;

    /** 毕业院校 */
    @ApiModelProperty(value = "毕业院校", position = 11)
    private String college;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 12)
    private String major;

    /** 毕业时间 */
    @ApiModelProperty(value = "毕业时间", position = 13)
    private String gradTime;

    /** 职称等级 */
    @ApiModelProperty(value = "职称等级", position = 14)
    private String certLevel;

    /** 职称专业 */
    @ApiModelProperty(value = "职称专业", position = 15)
    private String certMajor;

    /** 出生日期 - birthday */
    @ApiModelProperty(value = "出生日期", position = 16)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;

    /** 手机 */
    @ApiModelProperty(value = "手机", position = 17)
    private String phone;

    /** 转正时间 */
    @ApiModelProperty(value = "转正时间", position = 18)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date positiveTime;

    /**
     * 系统用户Id,SysUser.Id
     */
    @ApiModelProperty(value = "系统用户Id,SysUser.Id", position = 19)
    private String userId;

    /** 离职日期 */
    @ApiModelProperty(value = "离职日期", position = 20)
    private String leaveDate;

    /** 员工类别：总部|分公司 */
    @ApiModelProperty(value = "员工类别：总部|分公司 ", position = 100)
    private String userType;

}
