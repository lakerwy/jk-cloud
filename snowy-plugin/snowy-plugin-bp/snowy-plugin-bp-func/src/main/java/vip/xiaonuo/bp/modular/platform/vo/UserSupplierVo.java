package vip.xiaonuo.bp.modular.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.bp.modular.platform.entity.BpUser;

@Getter
@Setter
public class UserSupplierVo extends BpUser {

    /** 供应商 - 主键ID */
    @ApiModelProperty(value = "供应商 - 主键ID", position = 100)
    private String prvId;

    /** 供应商 - 审核状态 */
    @ApiModelProperty(value = "供应商 - 审核状态", position = 100)
    private String prvStatus;
}
