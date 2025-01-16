package vip.xiaonuo.biz.modular.prjFee.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 获取前期款项数据参数
 *
 * @author pzh
 * @date  2025/01/15 15:45
 **/
@Getter
@Setter
public class PrjFeeHisParam {

    /** 主键ID */
    @ApiModelProperty(value = "合同主键ID", required = true)
    @NotBlank(message = "合同主键ID不能为空")
    private String hetongId;
}
