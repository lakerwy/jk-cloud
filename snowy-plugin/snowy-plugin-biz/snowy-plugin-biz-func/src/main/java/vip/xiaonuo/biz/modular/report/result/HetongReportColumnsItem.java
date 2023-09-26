package vip.xiaonuo.biz.modular.report.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/7/10 9:54
 */

@Getter
@Setter
public class HetongReportColumnsItem {
    /** 正表头名称 - 正表头1  */
    @ApiModelProperty(value = "正表头名称 - 正表头1 ")
    private String label;

    /** 正表头列- 对应字段name1  */
    @ApiModelProperty(value = "正表头列- 对应字段name1 ")
    private String prop;

    /** 副表头- 数据  */
    @ApiModelProperty(value = "副表头- 数据 ")
    private List<HetongReportChildrenItem> children;
}
