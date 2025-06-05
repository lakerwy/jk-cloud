package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 项目计划管理查询参数
 *
 * @author yy
 * @date  2025/06/03 09:42
 **/
@Getter
@Setter
public class PorTaskPageParam {

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

    /** 项目产值台账 - 项目管理 - 主键Id */
    @ApiModelProperty(value = "项目产值台账 - 项目管理 - 主键Id")
    private String porProjectId;

    /** 任务名称 */
    @ApiModelProperty(value = "任务名称")
    private String taskName;

    /** 责任人id */
    @ApiModelProperty(value = "责任人id")
    private String taskLeaderId;

    /** 责任人 */
    @ApiModelProperty(value = "责任人")
    private String taskLeaderName;

}
