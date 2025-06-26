package vip.xiaonuo.biz.modular.prj.fin.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FinDataPageParam {

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

    /** 合同编号*/
    @ApiModelProperty(value = "合同编号")
    private String contractNo;

    /** 合同-主键*/
    @ApiModelProperty(value = "合同-主键")
    private String contractId;

    /** 责任单位 */
    @ApiModelProperty(value = "责任单位")
    private String companyOrDepartment;

    /** 项目类型 */
    @ApiModelProperty(value = "项目类型")
    private String bizProjectType;

    /** 经营部门 */
    @ApiModelProperty(value = "经营部门")
    private String manageDept;

}
