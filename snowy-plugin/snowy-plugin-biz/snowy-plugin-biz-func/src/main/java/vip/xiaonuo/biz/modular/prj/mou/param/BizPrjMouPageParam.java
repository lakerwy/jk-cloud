package vip.xiaonuo.biz.modular.prj.mou.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目承接备忘录查询参数
 *
 * @author yy
 * @date  2025/05/13 09:14
 **/
@Getter
@Setter
public class BizPrjMouPageParam {

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

    /** 项目Id */
    @ApiModelProperty(value = "项目Id")
    private String prjId;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称")
    private String prjName;

    /** 申请日期开始 */
    @ApiModelProperty(value = "申请日期开始")
    private String startApplyDate;

    /** 申请日期结束 */
    @ApiModelProperty(value = "申请日期结束")
    private String endApplyDate;

    /** 项目概要 */
    @ApiModelProperty(value = "项目概要")
    private String prjMemo;

    /** 建设单位 */
    @ApiModelProperty(value = "建设单位")
    private String devUnit;

    /** 分公司/合作单位 */
    @ApiModelProperty(value = "分公司/合作单位")
    private String partnerUnit;

    /** 经办部门Id */
    @ApiModelProperty(value = "经办部门Id")
    private String deptId;

    /** 经办部门 */
    @ApiModelProperty(value = "经办部门")
    private String deptName;

    /** SN */
    @ApiModelProperty(value = "SN")
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

}
