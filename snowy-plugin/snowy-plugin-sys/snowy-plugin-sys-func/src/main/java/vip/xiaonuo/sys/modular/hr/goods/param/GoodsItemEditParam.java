package vip.xiaonuo.sys.modular.hr.goods.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物资领用申请（附表） - 物资清单+退回清单 编辑参数
 *
 * @author yy
 * @date  2025/05/23 14:35
 **/
@Getter
@Setter
public class GoodsItemEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 物资清单|退回清单 */
    @ApiModelProperty(value = "物资清单|退回清单", position = 2)
    private String type;

    /** 物资名称|退回物资名称 */
    @ApiModelProperty(value = "物资名称|退回物资名称", position = 3)
    private String goodName;

    /** 数量 */
    @ApiModelProperty(value = "数量", position = 4)
    private BigDecimal goodNum;

    /** 单价 */
    @ApiModelProperty(value = "单价", position = 5)
    private BigDecimal goodPrice;

    /** 总价 */
    @ApiModelProperty(value = "总价", position = 6)
    private BigDecimal goodTotal;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String goodMemo;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 13)
    private String attachs;

    /** 物资领用申请id */
    @ApiModelProperty(value = "物资领用申请id", position = 16)
    private String goodsApplyId;
}
