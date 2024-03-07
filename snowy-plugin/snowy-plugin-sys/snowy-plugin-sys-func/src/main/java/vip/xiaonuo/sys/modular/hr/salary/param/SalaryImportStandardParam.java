/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.sys.modular.hr.salary.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 工资系统-员工月工资标准导入
 *
 * @author yong.ye
 * @date  2024/02/26 09:31
 **/
@Getter
@Setter
public class SalaryImportStandardParam {

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 4)
    private String no;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 5)
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 6)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 7)
    private String userName;

    /** 公司 */
    @ApiModelProperty(value = "公司", position = 5)
    private String company;

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
    private String hireDate;

    /** 离职日期 */
    @ApiModelProperty(value = "离职日期", position = 14)
    private String leaveDate;

    /** 转正日期 */
    @ApiModelProperty(value = "转正日期", position = 14)
    private String positiveTime;

    /** 生日 */
    @ApiModelProperty(value = "生日", position = 14)
    private String birthDay;

    /** 基本工资 */
    @ApiModelProperty(value = "基本工资", position = 17)
    private BigDecimal basicAmount;

    /** 岗位工资 */
    @ApiModelProperty(value = "岗位工资", position = 18)
    private BigDecimal postAmount;

    /** 绩效工资 */
    @ApiModelProperty(value = "绩效工资", position = 19)
    private BigDecimal meritAmount;

    /** 月度标准工资 */
    @ApiModelProperty(value = "月度标准工资", position = 20)
    private BigDecimal standardAmount;

    /** 当月变动|备注 */
    @ApiModelProperty(value = "当月变动", position = 54)
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

    /** 绩效系数 */
    @ApiModelProperty(value = "绩效系数", position = 31)
    private Float meritRate;
}
