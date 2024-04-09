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
package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 导入 其他费用明细 - 数据
 *
 * @author yong.ye
 * @date  2024/02/26 09:31
 **/
@Getter
@Setter
public class SalaryImportAddParam {

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

    /** 字典类型（1 补发、2 补扣） */
    @ApiModelProperty(value = "字典类型（1 补发、2 补扣）", position = 3)
    private String dictType;

    /** 类别：税前|税后 */
    @ApiModelProperty(value = "类别：税前|税后", position = 3)
    private String itemType;

    /** 1 补发、2 补扣 栏目名称 */
    @ApiModelProperty(value = "1 补发、2 补扣 栏目名称", position = 4)
    private String dictName;

    /** 实际金额 */
    @ApiModelProperty(value = "实际金额", position = 6)
    private BigDecimal realAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 所属公司【月度工资标准-所属公司|工资发放公司，便于区分是那个人】 */
    @ApiModelProperty(value = "所属公司【月度工资标准-所属公司|工资发放公司，便于区分是那个人】", position = 1)
    private String company;
}
