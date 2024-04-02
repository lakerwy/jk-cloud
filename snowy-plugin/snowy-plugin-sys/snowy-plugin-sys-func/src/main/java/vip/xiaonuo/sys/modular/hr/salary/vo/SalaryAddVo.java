package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryAdd;

/**
 * 工资 - 嘉奖|扣减项
 */
@Getter
@Setter
public class SalaryAddVo extends SalaryAdd {
    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 101)
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 102)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 103)
    private String userName;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 105)
    private String firstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 106)
    private String orgName;

    /** 所属公司 */
    @ApiModelProperty(value = "所属公司", position = 1)
    private String company;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 107)
    private String position;


    /** copy复制数据时专用 - 旧的主键Id */
    @ApiModelProperty(value = "copy复制数据时专用 - 旧的主键Id", position = 120)
    private String oldAddId;
}
