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

    /** 申请金额 */
    @ApiModelProperty(value = "申请金额", position = 8)
    private BigDecimal amount;

}
