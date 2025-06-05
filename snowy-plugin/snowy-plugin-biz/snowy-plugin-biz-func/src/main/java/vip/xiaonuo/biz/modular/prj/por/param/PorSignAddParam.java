package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 签字审图费管理添加参数
 *
 * @author yy
 * @date  2025/06/03 09:54
 **/
@Getter
@Setter
public class PorSignAddParam {

    /** 收入合同- 主键Id */
    @ApiModelProperty(value = "收入合同- 主键Id", position = 2)
    private String hetongId;

    /** 签字费比例 */
    @ApiModelProperty(value = "签字费比例", position = 3)
    private Double signRate;

    /** 应收签字费总金额 */
    @ApiModelProperty(value = "应收签字费总金额", position = 4)
    private BigDecimal signTotalAmount;

    /** 累计结算金额 */
    @ApiModelProperty(value = "累计结算金额", position = 5)
    private BigDecimal signSettleAmount;

    /** 累计收取金额 */
    @ApiModelProperty(value = "累计收取金额", position = 5)
    private BigDecimal signPayAmount;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 6)
    private String signAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String signMemo;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 12)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 13)
    private String attach;

}
