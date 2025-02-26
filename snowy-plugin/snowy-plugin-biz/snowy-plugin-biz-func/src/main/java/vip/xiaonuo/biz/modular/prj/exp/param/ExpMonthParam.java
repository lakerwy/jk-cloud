package vip.xiaonuo.biz.modular.prj.exp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class ExpMonthParam {

    /** 年月 */
    @ApiModelProperty(value = "年月", required = true, position = 1)
    @NotBlank(message = "年月不能为空")
    private String yearMonth;

    /** 报销类型: 费用报销单 FEE  差旅费用报销单 TRAVEL */
    @ApiModelProperty(value = "报销类型: 费用报销单 FEE  差旅费用报销单 TRAVEL",required = true, position = 2)
    @NotBlank(message = "报销类型不能为空")
    private String expType;

    /** 公司 */
    @ApiModelProperty(value = "公司", position = 3)
    private String company;

    /** 部门Id */
    @ApiModelProperty(value = "部门Id", position = 4)
    private String orgId;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 5)
    private String orgName;

    /** 费用类型（一级） */
    @ApiModelProperty(value = "费用类型（一级）", position = 6)
    private String expItem;

    /** 费用类型（二级） */
    @ApiModelProperty(value = "费用类型（二级）", position = 7)
    private String secondExpItem;


}
