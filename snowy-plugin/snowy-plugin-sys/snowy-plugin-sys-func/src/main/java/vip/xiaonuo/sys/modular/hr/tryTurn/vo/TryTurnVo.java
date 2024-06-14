package vip.xiaonuo.sys.modular.hr.tryTurn.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.sys.modular.hr.tryTurn.entity.TryTurn;

import java.util.Date;

/**
 * 试用期转正信息：基础数据来源于 花名册 + 试用期考核责任
 */

@Getter
@Setter
public class TryTurnVo extends TryTurn {

    /** 花名册 - 姓名 */
    @ApiModelProperty(value = "花名册 - 姓名", position = 51)
    private String name;

    /** 花名册 - 身份证号 */
    @ApiModelProperty(value = "花名册 - 身份证号", position = 52)
    private String idNumber;

    /** 花名册 - 部门 */
    @ApiModelProperty(value = "花名册 - 部门", position = 53)
    private String dept;

    /** 花名册 - 职务 */
    @ApiModelProperty(value = "花名册 - 职务", position = 54)
    private String duty;

    /** 花名册 - 入职日期 */
    @ApiModelProperty(value = "花名册 - 入职日期", position = 55)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 花名册 - 转正时间 */
    @ApiModelProperty(value = "花名册 - 转正时间", position = 56)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date positiveTime;

    /** 花名册 - 试用期 */
    @ApiModelProperty(value = "花名册 - 试用期", position = 57)
    private String trialPeriod;

    /** 花名册 - 最高学历 */
    @ApiModelProperty(value = "花名册 - 最高学历", position = 59)
    private String highestEducation;

    /** 花名册 - 毕业院校 */
    @ApiModelProperty(value = "花名册 - 毕业院校", position = 58)
    private String graduateSchool;

    /** 花名册 - 专业 */
    @ApiModelProperty(value = "花名册 - 专业", position = 60)
    private String major;

    /** 试用期考核责任 -SN */
    @ApiModelProperty(value = "试用期考核责任 -SN", position = 71)
    private String dutySn;

    /** 试用期考核责任 -流程实例Id */
    @ApiModelProperty(value = "试用期考核责任 -流程实例Id", position = 72)
    private String dutyProcessInstanceId;

    /** 试用期考核责任 - 申请人类型：职员|部门负责人 */
    @ApiModelProperty(value = "试用期考核责任 - 申请人类型：职员|部门负责人", position = 4)
    private String applyType;

}
