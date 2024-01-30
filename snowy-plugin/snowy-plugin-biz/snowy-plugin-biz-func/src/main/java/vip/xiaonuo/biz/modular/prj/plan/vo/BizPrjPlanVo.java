package vip.xiaonuo.biz.modular.prj.plan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.plan.entity.BizPrjPlan;
import vip.xiaonuo.biz.modular.prj.plan.entity.BizPrjPlanCollection;
import vip.xiaonuo.biz.modular.prj.plan.entity.BizPrjPlanInvoice;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/28 16:12
 */

@Getter
@Setter
public class BizPrjPlanVo extends BizPrjPlan {

    /** 项目计划付款单-本次提供发票列表 */
    @ApiModelProperty(value = "项目计划付款单-本次提供发票列表", position = 50)
    private List<BizPrjPlanInvoice> planInvoiceList;

    /** 项目计划付款单-收款单位列表 */
    @ApiModelProperty(value = "项目计划付款单-收款单位列表", position = 50)
    private List<PlanCollectionVo> planCollectionList;

}
