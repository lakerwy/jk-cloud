package vip.xiaonuo.oa.modular.attendance.apply.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 考勤申请管理表查询参数
 *
 * @author mayike
 * @date  2024/07/15 09:56
 **/
@Getter
@Setter
public class ApplyPageParam {

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

    @ApiModelProperty(value = "查询个人数据标识")
    private Boolean owner = false;

    /** 申请类型：1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到 */
    @ApiModelProperty(value = "申请类型：1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到")
    private int applyType = 0;

}
