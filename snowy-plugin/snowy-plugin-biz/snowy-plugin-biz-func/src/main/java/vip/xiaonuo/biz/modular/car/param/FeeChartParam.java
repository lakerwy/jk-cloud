package vip.xiaonuo.biz.modular.car.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/11/10 10:18
 */

@Getter
@Setter
public class FeeChartParam {
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

    /** 区间开始 */
    @ApiModelProperty(value = "区间开始")
    private Date startDate;

    /** 区间结束 */
    @ApiModelProperty(value = "区间结束")
    private Date endDate;

    /** 费用类型 */
    @ApiModelProperty(value = "费用类型")
    private String feeTypes ;

    /** 指派车辆Id */
    @ApiModelProperty(value = "指派车辆Id")
    private String vehicleId ;
}
