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
 * 工资系统 - 无工资人员社保|公积金(不参与工资计算)添加参数
 *
 * @author yong.ye
 * @date  2024/06/17 16:06
 **/
@Getter
@Setter
public class SalaryNoPayInsGjjAddParam {

    /** 类别：社保|公积金 */
    @ApiModelProperty(value = "类别：无工资-社保|无工资-公积金", position = 2)
    private String type;

    /** 员工类型：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类型：总部|分公司|外部", position = 3)
    private String userType;

    /** 所属公司 */
    @ApiModelProperty(value = "所属公司", position = 4)
    private String company;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 5)
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 6)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 7)
    private String userName;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 8)
    private String firstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 9)
    private String orgName;

    /** 职位|岗位 */
    @ApiModelProperty(value = "职位|岗位", position = 10)
    private String position;

    /** 缴纳单位 */
    @ApiModelProperty(value = "缴纳单位", position = 11)
    private String unitName;

    /** 缴费基数 */
    @ApiModelProperty(value = "缴费基数", position = 12)
    private BigDecimal base;

    /** 社保-应缴金额|公积金-月缴存额 */
    @ApiModelProperty(value = "社保-应缴金额|公积金-月缴存额 ", position = 13)
    private BigDecimal needPay;

    /** 社保 - 合计-个人|公积金 - 个人实际缴纳 */
    @ApiModelProperty(value = "社保 - 合计-个人|公积金 - 个人实际缴纳", position = 14)
    private BigDecimal per;

    /** 社保 - 合计-单位|公积金 - 单位实际缴纳 */
    @ApiModelProperty(value = "社保 - 合计-单位|公积金 - 单位实际缴纳", position = 15)
    private BigDecimal unit;

    /** 社保 - 合计-应缴总金额|公积金 - 差额 */
    @ApiModelProperty(value = "社保 - 合计-应缴总金额|公积金 - 差额", position = 16)
    private BigDecimal amount;

    /** 社保 - 养老-个人 */
    @ApiModelProperty(value = "社保 - 养老-个人", position = 17)
    private BigDecimal ynPer;

    /** 社保 - 养老-单位 */
    @ApiModelProperty(value = "社保 - 养老-单位", position = 18)
    private BigDecimal ynUnit;

    /** 社保 - 失业-个人 */
    @ApiModelProperty(value = "社保 - 失业-个人", position = 19)
    private BigDecimal syPer;

    /** 社保 - 失业-单位 */
    @ApiModelProperty(value = "社保 - 失业-单位", position = 20)
    private BigDecimal syUnit;

    /** 社保 - 工伤-单位 */
    @ApiModelProperty(value = "社保 - 工伤-单位", position = 21)
    private BigDecimal gsUnit;

    /** 社保 - 医疗-个人 */
    @ApiModelProperty(value = "社保 - 医疗-个人", position = 22)
    private BigDecimal ylPer;

    /** 社保 - 医疗-单位 */
    @ApiModelProperty(value = "社保 - 医疗-单位", position = 23)
    private BigDecimal ylUnit;

    /** 社保 - 医疗-大额医疗 */
    @ApiModelProperty(value = "社保 - 医疗-大额医疗", position = 24)
    private BigDecimal ylSale;

    /** 公积金 - 系统个人缴纳 */
    @ApiModelProperty(value = "公积金 - 系统个人缴纳", position = 25)
    private BigDecimal sysPer;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 26)
    private String memo;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 33)
    private String dataScopeId;

    /** 留痕备注 */
    @ApiModelProperty(value = "留痕备注", position = 34)
    private String note;

}
