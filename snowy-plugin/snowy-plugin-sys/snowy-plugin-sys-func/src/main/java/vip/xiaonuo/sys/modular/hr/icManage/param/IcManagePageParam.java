package vip.xiaonuo.sys.modular.hr.icManage.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工商管理查询参数
 *
 * @author pzh
 * @date  2025/02/25 16:33
 **/
@Getter
@Setter
public class IcManagePageParam {

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

    /** 类型 */
    @ApiModelProperty(value = "类型")
    private String type;

    /** 成立日期开始 */
    @ApiModelProperty(value = "成立日期开始")
    private String startEstablishTime;

    /** 成立日期结束 */
    @ApiModelProperty(value = "成立日期结束")
    private String endEstablishTime;

}
