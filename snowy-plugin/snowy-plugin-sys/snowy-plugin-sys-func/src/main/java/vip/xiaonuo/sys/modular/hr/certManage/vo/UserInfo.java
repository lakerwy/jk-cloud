package vip.xiaonuo.sys.modular.hr.certManage.vo;


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
 * 花名册人员基本信息
 */
@Getter
@Setter
public class UserInfo {

    /** 人员类别 */
    @ApiModelProperty(value = "人员类别", position = 1)
    private String userType;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 3)
    private String name;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 4)
    private String deptId;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 5)
    private String dept;

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
    private Date birth;

    /** 身份证有效期 */
    @ApiModelProperty(value = "身份证有效期", position = 13)
    private String idInvalid;

    /** 手机 */
    @ApiModelProperty(value = "手机", position = 18)
    private String phone;

    /** E-Mail */
    @ApiModelProperty(value = "E-Mail", position = 19)
    private String email;

    /** 最高学历 */
    @ApiModelProperty(value = "最高学历", position = 25)
    private String highestEducation;

    /** 毕业院校 */
    @ApiModelProperty(value = "毕业院校", position = 26)
    private String graduateSchool;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 27)
    private String major;

    /** 入职时间 */
    @ApiModelProperty(value = "入职时间", position = 29)
    private Date hireDate;

    /** 用工性质 */
    @ApiModelProperty(value = "用工性质", position = 30)
    private String employNature;

    /** 转正时间 */
    @ApiModelProperty(value = "转正时间", position = 34)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date positiveTime;

}
