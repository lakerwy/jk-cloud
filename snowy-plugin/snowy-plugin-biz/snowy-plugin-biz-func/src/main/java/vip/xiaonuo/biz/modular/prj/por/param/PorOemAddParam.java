package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 经营费管理添加参数
 *
 * @author yy
 * @date  2025/06/03 09:56
 **/
@Getter
@Setter
public class PorOemAddParam {

    /** 收入合同- 主键Id */
    @ApiModelProperty(value = "收入合同- 主键Id", position = 2)
    private String hetongId;

    /** 经营费比例 */
    @ApiModelProperty(value = "经营费比例", position = 3)
    private Double oemRate;

    /** 应收经营费总金额 */
    @ApiModelProperty(value = "应收经营费总金额", position = 4)
    private BigDecimal oemTotalAmount;

    /** 累计结算金额 */
    @ApiModelProperty(value = "累计结算金额", position = 5)
    private BigDecimal oemSettleAmount;

    /** 累计付款金额 */
    @ApiModelProperty(value = "累计付款金额", position = 5)
    private BigDecimal oemPayAmount;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 6)
    private String oemAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String oemMemo;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 12)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 13)
    private String attach;

}
