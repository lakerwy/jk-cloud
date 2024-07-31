package vip.xiaonuo.biz.modular.prj.expense.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 成本一览表查询参数
 *
 * @author yong.ye
 * @date  2024/07/30 15:32
 **/
@Getter
@Setter
public class BizExpCostPageParam {

    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 项目编号 */
    @ApiModelProperty(value = "项目编号")
    private String prjNo;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称")
    private String prjName;

    /** 明细科目Id */
    @ApiModelProperty(value = "明细科目Id")
    private String itemId;

    /** 明细科目 */
    @ApiModelProperty(value = "明细科目")
    private String item;

}
