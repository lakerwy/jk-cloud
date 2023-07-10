package vip.xiaonuo.biz.modular.report.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/7/10 9:55
 */

@Getter
@Setter
public class HetongReportChildrenItem {

    /** 副表头名称 - 副表头1  */
    @ApiModelProperty(value = "副表头名称 - 副表头1 ")
    private String label;

    /** 副表头列- 对应字段name1  */
    @ApiModelProperty(value = "副表头列- 对应字段name1 ")
    private String prop;
}
