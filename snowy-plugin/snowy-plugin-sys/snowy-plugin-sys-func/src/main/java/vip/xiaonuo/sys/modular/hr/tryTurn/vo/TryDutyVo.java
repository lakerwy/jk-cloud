package vip.xiaonuo.sys.modular.hr.tryTurn.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.sys.modular.hr.tryTurn.entity.TryDuty;

import java.util.Date;

/**
 * 试用期考核责任 ： 基础数据来源于 花名册
 */

@Getter
@Setter
public class TryDutyVo extends TryDuty {
    /** 花名册 - 姓名 */
    @ApiModelProperty(value = "花名册 - 姓名", position = 31)
    private String name;

    /** 花名册 - 身份证号 */
    @ApiModelProperty(value = "花名册 - 身份证号", position = 32)
    private String idNumber;

    /** 花名册 - 部门 */
    @ApiModelProperty(value = "花名册 - 部门", position = 33)
    private String dept;

    /** 花名册 - 职务 */
    @ApiModelProperty(value = "花名册 - 职务", position = 34)
    private String duty;

    /** 花名册 - 入职日期 */
    @ApiModelProperty(value = "花名册 - 入职日期", position = 35)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 花名册 - 转正时间 */
    @ApiModelProperty(value = "花名册 - 转正时间", position = 36)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date positiveTime;

    /** 花名册 - 试用期 */
    @ApiModelProperty(value = "花名册 - 试用期", position = 37)
    private String trialPeriod;

    /** 花名册 - 签约单位 */
    @ApiModelProperty(value = "花名册 - 签约单位", position = 38)
    private String contractUnit;

    /** 花名册 - 最高学历 */
    @ApiModelProperty(value = "花名册 - 最高学历", position = 59)
    private String highestEducation;

    /** 花名册 - 毕业院校 */
    @ApiModelProperty(value = "花名册 - 毕业院校", position = 58)
    private String graduateSchool;

    /** 花名册 - 专业 */
    @ApiModelProperty(value = "花名册 - 专业", position = 60)
    private String major;
}
