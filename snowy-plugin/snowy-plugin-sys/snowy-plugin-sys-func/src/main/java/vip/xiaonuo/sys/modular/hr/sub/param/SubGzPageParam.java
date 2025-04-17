package vip.xiaonuo.sys.modular.hr.sub.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司-工资发放（流程）查询参数
 *
 * @author yong.ye
 * @date  2025/02/06 09:09
 **/
@Getter
@Setter
public class SubGzPageParam {

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

    /** 部门Id */
    @ApiModelProperty(value = "部门Id")
    private String orgId;

    /** 部门 */
    @ApiModelProperty(value = "部门")
    private String orgName;

    /** 所属月份 */
    @ApiModelProperty(value = "所属月份")
    private String yM;

    /** 所属大区 */
    @ApiModelProperty(value = "所属大区")
    private String region;

    /** 大区经理 */
    @ApiModelProperty(value = "大区经理")
    private String managerName;

    /** SN */
    @ApiModelProperty(value = "SN")
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

    /** 类型：工资|奖金|工资+奖金|经济补偿金 */
    @ApiModelProperty(value = "类型：工资|奖金|工资+奖金|经济补偿金")
    private String type;

}
