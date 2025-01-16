package vip.xiaonuo.sys.modular.hr.reg.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.sys.modular.hr.reg.entity.RegSubsidyResult;

import java.util.Date;


@Getter
@Setter
public class RegResultVo extends RegSubsidyResult {

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 101)
    private String regPosition;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 102)
    private String regFirstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 103)
    private String regSecondOrgName;

    /** 入职时间 */
    @ApiModelProperty(value = "入职时间", position = 104)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regHireDate;

    /** 最高学历 */
    @ApiModelProperty(value = "最高学历", position = 105)
    private String regEducation;

    /** 毕业院校 */
    @ApiModelProperty(value = "毕业院校", position = 106)
    private String regCollege;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 107)
    private String regMajor;

    /** 毕业时间 */
    @ApiModelProperty(value = "毕业时间", position = 108)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regGradTime;

    /** 职称等级 */
    @ApiModelProperty(value = "职称等级", position = 109)
    private String regCertLevel;

    /** 职称专业 */
    @ApiModelProperty(value = "职称专业", position = 110)
    private String regCertMajor;

    /** 承诺书扫描附件 */
    @ApiModelProperty(value = "承诺书扫描附件", position = 111)
    private String regAttach;

    /** 归属集团金额合计 */
    @ApiModelProperty(value = "归属集团金额合计", position = 112)
    private String regJtTotal;

    /** 归属部门金额合计 */
    @ApiModelProperty(value = "归属部门金额合计", position = 113)
    private String regDeptTotal;

    /** 小计 */
    @ApiModelProperty(value = "小计", position = 114)
    private String regSumTotal;

    /** SN */
    @ApiModelProperty(value = "SN", position = 115)
    private String regSn;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 116)
    private String regProcessInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 117)
    private String regStatus;

}
