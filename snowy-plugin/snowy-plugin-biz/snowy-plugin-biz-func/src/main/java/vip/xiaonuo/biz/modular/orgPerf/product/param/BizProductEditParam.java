package vip.xiaonuo.biz.modular.orgPerf.product.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 产值情况编辑参数
 *
 * @author pzh
 * @date  2025/06/17 09:03
 **/
@Getter
@Setter
public class BizProductEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 2)
    private String orgName;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 3)
    private String orgId;

    /** 年份（例如：2025） */
    @ApiModelProperty(value = "年份（例如：2025）", position = 4)
    private String year;

    /** 季度（例如：1） */
    @ApiModelProperty(value = "季度（例如：1）", position = 5)
    private String season;

    /** 生产产值 */
    @ApiModelProperty(value = "生产产值", position = 6)
    private BigDecimal productAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 8)
    private String attach;

}
