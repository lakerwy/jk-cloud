package vip.xiaonuo.biz.modular.prj.exp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ExpMonthTotal {

    /** 公司 */
    @ApiModelProperty(value = "公司", position = 2)
    private String company;

    /** 部门Id */
    @ApiModelProperty(value = "部门Id", position = 3)
    private String orgId;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 4)
    private String orgName;

    /** 费用类型（一级） */
    @ApiModelProperty(value = "费用类型（一级）", position = 5)
    private String expItem;

    /** 费用类型（二级） */
    @ApiModelProperty(value = "费用类型（二级）", position = 6)
    private String secondExpItem;

    /** 金额（财务核定金额 - 进项税） */
    @ApiModelProperty(value = "金额（财务核定金额 - 进项税）", position = 8)
    private BigDecimal amount;

    /** 费用承担部门 */
    @ApiModelProperty(value = "费用承担部门", position = 11)
    private String deptName;

}
