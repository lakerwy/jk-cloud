package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 项目业主结算记录查询参数
 *
 * @author yy
 * @date  2025/06/03 09:52
 **/
@Getter
@Setter
public class PorOwnerSettlePageParam {

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

    /** 收入合同- 主键Id */
    @ApiModelProperty(value = "收入合同- 主键Id")
    private String hetongId;

    /** 结算单号 */
    @ApiModelProperty(value = "结算单号")
    private String sn;

    /** 结算类型 */
    @ApiModelProperty(value = "结算类型")
    private String settleType;

    /** 结算日期开始 */
    @ApiModelProperty(value = "结算日期开始")
    private String startSettleDate;

    /** 结算日期结束 */
    @ApiModelProperty(value = "结算日期结束")
    private String endSettleDate;

    /** 结算状态 */
    @ApiModelProperty(value = "结算状态")
    private String settleStatus;

}
