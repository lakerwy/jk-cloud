package vip.xiaonuo.biz.modular.crm.prjValue.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 项目产值划分明细Id参数
 *
 * @author pzh
 * @date  2025/06/04 16:48
 **/
@Getter
@Setter
public class PrjValueAllocationItemIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
