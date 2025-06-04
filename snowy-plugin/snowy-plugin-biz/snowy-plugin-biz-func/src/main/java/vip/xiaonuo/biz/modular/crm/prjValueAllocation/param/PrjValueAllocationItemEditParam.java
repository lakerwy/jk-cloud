package vip.xiaonuo.biz.modular.crm.prjValueAllocation.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值划分明细编辑参数
 *
 * @author pzh
 * @date  2025/06/04 16:48
 **/
@Getter
@Setter
public class PrjValueAllocationItemEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 关联-项目产值划分主键ID */
    @ApiModelProperty(value = "关联-项目产值划分主键ID", position = 2)
    private String allocationId;

    /** 主合同ID */
    @ApiModelProperty(value = "主合同ID", position = 3)
    private String hetongId;

    /** 单位类型 */
    @ApiModelProperty(value = "单位类型", position = 4)
    private String unitType;

    /** 单位名称 */
    @ApiModelProperty(value = "单位名称", position = 5)
    private String unitName;

    /** 负责人 */
    @ApiModelProperty(value = "负责人", position = 6)
    private String leader;

    /** 负责人ID */
    @ApiModelProperty(value = "负责人ID", position = 7)
    private String leaderId;

    /** 产值划分比例 */
    @ApiModelProperty(value = "产值划分比例", position = 8)
    private BigDecimal allocationRate;

    /** 产值划分金额 */
    @ApiModelProperty(value = "产值划分金额", position = 9)
    private BigDecimal allocationAmount;

    /** 费用类型 */
    @ApiModelProperty(value = "费用类型", position = 10)
    private String feeType;

    /** 费用比例 */
    @ApiModelProperty(value = "费用比例", position = 11)
    private BigDecimal feeRate;

    /** 费用金额 */
    @ApiModelProperty(value = "费用金额", position = 12)
    private BigDecimal feeAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 14)
    private String attach;

}
