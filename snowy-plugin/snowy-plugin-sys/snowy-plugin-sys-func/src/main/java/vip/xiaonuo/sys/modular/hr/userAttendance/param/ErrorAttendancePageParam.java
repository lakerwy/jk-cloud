package vip.xiaonuo.sys.modular.hr.userAttendance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ErrorAttendancePageParam {
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

    /** 人员信息ID */
    @ApiModelProperty(value = "人员信息ID")
    private List<String> userIdList;

    /** 人员信息ID */
    @ApiModelProperty(value = "部门信息ID")
    private List<String> orgIdList;

    /** 日期 */
    @ApiModelProperty(value = "日期：年月日")
    private String dayTime;

    @ApiModelProperty(value = "异常状态：-1 缺卡，3 迟到， 4 早退，5 严重迟到，6 严重早退，空值 返回所有数据")
    private Integer abnormalFlag;

    @ApiModelProperty(value = "是否处理状态：1 已处理，2 未处理")
    private Integer dealFlag;
}
