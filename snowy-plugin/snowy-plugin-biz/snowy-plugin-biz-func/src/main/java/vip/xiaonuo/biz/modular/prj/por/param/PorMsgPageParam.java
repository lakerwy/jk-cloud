package vip.xiaonuo.biz.modular.prj.por.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 收款提醒查询参数
 *
 * @author yy
 * @date  2025/06/18 16:13
 **/
@Getter
@Setter
public class PorMsgPageParam {

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

    /** 业务模块（回款计划|业主结算） */
    @ApiModelProperty(value = "业务模块（回款计划|业主结算）")
    private String modelType;

    /** 收入合同- 主键Id */
    @ApiModelProperty(value = "收入合同- 主键Id")
    private String hetongId;

    /** 业务 - 主键Id */
    @ApiModelProperty(value = "业务 - 主键Id")
    private String objId;

    /** 是否同步发送钉钉通知 */
    @ApiModelProperty(value = "是否同步发送钉钉通知")
    private String isDing;

    /** 主题 */
    @ApiModelProperty(value = "主题")
    private String subjectTitle;

}
