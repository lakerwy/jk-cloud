package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理查询参数
 *
 * @author yy
 * @date  2025/06/03 09:39
 **/
@Getter
@Setter
public class PorProjectPageParam {

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

    /** 立项Id */
    @ApiModelProperty(value = "立项Id")
    private String projectId;

    /** 项目状态 */
    @ApiModelProperty(value = "项目状态")
    private String projectStatus;

    /** 项目类型（BizProject.type） */
    @ApiModelProperty(value = "项目类型（BizProject.type）")
    private String bizPrjType;


    /** 已作废、已结项、已完工，这三个状态的项目默认不显示  1 默认不展示  0 不做限制 */
    @ApiModelProperty(value = "已作废、已结项、已完工，这三个状态的项目默认不显示  1 默认不展示  0 不做限制 ")
    private String isHidden;
}
