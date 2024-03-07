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
 * 工资系统-专项扣除添加参数
 *
 * @author yong.ye
 * @date  2024/02/26 09:38
 **/
@Getter
@Setter
public class SalaryTaxAddParam {

    /** 【外键】发放工资员工Id：HR_SALARY_STANDARD.ID */
    @ApiModelProperty(value = "【外键】发放工资员工Id：HR_SALARY_STANDARD.ID", position = 3)
    private String standardId;

    /** 发放公司 */
    @ApiModelProperty(value = "发放公司", position = 10)
    private String unitName;

    /** 合计 */
    @ApiModelProperty(value = "合计", position = 11)
    private BigDecimal amount;

    /** 赡养老人 */
    @ApiModelProperty(value = "赡养老人", position = 12)
    private BigDecimal taxSy;

    /** 子女教育 */
    @ApiModelProperty(value = "子女教育", position = 13)
    private BigDecimal taxZn;

    /** 房贷利息 */
    @ApiModelProperty(value = "房贷利息", position = 14)
    private BigDecimal taxFd;

    /** 住房租金 */
    @ApiModelProperty(value = "住房租金", position = 15)
    private BigDecimal taxZf;

    /** 继续教育 */
    @ApiModelProperty(value = "继续教育", position = 16)
    private BigDecimal taxJy;

    /** 婴幼儿照护 */
    @ApiModelProperty(value = "婴幼儿照护", position = 17)
    private BigDecimal taxZh;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 18)
    private String memo;

}
