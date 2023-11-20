package vip.xiaonuo.biz.modular.prj.allotment.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.allotment.entity.BizPrjAllotment;
import vip.xiaonuo.biz.modular.prj.allotment.entity.BizPrjAllotmentCn;

import java.util.List;

@Getter
@Setter
public class BizPrjAllotmentVo extends BizPrjAllotment {

    /** 业务表-项目款拨付申请单-出纳支付信息 */
    @ApiModelProperty(value = "业务表-项目款拨付申请单-出纳支付信息列表", position = 50)
    private List<BizPrjAllotmentCn> allotmentCnList;
}
