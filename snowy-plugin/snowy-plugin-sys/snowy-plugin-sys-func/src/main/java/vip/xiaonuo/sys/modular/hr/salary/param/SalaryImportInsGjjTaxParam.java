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
 * 工资系统-社保|公积金|专项扣除导入
 *
 * @author yong.ye
 * @date  2024/02/26 09:31
 **/
@Getter
@Setter
public class SalaryImportInsGjjTaxParam {

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

    /** 所属单位 */
    @ApiModelProperty(value = "所属单位", position = 10)
    private String unitName;

    /** 缴费基数 */
    @ApiModelProperty(value = "缴费基数", position = 34)
    private BigDecimal base;

    /** 应缴金额|月缴存额 */
    @ApiModelProperty(value = "应缴金额|月缴存额", position = 35)
    private BigDecimal needPay;

    /** 合计-个人|个人实际缴纳 */
    @ApiModelProperty(value = "合计-个人|个人实际缴纳", position = 36)
    private BigDecimal per;

    /** 合计-单位|单位实际缴纳 */
    @ApiModelProperty(value = "合计-单位|单位实际缴纳", position = 37)
    private BigDecimal unit;

    /** 合计-应缴总金额|差额|合计 */
    @ApiModelProperty(value = "合计-应缴总金额|差额|合计", position = 38)
    private BigDecimal amount;

    /** 养老-个人 */
    @ApiModelProperty(value = "养老-个人", position = 39)
    private BigDecimal ynPer;

    /** 养老-单位 */
    @ApiModelProperty(value = "养老-单位", position = 40)
    private BigDecimal ynUnit;

    /** 失业-个人 */
    @ApiModelProperty(value = "失业-个人", position = 41)
    private BigDecimal syPer;

    /** 失业-单位 */
    @ApiModelProperty(value = "失业-单位", position = 42)
    private BigDecimal syUnit;

    /** 工伤-单位 */
    @ApiModelProperty(value = "工伤-单位", position = 43)
    private BigDecimal gsUnit;

    /** 医疗-个人 */
    @ApiModelProperty(value = "医疗-个人", position = 44)
    private BigDecimal ylPer;

    /** 医疗-单位 */
    @ApiModelProperty(value = "医疗-单位", position = 45)
    private BigDecimal ylUnit;

    /** 医疗-大额医疗 */
    @ApiModelProperty(value = "医疗-大额医疗", position = 46)
    private BigDecimal ylSale;

    /** 系统个人缴纳 */
    @ApiModelProperty(value = "系统个人缴纳", position = 47)
    private BigDecimal sysPer;

    /** 赡养老人 */
    @ApiModelProperty(value = "赡养老人", position = 48)
    private BigDecimal taxSy;

    /** 子女教育 */
    @ApiModelProperty(value = "子女教育", position = 49)
    private BigDecimal taxZn;

    /** 房贷利息 */
    @ApiModelProperty(value = "房贷利息", position = 50)
    private BigDecimal taxFd;

    /** 住房租金 */
    @ApiModelProperty(value = "住房租金", position = 51)
    private BigDecimal taxZf;

    /** 继续教育 */
    @ApiModelProperty(value = "继续教育", position = 52)
    private BigDecimal taxJy;

    /** 婴幼儿照护 */
    @ApiModelProperty(value = "婴幼儿照护", position = 53)
    private BigDecimal taxZh;

    /** 当月变动|备注 */
    @ApiModelProperty(value = "备注", position = 54)
    private String memo;
}
