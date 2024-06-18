package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 按照 个人|公司|部门 查询 一定期限内工资数据
 */

@Getter
@Setter
public class SearchPage {

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

    /** 所属年月 - 起 */
    @ApiModelProperty(value = "所属年月 - 起")
    private String fromYearMonth;

    /** 所属年月 - 止 */
    @ApiModelProperty(value = "所属年月 - 止")
    private String toYearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String userName;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门")
    private String firstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门")
    private String orgName;

    /** 员工类别：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类别：总部|分公司|外部")
    private String userType;

    /** 是否缓发 */
    @ApiModelProperty(value = "是否缓发")
    private String isSlow;

    /** 工资发放公司 */
    @ApiModelProperty(value = "工资发放公司")
    private String company;
}
