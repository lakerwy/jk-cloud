package vip.xiaonuo.biz.modular.prj.expense.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务管理 - 费用报销 - 研发项目信息表查询参数
 *
 * @author yong.ye
 * @date  2024/08/01 11:22
 **/
@Getter
@Setter
public class BizExpDevPageParam {

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

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称")
    private String dept;

    /** 编号 */
    @ApiModelProperty(value = "编号")
    private String code;

    /** 在研课题 */
    @ApiModelProperty(value = "在研课题")
    private String subject;

}
