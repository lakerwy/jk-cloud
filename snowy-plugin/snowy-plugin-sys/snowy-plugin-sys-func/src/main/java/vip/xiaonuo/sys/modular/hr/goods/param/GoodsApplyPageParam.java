package vip.xiaonuo.sys.modular.hr.goods.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物资领用申请查询参数
 *
 * @author yy
 * @date  2025/05/23 14:34
 **/
@Getter
@Setter
public class GoodsApplyPageParam {

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

    /** 领用部门Id */
    @ApiModelProperty(value = "领用部门Id")
    private String deptId;

    /** 领用部门 */
    @ApiModelProperty(value = "领用部门")
    private String deptName;

    /** 领用人Id */
    @ApiModelProperty(value = "领用人Id")
    private String optId;

    /** 领用人 */
    @ApiModelProperty(value = "领用人")
    private String optName;

    /** 申请时间开始 */
    @ApiModelProperty(value = "申请时间开始")
    private String startOptTime;

    /** 申请时间结束 */
    @ApiModelProperty(value = "申请时间结束")
    private String endOptTime;

    /** 领用事由 */
    @ApiModelProperty(value = "领用事由")
    private String descMemo;

    /** 领取确认 */
    @ApiModelProperty(value = "领取确认")
    private String confirmType;

    /** 实际领用日期开始 */
    @ApiModelProperty(value = "实际领用日期开始")
    private String startConfirmTime;

    /** 实际领用日期结束 */
    @ApiModelProperty(value = "实际领用日期结束")
    private String endConfirmTime;

    /** 退回人Id */
    @ApiModelProperty(value = "退回人Id")
    private String backId;

    /** 退回人 */
    @ApiModelProperty(value = "退回人")
    private String backName;

    /** 核收人Id */
    @ApiModelProperty(value = "核收人Id")
    private String verifyId;

    /** 核收人 */
    @ApiModelProperty(value = "核收人")
    private String verifyName;

    /** SN */
    @ApiModelProperty(value = "SN")
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

}
