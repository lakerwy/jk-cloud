package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 批量更新 - 月工资标准数据
 */
@Getter
@Setter
public class UpdateInfo {

    /** 主键Id */
    @ApiModelProperty(value = "主键Id", position = 1)
    private String id;

    /** 所属年月 - 不可修改 - 【匹配专用】 */
    @ApiModelProperty(value = "所属年月 - 不可修改 - 【匹配专用】", position = 1)
    private String yearMonth;

    /** 身份证号 - 不可修改 - 【匹配专用】 */
    @ApiModelProperty(value = "身份证号 - 不可修改 - 【匹配专用】", position = 2)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 3)
    private String userName;

    /** 所属公司|工资发放公司 */
    @ApiModelProperty(value = "所属公司|工资发放公司", position = 4)
    private String company;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 5)
    private String firstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 6)
    private String orgName;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 7)
    private String position;

    /** 入职日期 */
    @ApiModelProperty(value = "入职日期", position = 8)
    private String hireDate;

    /** 离职日期 */
    @ApiModelProperty(value = "离职日期", position = 9)
    private String leaveDate;

    /** 转正日期 */
    @ApiModelProperty(value = "转正日期", position = 10)
    private String positiveTime;

    /** 生日 */
    @ApiModelProperty(value = "生日", position = 11)
    private String birthDay;

    /** 基本工资 */
    @ApiModelProperty(value = "基本工资", position = 12)
    private BigDecimal basicAmount;

    /** 岗位工资 */
    @ApiModelProperty(value = "岗位工资", position = 13)
    private BigDecimal postAmount;

    /** 绩效工资 */
    @ApiModelProperty(value = "绩效工资", position = 14)
    private BigDecimal meritAmount;

    /** 月度标准工资 */
    @ApiModelProperty(value = "月度标准工资", position = 15)
    private BigDecimal standardAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 16)
    private String memo;

    /** 当月变动 */
    @ApiModelProperty(value = "当月变动", position = 17)
    private String monthChange;

    /** 员工类别：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类别：总部|分公司|外部", position = 18)
    private String userType;

    /** 是否有生日福利：是 否*/
    @ApiModelProperty(value = "是否有生日福利：是 否", position = 19)
    private String haveBirthdayPay;

    /** 是否有工龄工资：是 否*/
    @ApiModelProperty(value = "是否有工龄工资：是 否", position = 20)
    private String haveSeniorityPay;

    /** 绩效系数 */
    @ApiModelProperty(value = "绩效系数", position = 21)
    private Float meritRate;

    /** 是否重点关注 */
    @ApiModelProperty(value = "是否重点关注", position = 22)
    private String isSpec;

    /** 是否实习生 */
    @ApiModelProperty(value = "是否实习生", position = 23)
    private String isTry;

    /** 错误信息 */
    @ApiModelProperty(value = "错误信息", position = 24)
    private String errorMsg;

}
