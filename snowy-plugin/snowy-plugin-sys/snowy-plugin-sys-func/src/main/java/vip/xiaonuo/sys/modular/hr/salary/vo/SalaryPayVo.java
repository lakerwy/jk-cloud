package vip.xiaonuo.sys.modular.hr.salary.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryPay;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  福利
 */

@Getter
@Setter
public class SalaryPayVo extends SalaryPay {

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 101)
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 102)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 103)
    private String userName;

    /** 公司 */
    @ApiModelProperty(value = "公司", position = 104)
    private String company;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 105)
    private String firstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 106)
    private String orgName;

    /** 入职日期 */
    @ApiModelProperty(value = "入职日期", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 离职日期 */
    @ApiModelProperty(value = "离职日期", position = 14)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leaveDate;

    /** 转正日期 */
    @ApiModelProperty(value = "转正日期", position = 14)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date positiveTime;

    /** 生日 */
    @ApiModelProperty(value = "生日", position = 14)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;

    /** 月度标准工资 */
    @ApiModelProperty(value = "月度标准工资", position = 18)
    private BigDecimal standardAmount;

    /** 员工类别：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类别：总部|分公司|外部", position = 28)
    private String userType;

}
