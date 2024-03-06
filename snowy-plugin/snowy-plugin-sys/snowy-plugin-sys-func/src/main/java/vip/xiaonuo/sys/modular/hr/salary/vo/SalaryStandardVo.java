package vip.xiaonuo.sys.modular.hr.salary.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 类别：工资|奖金 */
    @ApiModelProperty(value = "类别：工资|奖金", position = 2)
    private String type;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 3)
    @TableField("`YEAR_MONTH`")
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 4)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 5)
    private String userName;

    /** 公司 */
    @ApiModelProperty(value = "公司", position = 5)
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

    /** 一级部门Id */
    @ApiModelProperty(value = "一级部门Id", position = 8)
    private String firstOrgId;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 9)
    private String orgName;

    /** 二级部门Id */
    @ApiModelProperty(value = "二级部门Id", position = 10)
    private String orgId;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 11)
    private String position;

    /** 职位Id */
    @ApiModelProperty(value = "职位Id", position = 12)
    private String positionId;

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

    /** 当月变动 */
    @ApiModelProperty(value = "当月变动", position = 20)
    private String memo;

    /** 员工类别：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类别：总部|分公司|外部", position = 28)
    private String userType;

    /** 是否有生日福利：是 否*/
    @ApiModelProperty(value = "是否有生日福利：是 否", position = 29)
    private String haveBirthdayPay;

    /** 是否有工龄工资：是 否*/
    @ApiModelProperty(value = "是否有工龄工资：是 否", position = 30)
    private String haveSeniorityPay;

}
