package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司评级（二期）- 分公司评级填报查询参数
 *
 * @author yong.ye
 * @date  2025/03/18 08:48
 **/
@Getter
@Setter
public class BranchFillPageParam {

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

    /** 分公司id */
    @ApiModelProperty(value = "分公司id")
    private String branchId;

    /** 分公司名称 */
    @ApiModelProperty(value = "分公司名称")
    private String branchName;

    /** 分公司经营范围 */
    @ApiModelProperty(value = "分公司经营范围")
    private String branchScope;

    /** 区域 */
    @ApiModelProperty(value = "区域")
    private String area;

    /** 区域经理 */
    @ApiModelProperty(value = "区域经理")
    private String areaManager;

    /** 级别 */
    @ApiModelProperty(value = "级别")
    private String level;

    /** 是否公示 */
    @ApiModelProperty(value = "是否公示")
    private Integer isShow;

    /** SN */
    @ApiModelProperty(value = "SN")
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

    /** 填报季度 */
    @ApiModelProperty(value = "填报季度")
    private String fillSeason;

}
