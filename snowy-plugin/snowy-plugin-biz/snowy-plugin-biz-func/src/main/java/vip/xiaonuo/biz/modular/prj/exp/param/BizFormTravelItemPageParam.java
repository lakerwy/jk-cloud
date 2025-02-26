package vip.xiaonuo.biz.modular.prj.exp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 费用报销（二期）- 差旅费用报销单 - 报销明细查询参数
 *
 * @author yong.ye
 * @date  2025/02/24 15:19
 **/
@Getter
@Setter
public class BizFormTravelItemPageParam {

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

    /** 日期开始 */
    @ApiModelProperty(value = "日期开始")
    private String startOptDate;

    /** 日期结束 */
    @ApiModelProperty(value = "日期结束")
    private String endOptDate;

    /** 起点 */
    @ApiModelProperty(value = "起点")
    private String pointFrom;

    /** 终点 */
    @ApiModelProperty(value = "终点")
    private String pointTo;

    /** 交通工具 */
    @ApiModelProperty(value = "交通工具")
    private String goType;

}
