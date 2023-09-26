package vip.xiaonuo.biz.modular.report.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/7/26 16:06
 */

@Getter
@Setter
public class HtFilingPageParam {
    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 是否归档 */
    @ApiModelProperty(value = "是否归档")
    private String isFiling;

    /** 工程类别 */
    @ApiModelProperty(value = "工程类别")
    private String secondLevel;

    /** 合同编号（历史表Hetong.ContractNo） */
    @ApiModelProperty(value = "合同编号")
    private String contractNo;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称")
    private String projectName;

    /** 甲方 */
    @ApiModelProperty(value = "甲方")
    private String counterparty;

    /** 乙方 */
    @ApiModelProperty(value = "乙方")
    private String secondparty;

    /** 合作伙伴类型 */
    @ApiModelProperty(value = "合作伙伴类型")
    private String partnerType;

    /** 合作伙伴责任单位 */
    @ApiModelProperty(value = "合作伙伴责任单位")
    private String companyOrDepartment;

    /** 合作伙伴责任人 */
    @ApiModelProperty(value = "合作伙伴责任人")
    private String owner;

    /** 合同盖章日期开始 */
    @ApiModelProperty(value = "合同盖章日期开始")
    private String startSealDate;

    /** 合同盖章日期结束 */
    @ApiModelProperty(value = "合同盖章日期结束")
    private String endSealDate;

    /** 丙方 */
    @ApiModelProperty(value = "丙方")
    private String partyc;
}
