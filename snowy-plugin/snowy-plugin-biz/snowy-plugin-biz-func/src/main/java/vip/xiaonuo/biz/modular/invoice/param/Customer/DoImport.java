package vip.xiaonuo.biz.modular.invoice.param.Customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.invoice.vo.InvoicePrjRefundVo;

import java.util.List;

/**
 * 正式导入数据实体
 */

@Getter
@Setter
public class DoImport {

    /** 正式导入数据-集合 */
    @ApiModelProperty(value = "正式导入数据-集合", position = 100)
    private List<ImportRefundParam> importData;
}
