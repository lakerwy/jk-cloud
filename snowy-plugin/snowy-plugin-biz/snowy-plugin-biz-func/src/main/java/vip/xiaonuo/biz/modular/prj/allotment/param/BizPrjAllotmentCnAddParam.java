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
package vip.xiaonuo.biz.modular.prj.allotment.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-项目款拨付申请单-出纳支付信息添加参数
 *
 * @author yong.ye
 * @date  2023/11/20 11:13
 **/
@Getter
@Setter
public class BizPrjAllotmentCnAddParam {

    /** 出纳支付信息-本次收款单位编号 */
    @ApiModelProperty(value = "出纳支付信息-本次收款单位编号", position = 2)
    private String unitCode;

    /** 出纳支付信息-收款款单位名称 */
    @ApiModelProperty(value = "出纳支付信息-收款款单位名称", position = 3)
    private String unitName;

    /** 出纳支付信息-本次付款合同编号 */
    @ApiModelProperty(value = "出纳支付信息-本次付款合同编号", position = 4)
    private String contractNo;

    /** 出纳支付信息-合同名称 */
    @ApiModelProperty(value = "出纳支付信息-合同名称", position = 5)
    private String contractName;

    /** 出纳支付信息-本次实际付款金额 */
    @ApiModelProperty(value = "出纳支付信息-本次实际付款金额", position = 6)
    private BigDecimal realAmount;

    /** 出纳支付信息-实际付款日期 */
    @ApiModelProperty(value = "出纳支付信息-实际付款日期", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date realDate;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String memo;

    /** 项目款拨付申请单主键BIZ_PRJ_ALLOTMENT.Id */
    @ApiModelProperty(value = "项目款拨付申请单主键BIZ_PRJ_ALLOTMENT.Id", position = 14)
    private String allotmentId;

}
