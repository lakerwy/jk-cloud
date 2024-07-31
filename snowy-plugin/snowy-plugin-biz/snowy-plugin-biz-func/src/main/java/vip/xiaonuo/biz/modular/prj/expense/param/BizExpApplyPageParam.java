package vip.xiaonuo.biz.modular.prj.expense.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 申请流程查询参数
 *
 * @author yong.ye
 * @date  2024/07/30 15:26
 **/
@Getter
@Setter
public class BizExpApplyPageParam {

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

    /** 报销类别 */
    @ApiModelProperty(value = "报销类别")
    private String expType;

    /** 发起人 */
    @ApiModelProperty(value = "发起人")
    private String initor;

    /** 报销人 */
    @ApiModelProperty(value = "报销人")
    private String exptor;

    /** 财务编码 */
    @ApiModelProperty(value = "财务编码")
    private String finCode;

    /** 单位 */
    @ApiModelProperty(value = "单位")
    private String unit;

    /** 部门 */
    @ApiModelProperty(value = "部门")
    private String dept;

    /** 日期开始 */
    @ApiModelProperty(value = "日期开始")
    private String startExpDate;

    /** 日期结束 */
    @ApiModelProperty(value = "日期结束")
    private String endExpDate;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号")
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

}
