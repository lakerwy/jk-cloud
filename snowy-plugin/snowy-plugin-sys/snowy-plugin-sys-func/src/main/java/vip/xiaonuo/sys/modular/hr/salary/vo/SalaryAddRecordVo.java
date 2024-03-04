package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryAddRecord;

import java.math.BigDecimal;

/**
 * 嘉奖扣除记录详情
 */

@Getter
@Setter
public class SalaryAddRecordVo extends SalaryAddRecord {

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 32)
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 33)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 34)
    private String userName;

    /** 建科云用户Id */
    @ApiModelProperty(value = "建科云用户Id", position = 35)
    private String userId;

    /** 花名册Id */
    @ApiModelProperty(value = "花名册Id", position = 35)
    private String personnelId;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 36)
    private String firstOrgName;

    /** 一级部门Id */
    @ApiModelProperty(value = "一级部门Id", position = 37)
    private String firstOrgId;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 38)
    private String orgName;

    /** 二级部门Id */
    @ApiModelProperty(value = "二级部门Id", position = 39)
    private String orgId;

    /** 嘉奖小计 */
    @ApiModelProperty(value = "嘉奖小计", position = 40)
    private BigDecimal addAmount;

    /** 扣除小计 */
    @ApiModelProperty(value = "扣除小计", position = 41)
    private BigDecimal minusAmount;

    /** 合计金额 (嘉奖小计 - 扣除小计)*/
    @ApiModelProperty(value = "合计金额", position = 42)
    private BigDecimal totalAmount;

}
