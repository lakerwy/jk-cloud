package vip.xiaonuo.biz.modular.report.result;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/7/26 16:16
 */

@Getter
@Setter
public class HTFilingPageResult {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 是否归档 */
    @ApiModelProperty(value = "是否归档", position = 4)
    private String isFiling;

    /** 工程类别 */
    @ApiModelProperty(value = "工程类别", position = 5)
    private String secondLevel;

    /** 合同编号 */
    @ApiModelProperty(value = "合同编号", position = 6)
    private String contractNo;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 7)
    private String projectName;

    /** 甲方 */
    @ApiModelProperty(value = "甲方", position = 8)
    private String counterparty;

    /** 乙方 */
    @ApiModelProperty(value = "乙方", position = 9)
    private String secondparty;

    /** 合作伙伴责任单位 */
    @ApiModelProperty(value = "合作伙伴责任单位", position = 12)
    private String companyOrDepartment;

    /** 合同盖章日期 */
    @ApiModelProperty(value = "合同盖章日期", position = 17)
    private Date sealDate;

    /** 归档日期 */
    @ApiModelProperty(value = "归档日期", position = 18)
    private Date filingDate;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 19)
    private BigDecimal contractAmount;

    /** 所属责任单位： 合同总数 */
    @ApiModelProperty(value = "所属责任单位：合同总数", position = 52)
    private Integer totalNum;

    /** 所属责任单位：归档数 */
    @ApiModelProperty(value = "所属责任单位：归档数", position = 53)
    private Integer filingNum;

    /** 所属责任单位：未归档数 */
    @ApiModelProperty(value = "所属责任单位：未归档数", position = 54)
    private Integer unFilingNum;

    /** 所属责任单位：归档率 */
    @ApiModelProperty(value = "所属责任单位：归档率", position = 55)
    private Double filingRate;

    /** 所属责任单位：超过3个月未归档数 */
    @ApiModelProperty(value = "所属责任单位：超过3个月未归档数", position = 56)
    private Integer threeUnFilingNum;
}
