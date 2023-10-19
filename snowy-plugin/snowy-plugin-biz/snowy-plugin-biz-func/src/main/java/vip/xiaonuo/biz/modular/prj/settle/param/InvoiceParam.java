package vip.xiaonuo.biz.modular.prj.settle.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/10/19 14:14
 */

@Getter
@Setter
public class InvoiceParam {

    /** 合同编号 */
    @ApiModelProperty(value = "合同编号", position = 1)
    private String contractNo;

}
