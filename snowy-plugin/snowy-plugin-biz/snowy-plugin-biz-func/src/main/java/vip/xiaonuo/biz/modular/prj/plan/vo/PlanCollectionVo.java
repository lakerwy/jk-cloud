package vip.xiaonuo.biz.modular.prj.plan.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.plan.entity.BizPrjPlanCollection;

import java.util.Date;

/**
 * 计划单 - 收款单位信息
 */

@Getter
@Setter
public class PlanCollectionVo extends BizPrjPlanCollection {

    /** 合同盖章日期-分包合同 */
    @ApiModelProperty(value = "合同盖章日期-分包合同", position = 30)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date sealDate;

    /** 归档日期-分包合同 */
    @ApiModelProperty(value = "归档日期-分包合同", position = 30)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date filingDate;

}
