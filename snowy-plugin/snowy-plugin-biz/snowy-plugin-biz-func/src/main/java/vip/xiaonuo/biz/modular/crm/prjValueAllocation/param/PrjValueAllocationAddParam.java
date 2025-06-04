package vip.xiaonuo.biz.modular.crm.prjValueAllocation.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目产值划分添加参数
 *
 * @author pzh
 * @date  2025/06/04 16:35
 **/
@Getter
@Setter
public class PrjValueAllocationAddParam {

    /** 主合同ID */
    @ApiModelProperty(value = "主合同ID", position = 2)
    private String hetongId;

    /** 主合同名称 */
    @ApiModelProperty(value = "主合同名称", position = 3)
    private String contractName;

    /** 主合同编号 */
    @ApiModelProperty(value = "主合同编号", position = 4)
    private String contractNo;

    /** 建科所占金额 */
    @ApiModelProperty(value = "建科所占金额", position = 5)
    private BigDecimal contractAmount;

    /** 行业分类 */
    @ApiModelProperty(value = "行业分类", position = 6)
    private String industryClassification;

    /** 项目类型 */
    @ApiModelProperty(value = "项目类型", position = 7)
    private String projectType;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 9)
    private String attach;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 17)
    private String status;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 18)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 19)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 20)
    private String processInstanceId;

    /** 产值划分明细 */
    @ApiModelProperty(value = "产值划分明细列表", position = 30)
    private List<PrjValueAllocationItemAddParam> prjValueAllocationItemList;

}
