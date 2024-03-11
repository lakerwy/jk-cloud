package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryGjj;

/**
 *  公积金
 */

@Getter
@Setter
public class SalaryGjjVo extends SalaryGjj {

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

}
