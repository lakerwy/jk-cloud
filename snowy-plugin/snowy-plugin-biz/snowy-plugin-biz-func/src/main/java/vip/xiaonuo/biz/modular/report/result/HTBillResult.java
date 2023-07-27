package vip.xiaonuo.biz.modular.report.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/7/26 11:01
 */

@Getter
@Setter
public class HTBillResult {
    /** 合同编号 */
    @ApiModelProperty(value = "合同编号", position = 1)
    private String contractNo;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 2)
    private String projectName;

    /** 工程类别 */
    @ApiModelProperty(value = "工程类别", position = 3)
    private String secondLevel;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 4)
    private BigDecimal contractAmount;

    /** 甲方 */
    @ApiModelProperty(value = "甲方", position = 5)
    private String counterparty;

    /** 乙方 */
    @ApiModelProperty(value = "乙方", position = 6)
    private String secondparty;

    /** 投资规模(万元) */
    @ApiModelProperty(value = "投资规模(万元)", position = 7)
    private String investScale;

    /** 合作伙伴责任单位 */
    @ApiModelProperty(value = "合作伙伴责任单位", position = 8)
    private String companyOrDepartment;

    /** 合作伙伴责任人 */
    @ApiModelProperty(value = "合作伙伴责任人", position = 9)
    private String owner;

    /** 合同签订人 */
    @ApiModelProperty(value = "合同签订人", position = 10)
    private String register;

    /** 合同盖章日期 */
    @ApiModelProperty(value = "合同盖章日期", position = 11)
    private Date sealDate;

    /** 归档日期 */
    @ApiModelProperty(value = "归档日期", position = 12)
    private Date filingDate;

    /** 是否归档（原件归档） */
    @ApiModelProperty(value = "是否归档（原件归档）", position = 13)
    private String isFiling;
}
