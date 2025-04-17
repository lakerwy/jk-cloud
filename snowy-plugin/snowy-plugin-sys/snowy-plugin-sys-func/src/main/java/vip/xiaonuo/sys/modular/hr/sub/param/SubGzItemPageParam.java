package vip.xiaonuo.sys.modular.hr.sub.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司-工资发放 - 工资|奖金明细查询参数
 *
 * @author yong.ye
 * @date  2025/02/06 09:11
 **/
@Getter
@Setter
public class SubGzItemPageParam {

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

    /** 明细类别：工资|奖金|经济补偿金 */
    @ApiModelProperty(value = "明细类别：工资|奖金|经济补偿金")
    private String type;

    /** 所属月份 */
    @ApiModelProperty(value = "所属月份")
    private String yM;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String userName;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idCardNumber;

    /** 部门 */
    @ApiModelProperty(value = "部门")
    private String orgName;

    /** 职位 */
    @ApiModelProperty(value = "职位")
    private String duty;

    /** 用工性质 */
    @ApiModelProperty(value = "用工性质")
    private String nature;

}
