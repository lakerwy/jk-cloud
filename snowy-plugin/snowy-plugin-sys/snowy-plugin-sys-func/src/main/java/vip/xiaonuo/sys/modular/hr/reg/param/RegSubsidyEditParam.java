package vip.xiaonuo.sys.modular.hr.reg.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 注册补贴（新版）-审批流编辑参数
 *
 * @author yong.ye
 * @date  2025/01/15 09:07
 **/
@Getter
@Setter
public class RegSubsidyEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 申请人 */
    @ApiModelProperty(value = "申请人", position = 2)
    private String name;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 3)
    private String position;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 4)
    private String idCardNumber;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 5)
    private String firstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 6)
    private String secondOrgName;

    /** 入职时间 */
    @ApiModelProperty(value = "入职时间", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 最高学历 */
    @ApiModelProperty(value = "最高学历", position = 8)
    private String education;

    /** 毕业院校 */
    @ApiModelProperty(value = "毕业院校", position = 9)
    private String college;

    /** 专业 */
    @ApiModelProperty(value = "专业", position = 10)
    private String major;

    /** 毕业时间 */
    @ApiModelProperty(value = "毕业时间", position = 11)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date gradTime;

    /** 职称等级 */
    @ApiModelProperty(value = "职称等级", position = 12)
    private String certLevel;

    /** 职称专业 */
    @ApiModelProperty(value = "职称专业", position = 13)
    private String certMajor;

    /** 已有证书json */
    @ApiModelProperty(value = "已有证书json", position = 14)
    private String haveJson;

    /** 本次申请json */
    @ApiModelProperty(value = "本次申请json", position = 15)
    private String applyJson;

    /** 承诺书扫描附件 */
    @ApiModelProperty(value = "承诺书扫描附件", position = 16)
    private String attach;

    /** 结果反馈json */
    @ApiModelProperty(value = "结果反馈json", position = 17)
    private String resultJson;

    /** 归属集团金额合计 */
    @ApiModelProperty(value = "归属集团金额合计", position = 18)
    private String jtTotal;

    /** 归属部门金额合计 */
    @ApiModelProperty(value = "归属部门金额合计", position = 19)
    private String deptTotal;

    /** 归属部门金额合计json */
    @ApiModelProperty(value = "归属部门金额合计json", position = 20)
    private String deptJson;

    /** 小计 */
    @ApiModelProperty(value = "小计", position = 21)
    private String sumTotal;

    /** 申请人：花名册主键id */
    @ApiModelProperty(value = "申请人：花名册主键id", position = 22)
    private String personnelId;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 23)
    private String memo;

    /** SN */
    @ApiModelProperty(value = "SN", position = 31)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 32)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 33)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 34)
    private String status;

}
