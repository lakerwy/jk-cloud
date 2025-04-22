package vip.xiaonuo.sys.modular.hr.icManage.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 工商信息新增|变更|注销--流程申请查询参数
 *
 * @author yy
 * @date  2025/04/21 15:58
 **/
@Getter
@Setter
public class IcManageApplyPageParam {

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

    /** 事项类别 */
    @ApiModelProperty(value = "事项类别")
    private String applyType;

    /** 公司类别 */
    @ApiModelProperty(value = "公司类别")
    private String companyType;

    /** 经办部门 */
    @ApiModelProperty(value = "经办部门")
    private String applyDeptName;

    /** 经办人 */
    @ApiModelProperty(value = "经办人")
    private String applyName;

    /** 公司名称 */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码")
    private String corpCode;

    /** 成立日期开始 */
    @ApiModelProperty(value = "成立日期开始")
    private String startEstablishTime;

    /** 成立日期结束 */
    @ApiModelProperty(value = "成立日期结束")
    private String endEstablishTime;

    /** SN */
    @ApiModelProperty(value = "SN")
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

}
