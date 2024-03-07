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

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 工资系统-公积金明细编辑参数
 *
 * @author yong.ye
 * @date  2024/02/26 09:28
 **/
@Getter
@Setter
public class SalaryGjjEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 【外键】发放工资员工Id：HR_SALARY_STANDARD.ID */
    @ApiModelProperty(value = "【外键】发放工资员工Id：HR_SALARY_STANDARD.ID", position = 3)
    private String standardId;

    /** 发放公司 */
    @ApiModelProperty(value = "发放公司", position = 10)
    private String unitName;

    /** 缴费基数 */
    @ApiModelProperty(value = "缴费基数", position = 11)
    private BigDecimal base;

    /** 月缴存额 */
    @ApiModelProperty(value = "月缴存额", position = 12)
    private BigDecimal needPay;

    /** 个人实际缴纳 */
    @ApiModelProperty(value = "个人实际缴纳", position = 13)
    private BigDecimal per;

    /** 单位实际缴纳 */
    @ApiModelProperty(value = "单位实际缴纳", position = 14)
    private BigDecimal unit;

    /** 差额 */
    @ApiModelProperty(value = "差额", position = 15)
    private BigDecimal amount;

    /** 系统个人缴纳 */
    @ApiModelProperty(value = "系统个人缴纳", position = 16)
    private BigDecimal sysPer;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 17)
    private String memo;

}
