package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 项目现场管理费用 - 分类明细添加参数
 *
 * @author yy
 * @date  2025/06/03 09:47
 **/
@Getter
@Setter
public class PorSiteFeeItemAddParam {

    /** 项目现场管理费用- 主键Id */
    @ApiModelProperty(value = "项目现场管理费用- 主键Id", position = 2)
    private String porSiteFeeId;

    /** 归属月份 */
    @ApiModelProperty(value = "归属月份", position = 3)
    private String feeMonth;

    /** 费用类型 */
    @ApiModelProperty(value = "费用类型", position = 4)
    private String feeType;

    /** 金额 */
    @ApiModelProperty(value = "金额", position = 5)
    private BigDecimal feeAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 11)
    private String attach;

}
