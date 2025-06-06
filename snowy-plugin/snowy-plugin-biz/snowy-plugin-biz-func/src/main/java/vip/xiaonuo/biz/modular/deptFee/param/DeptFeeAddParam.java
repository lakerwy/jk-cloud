package vip.xiaonuo.biz.modular.deptFee.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 部门费用管理添加参数
 *
 * @author pzh
 * @date  2025/06/05 14:56
 **/
@Getter
@Setter
public class DeptFeeAddParam {

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

    /** 部门费用 */
    @ApiModelProperty(value = "部门费用", position = 6)
    private BigDecimal deptFee;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 8)
    private String attach;

    /** 留痕日志 */
    @ApiModelProperty(value = "留痕记录", position = 16)
    private String note;

}
