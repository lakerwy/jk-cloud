package vip.xiaonuo.sys.modular.hr.reg.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 注册补贴（新版）- 结果反馈查询参数
 *
 * @author yong.ye
 * @date  2025/01/15 09:08
 **/
@Getter
@Setter
public class RegSubsidyResultPageParam {

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

    /** 注册等级 */
    @ApiModelProperty(value = "注册等级")
    private String level;

    /** 注册类别 */
    @ApiModelProperty(value = "注册类别")
    private String type;

    /** 注册专业 */
    @ApiModelProperty(value = "注册专业")
    private String major;

    /** 费用类型 */
    @ApiModelProperty(value = "费用类型")
    private String feeType;

    /** 费用归属部门 */
    @ApiModelProperty(value = "费用归属部门")
    private String belongType;

    /** 是否通过审核 */
    @ApiModelProperty(value = "是否通过审核")
    private String isPass;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idCardNumber;

}
