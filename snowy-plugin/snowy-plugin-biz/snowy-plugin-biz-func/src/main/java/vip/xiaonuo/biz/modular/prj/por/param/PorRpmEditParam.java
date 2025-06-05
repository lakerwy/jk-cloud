package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 责任上缴费用管理编辑参数
 *
 * @author yy
 * @date  2025/06/03 09:55
 **/
@Getter
@Setter
public class PorRpmEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 收入合同- 主键Id */
    @ApiModelProperty(value = "收入合同- 主键Id", position = 2)
    private String hetongId;

    /** 责任上缴比例 */
    @ApiModelProperty(value = "责任上缴比例", position = 3)
    private Double rpmRate;

    /** 责任上缴总金额 */
    @ApiModelProperty(value = "责任上缴总金额", position = 4)
    private BigDecimal rpmTotalAmount;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 5)
    private String rpmAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 6)
    private String rpmMemo;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 11)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 12)
    private String attach;

}
