package vip.xiaonuo.biz.modular.prj.expense.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务管理 - 费用报销 - 财务收单编码库查询参数
 *
 * @author yong.ye
 * @date  2024/08/01 11:21
 **/
@Getter
@Setter
public class BizFinCodePageParam {

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

    /** 公司 */
    @ApiModelProperty(value = "公司")
    private String company;

    /** 公司码 */
    @ApiModelProperty(value = "公司码")
    private String codePre;

    /** 年 */
    @ApiModelProperty(value = "年")
    private String codeYear;

    /** 月 */
    @ApiModelProperty(value = "月")
    private String codeMonth;

    /** 最终形成的编码 */
    @ApiModelProperty(value = "最终形成的编码")
    private String code;

}
