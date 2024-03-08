package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 待确认工资|已确认封存工资 混合列表
 */

@Getter
@Setter
public class ConfirmSalaryParam {
    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月")
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String userName;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门")
    private String firstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门")
    private String orgName;

    /** 员工类别：总部|分公司|外聘人员 */
    @ApiModelProperty(value = "员工类别：总部|分公司|外聘人员")
    private String userType;

    /** 确认状态：已确认|未确认*/
    @ApiModelProperty(value = "确认状态：已确认|未确认")
    private String confirmStatus;
}
