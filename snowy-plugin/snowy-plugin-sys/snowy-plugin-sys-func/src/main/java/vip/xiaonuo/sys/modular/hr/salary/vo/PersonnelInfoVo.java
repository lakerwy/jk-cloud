package vip.xiaonuo.sys.modular.hr.salary.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 花名册 - 人员信息实体简化版
 */

@Getter
@Setter
public class PersonnelInfoVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 3)
    private String name;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 4)
    private String deptId;

    /** 职位ID */
    @ApiModelProperty(value = "职位ID", position = 6)
    private String dutyId;

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
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

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
    private String deptName;

    /** 职位 - 翻译字段 */
    @ApiModelProperty(value = "职位 - 翻译字段 ", position = 100)
    private String dutyName;

    /** 员工类别：总部|分公司 */
    @ApiModelProperty(value = "员工类别：总部|分公司 ", position = 100)
    private String userType;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 7)
    private String firstOrgName;

    /** 一级部门Id */
    @ApiModelProperty(value = "一级部门Id", position = 8)
    private String firstOrgId;
}
