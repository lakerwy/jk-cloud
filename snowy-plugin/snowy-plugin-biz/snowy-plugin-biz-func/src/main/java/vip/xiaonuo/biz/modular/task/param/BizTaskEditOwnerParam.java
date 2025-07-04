package vip.xiaonuo.biz.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 组织责任人变更参数
 *
 * @author pzh
 * @date  2025/06/18 14:24
 **/
@Getter
@Setter
public class BizTaskEditOwnerParam {

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 1)
    private String orgId;

    /** 责任人名称 */
    @ApiModelProperty(value = "责任人名称", position = 3)
    private String ower;

    /** 责任人Id */
    @ApiModelProperty(value = "责任人Id", position = 5)
    private String owerId;

    /** 年度 */
    @ApiModelProperty(value = "年度", position = 7)
    private String year;

}
