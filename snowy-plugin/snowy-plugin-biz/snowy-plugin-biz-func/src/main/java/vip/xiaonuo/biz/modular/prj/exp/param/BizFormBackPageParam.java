package vip.xiaonuo.biz.modular.prj.exp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 费用报销（二期）- 归还登记查询参数
 *
 * @author yong.ye
 * @date  2025/02/25 09:15
 **/
@Getter
@Setter
public class BizFormBackPageParam {

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

    /** 借支人id */
    @ApiModelProperty(value = "借支人id")
    private String expId;

    /** 借支人 */
    @ApiModelProperty(value = "借支人")
    private String expName;

    /** 借支单id(BIZ_FORM_CASH.ID) */
    @ApiModelProperty(value = "借支单id(BIZ_FORM_CASH.ID)")
    private String cashId;

    /** 借支单(BIZ_FORM_CASH.SN) */
    @ApiModelProperty(value = "借支单(BIZ_FORM_CASH.SN)")
    private String cashSn;

    /** 归还方式 */
    @ApiModelProperty(value = "归还方式")
    private String backType;

    /** 归还日期开始 */
    @ApiModelProperty(value = "归还日期开始")
    private String startBackDate;

    /** 归还日期结束 */
    @ApiModelProperty(value = "归还日期结束")
    private String endBackDate;

    /** SN */
    @ApiModelProperty(value = "SN")
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态")
    private String status;

}
