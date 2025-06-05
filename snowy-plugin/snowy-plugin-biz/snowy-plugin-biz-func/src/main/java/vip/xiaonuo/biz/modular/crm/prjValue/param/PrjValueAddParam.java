package vip.xiaonuo.biz.modular.crm.prjValue.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 生产院及子公司产值添加参数
 *
 * @author pzh
 * @date  2025/06/05 10:04
 **/
@Getter
@Setter
public class PrjValueAddParam {

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 2)
    private String deptName;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 3)
    private String deptId;

    /** 年份（例如：2025） */
    @ApiModelProperty(value = "年份（例如：2025）", position = 4)
    private String year;

    /** 季度（例如：1） */
    @ApiModelProperty(value = "季度（例如：1）", position = 5)
    private String season;

    /** 经营产值 */
    @ApiModelProperty(value = "经营产值", position = 6)
    private BigDecimal operateAmount;

    /** 生产产值 */
    @ApiModelProperty(value = "生产产值", position = 7)
    private BigDecimal productAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 9)
    private String attach;

}
