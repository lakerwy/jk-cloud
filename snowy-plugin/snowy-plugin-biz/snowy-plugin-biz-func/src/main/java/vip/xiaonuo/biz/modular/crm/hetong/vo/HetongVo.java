package vip.xiaonuo.biz.modular.crm.hetong.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 导出列表展示字段
 */

@Getter
@Setter
public class HetongVo {
    /** 合同编号 */
    @ApiModelProperty(value = "合同编号", position = 1)
    private String contractNo;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 2)
    private String projectName;

    /** 立项-行业分类 */
    @ApiModelProperty(value = "立项-行业分类", position = 3)
    private String industryClassification;

    /** 立项-项目类型 */
    @ApiModelProperty(value = "立项-项目类型", position = 4)
    private String bizProjectType;

    /** 立项-是否联合体 */
    @ApiModelProperty(value = "立项-是否联合体", position = 5)
    private String isConsortium;

    /** 立项-是否EPC */
    @ApiModelProperty(value = "立项-是否EPC", position = 6)
    private String isEpc;

    /** 类别（旧） */
    @ApiModelProperty(value = "类别（旧）", position = 7)
    private String secondLevel;

    /** 合同金额 - 建科所占金额 */
    @ApiModelProperty(value = "合同金额 - 建科所占金额 ", position = 8)
    private BigDecimal contractAmount;

    /** 甲方（合同相对方） */
    @ApiModelProperty(value = "甲方（合同相对方）", position = 9)
    private String counterparty;

    /** 投资规模 */
    @ApiModelProperty(value = "投资规模", position = 10)
    private String investScale;

    /** 合作伙伴责任单位（责任部门） */
    @ApiModelProperty(value = "合作伙伴责任单位（责任部门）", position = 11)
    private String companyOrDepartment;

    /** 合作伙伴责任人（责任人） */
    @ApiModelProperty(value = "合作伙伴责任人（责任人）", position = 12)
    private String owner;

    /** 合同签订人 */
    @ApiModelProperty(value = "合同签订人", position = 13)
    private String register;

    /** 合同盖章日期 */
    @ApiModelProperty(value = "合同盖章日期", position = 14)
    private Date sealDate;

    /** 归档日期 */
    @ApiModelProperty(value = "归档日期", position = 15)
    private Date filingDate;

    /** 是否归档 */
    @ApiModelProperty(value = "是否归档", position = 16)
    private String isFiling;

    /** 项目状态 */
    @ApiModelProperty(value = "项目状态", position = 17)
    private String htstatus;

    /** 立项-建设地点 */
    @ApiModelProperty(value = "立项-建设地点", position = 18)
    private String bizProjectLocation;

    /** 立项-所属大区 */
    @ApiModelProperty(value = "立项-所属大区", position = 19)
    private String region;

}
