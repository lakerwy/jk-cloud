package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 项目成员管理查询参数
 *
 * @author yy
 * @date  2025/06/03 09:40
 **/
@Getter
@Setter
public class PorMemberPageParam {

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

    /** 项目产值台账 - 项目管理 - 主键Id */
    @ApiModelProperty(value = "项目产值台账 - 项目管理 - 主键Id")
    private String porProjectId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String userName;

    /** 用户id */
    @ApiModelProperty(value = "用户id")
    private String userId;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /** 所属部门 */
    @ApiModelProperty(value = "所属部门")
    private String orgName;

    /** 所属部门Id */
    @ApiModelProperty(value = "所属部门Id")
    private String orgId;

    /** 担任角色 */
    @ApiModelProperty(value = "担任角色")
    private String roleName;

}
