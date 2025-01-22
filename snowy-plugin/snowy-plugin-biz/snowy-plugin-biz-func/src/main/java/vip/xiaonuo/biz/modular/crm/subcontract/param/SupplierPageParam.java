package vip.xiaonuo.biz.modular.crm.subcontract.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 供应商信息查询参数
 *
 * @author pzh
 * @date  2025/01/22 15:52
 **/
@Getter
@Setter
public class SupplierPageParam {

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

    /** 公司名称 */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /** 黑名单标记(1-是黑名单，0-不是黑名单) */
    @ApiModelProperty(value = "黑名单标记(1-是黑名单，0-不是黑名单)")
    private String isBlacklist;

    /** 是否优质供应商（1-是，0-否） */
    @ApiModelProperty(value = "是否优质供应商（1-是，0-否）")
    private String isGood;

    /** 是否首页展示（1-是，0-否） */
    @ApiModelProperty(value = "是否首页展示（1-是，0-否）")
    private String isShow;

    /** 入库时间 */
    @ApiModelProperty(value = "入库时间")
    private String approvedTime;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

    /** 经营范围 */
    @ApiModelProperty(value = "经营范围")
    private String businessScope;
}
