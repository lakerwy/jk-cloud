package vip.xiaonuo.sys.modular.hr.school.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 校招 - （应聘者）- 推送部门流程查询参数
 *
 * @author yong.ye
 * @date  2024/09/03 10:47
 **/
@Getter
@Setter
public class SchoolDeptPageParam {

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

    /** 应聘部门Id */
    @ApiModelProperty(value = "应聘部门Id")
    private String deptId;

    /** 应聘部门 */
    @ApiModelProperty(value = "应聘部门")
    private String dept;

    /** 审核人Id */
    @ApiModelProperty(value = "审核人Id")
    private String auditId;

    /** 审核人 */
    @ApiModelProperty(value = "审核人")
    private String audit;

    /** SN */
    @ApiModelProperty(value = "SN")
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

}
