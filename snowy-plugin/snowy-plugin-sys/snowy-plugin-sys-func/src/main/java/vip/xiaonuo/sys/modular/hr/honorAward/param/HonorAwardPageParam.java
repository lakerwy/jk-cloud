package vip.xiaonuo.sys.modular.hr.honorAward.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 项目成果查询参数
 *
 * @author pzh
 * @date  2024/10/16 15:18
 **/
@Getter
@Setter
public class HonorAwardPageParam {

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

    /** 荣誉奖项类型（项目成果|科技奖|数字化成果） */
    @ApiModelProperty(value = "荣誉奖项类型（项目成果|科技奖|数字化成果）")
    private String type;

    /** 人员Id */
    @ApiModelProperty(value = "人员Id")
    private String personnelId;

    /** 名称 */
    @ApiModelProperty(value = "名称")
    private String prjName;

    /** 专业 */
    @ApiModelProperty(value = "专业")
    private String profession;

    /** 级别 */
    @ApiModelProperty(value = "级别")
    private String level;

}
