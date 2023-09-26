package vip.xiaonuo.biz.modular.report.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yong.ye
 * @date 2023/7/26 11:15
 */

@Getter
@Setter
public class HtBillParam {

    /** 当前页 */
    @ApiModelProperty(value = "当前页码", position = 1)
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数", position = 2)
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName", position = 3)
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND", position = 4)
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词", position = 5)
    private String searchKey;

    /** 统计开始时间 */
    @ApiModelProperty(value = "开始时间", position = 6)
    private String startDate;

    /** 统计结束时间 */
    @ApiModelProperty(value = "结束时间", position = 7)
    private String endDate;

    /** 统计类型：分公司、零星... */
    @ApiModelProperty(value = "统计类型：分公司、零星... ", position = 8)
    private String type;

    /** 报表类型：
     * 1、近一年收入合同清单（盖章日期2022.3.31-2023.3.31）
     * 2、超过3个月未归档合同数统计表（盖章日期2017.1.1-2023.3.31）
    */
    @ApiModelProperty(value = "报表类型：1、分公司近一年收入合同清单（盖章日期2022.3.31-2023.3.31）  2、分公司超过3个月未归档合同数统计表（盖章日期2017.1.1-2023.3.31）", required = true, position = 9)
    @NotBlank(message = "reportType不能为空")
    private String reportType;
}
