package vip.xiaonuo.sys.modular.hr.h.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 招待事项登记查询参数
 *
 * @author yy
 * @date  2025/05/06 16:11
 **/
@Getter
@Setter
public class HRegPageParam {

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

    /** 填报部门Id */
    @ApiModelProperty(value = "填报部门Id")
    private String deptId;

    /** 填报部门 */
    @ApiModelProperty(value = "填报部门")
    private String deptName;

    /** 填报人Id */
    @ApiModelProperty(value = "填报人Id")
    private String optId;

    /** 填报人 */
    @ApiModelProperty(value = "填报人")
    private String optName;

    /** 登记日期开始 */
    @ApiModelProperty(value = "登记日期开始")
    private String startOptTime;

    /** 登记日期结束 */
    @ApiModelProperty(value = "登记日期结束")
    private String endOptTime;

    /** 招待日期开始 */
    @ApiModelProperty(value = "招待日期开始")
    private String startHDate;

    /** 招待日期结束 */
    @ApiModelProperty(value = "招待日期结束")
    private String endHDate;

    /** 招待事由 */
    @ApiModelProperty(value = "招待事由")
    private String descMemo;

    /** 陪同人员 */
    @ApiModelProperty(value = "陪同人员")
    private String joinJosn;

    /** SN */
    @ApiModelProperty(value = "SN")
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

    /** 招待方式 */
    @ApiModelProperty(value = "招待方式", position = 25)
    private String type;

}
