package vip.xiaonuo.sys.modular.hr.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工月度考勤(钉钉)查询参数
 *
 * @author pzh
 * @date  2025/02/12 15:17
 **/
@Getter
@Setter
public class HrEmpAttendDingdingPageParam {

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

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月")
    private String yearMonth;

    /** 员工类型：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类型：总部|分公司|外部")
    private String userType;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String userName;

    /** 所属公司 */
    @ApiModelProperty(value = "所属公司")
    private String company;

    /** 建科云用户Id */
    @ApiModelProperty(value = "建科云用户Id")
    private String userId;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门")
    private String firstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门")
    private String orgName;

}
