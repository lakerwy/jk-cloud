package vip.xiaonuo.biz.modular.prj.mou.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目承接备忘录 - 项目生产内部分工查询参数
 *
 * @author yy
 * @date  2025/06/06 11:43
 **/
@Getter
@Setter
public class BizPrjMouItemPageParam {

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

    /** 项目备忘录Id */
    @ApiModelProperty(value = "项目备忘录Id")
    private String bizPrjMouId;

    /** 承包类型 */
    @ApiModelProperty(value = "承包类型")
    private String mouType;

    /** 经办部门Id */
    @ApiModelProperty(value = "经办部门Id")
    private String deptId;

    /** 经办部门 */
    @ApiModelProperty(value = "经办部门")
    private String deptName;

    /** 工作内容 */
    @ApiModelProperty(value = "工作内容")
    private String content;

}
