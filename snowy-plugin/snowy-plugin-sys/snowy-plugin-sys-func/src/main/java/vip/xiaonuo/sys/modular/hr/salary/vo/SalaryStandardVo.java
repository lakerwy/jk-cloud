package vip.xiaonuo.sys.modular.hr.salary.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 月度工资标准 实体对象 (简化信息)
 */

@Getter
@Setter
public class SalaryStandardVo {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 3)
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 4)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 5)
    private String userName;

    /** 所属公司|工资发放公司 */
    @ApiModelProperty(value = "所属公司|工资发放公司", position = 5)
    private String company;

    /** 建科云用户Id */
    @ApiModelProperty(value = "建科云用户Id", position = 6)
    private String userId;

    /** 花名册Id */
    @ApiModelProperty(value = "花名册Id", position = 6)
    private String personnelId;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 7)
    private String firstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 9)
    private String orgName;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 11)
    private String position;

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

    /** 基本工资 */
    @ApiModelProperty(value = "基本工资", position = 15)
    private BigDecimal basicAmount;

    /** 岗位工资 */
    @ApiModelProperty(value = "岗位工资", position = 16)
    private BigDecimal postAmount;

    /** 绩效工资 */
    @ApiModelProperty(value = "绩效工资", position = 17)
    private BigDecimal meritAmount;

    /** 月度标准工资 */
    @ApiModelProperty(value = "月度标准工资", position = 18)
    private BigDecimal standardAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 20)
    private String memo;

    /** 当月变动 */
    @ApiModelProperty(value = "当月变动", position = 20)
    private String monthChange;

    /** 员工类别：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类别：总部|分公司|外部", position = 28)
    private String userType;

    /** 是否有生日福利：是 否*/
    @ApiModelProperty(value = "是否有生日福利：是 否", position = 29)
    private String haveBirthdayPay;

    /** 是否有工龄工资：是 否*/
    @ApiModelProperty(value = "是否有工龄工资：是 否", position = 30)
    private String haveSeniorityPay;

    /** 绩效系数 */
    @ApiModelProperty(value = "绩效系数", position = 31)
    private Float meritRate;

    /** 是否重点关注 */
    @ApiModelProperty(value = "是否重点关注", position = 32)
    private String isSpec;

    /** 是否实习生 */
    @ApiModelProperty(value = "是否实习生", position = 33)
    private String isTry;
}
