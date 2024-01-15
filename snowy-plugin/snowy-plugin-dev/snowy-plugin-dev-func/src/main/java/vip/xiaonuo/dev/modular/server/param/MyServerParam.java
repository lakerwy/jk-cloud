package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyServerParam {
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

    /** 系统类别 */
    @ApiModelProperty(value = "系统类别")
    private Integer systemType;

    /** EPC单体服务部署状态 */
    @ApiModelProperty(value = "EPC单体服务部署状态")
    private Integer systemStatus;

    /** 系统名称 */
    @ApiModelProperty(value = "系统名称")
    private String systemName;
}
