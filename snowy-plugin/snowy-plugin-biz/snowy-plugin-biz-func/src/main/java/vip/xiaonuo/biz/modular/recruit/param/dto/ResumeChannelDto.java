package vip.xiaonuo.biz.modular.recruit.param.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/8/29 10:38
 */

@Getter
@Setter
public class ResumeChannelDto {

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

    /** 所属部门 */
    @ApiModelProperty(value = "所属部门")
    private String orgId;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间")
    private String startDate;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间")
    private String endDate;
}
