package vip.xiaonuo.sys.modular.hr.tryTurn.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.sys.modular.hr.tryTurn.entity.TryTurn;

import java.util.Date;

/**
 * 试用期转正信息
 */

@Getter
@Setter
public class TryTurnVo extends TryTurn {
    /** 试用期考核责任 - 姓名 */
    @ApiModelProperty(value = "试用期考核责任 -姓名", position = 60)
    private String name;

    /** 试用期考核责任 -身份证号 */
    @ApiModelProperty(value = "试用期考核责任 -身份证号", position = 61)
    private String idNumber;

    /** 试用期考核责任 -部门 */
    @ApiModelProperty(value = "试用期考核责任 -部门", position = 62)
    private String dept;

    /** 试用期考核责任 -职务 */
    @ApiModelProperty(value = "试用期考核责任 -职务", position = 63)
    private String duty;

    /** 试用期考核责任 -入职时间 */
    @ApiModelProperty(value = "试用期考核责任 -入职时间", position = 64)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 试用期考核责任 -转正时间 */
    @ApiModelProperty(value = "试用期考核责任 -转正时间", position = 65)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date positiveTime;

    /** 试用期考核责任 -试用期 */
    @ApiModelProperty(value = "试用期考核责任 -试用期", position = 66)
    private String trialPeriod;

    /** 试用期考核责任 -签约单位 */
    @ApiModelProperty(value = "试用期考核责任 -签约单位", position = 67)
    private String contractUnit;

//    /** 试用期考核责任 -岗位职责 */
//    @ApiModelProperty(value = "试用期考核责任 -岗位职责", position = 68)
//    private String dutyAttach;
//
//    /** 试用期考核责任 -试用期目标责任 */
//    @ApiModelProperty(value = "试用期考核责任 -试用期目标责任", position = 69)
//    private String pointJson;
//
//    /** 试用期考核责任 -试用期考核责任附件-扫描复印件（签字） */
//    @ApiModelProperty(value = "试用期考核责任 -试用期考核责任附件-扫描复印件（签字）", position = 70)
//    private String scanAttach;

    /** 试用期考核责任 -SN */
    @ApiModelProperty(value = "试用期考核责任 -SN", position = 71)
    private String dutySn;

    /** 试用期考核责任 -流程实例Id */
    @ApiModelProperty(value = "试用期考核责任 -流程实例Id", position = 72)
    private String dutyProcessInstanceId;

}
