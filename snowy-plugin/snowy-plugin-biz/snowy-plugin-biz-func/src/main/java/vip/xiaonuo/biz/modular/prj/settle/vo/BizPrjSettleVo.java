package vip.xiaonuo.biz.modular.prj.settle.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.settle.entity.BizPrjSettle;
import vip.xiaonuo.biz.modular.prj.settle.entity.BizPrjSettleInvoice;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/28 16:26
 */

@Getter
@Setter
public class BizPrjSettleVo extends BizPrjSettle {
    /** 项目款结算单-发票列表 */
    @ApiModelProperty(value = "项目款结算单-发票列表", position = 50)
    private List<BizPrjSettleInvoice> settleInvoiceList;

}
