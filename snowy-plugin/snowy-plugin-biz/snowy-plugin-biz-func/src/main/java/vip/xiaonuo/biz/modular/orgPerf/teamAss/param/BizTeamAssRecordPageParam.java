package vip.xiaonuo.biz.modular.orgPerf.teamAss.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 班子考核记录查询参数
 *
 * @author pzh
 * @date  2025/06/17 17:08
 **/
@Getter
@Setter
public class BizTeamAssRecordPageParam {

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

    /** 考核年度（例如：2025） */
    @ApiModelProperty(value = "考核年度（例如：2025）")
    private Integer assYear;

    /** 考核人ID */
    @ApiModelProperty(value = "考核人ID")
    private String assessorId;

    /** 考核人姓名 */
    @ApiModelProperty(value = "考核人姓名")
    private String assessorName;

}
