package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * 复制 数据
 */

@Getter
@Setter
public class CopyParam {
    /** 复制：源数据 - 来源于 所属年月 */
    @ApiModelProperty(value = "复制：源数据 - 来源于 所属年月 - 不提供默认位当前年月的上一个月")
    private String fromYearMonth;

    /** 复制：目标 - 复制到 所属年月 */
    @ApiModelProperty(value = "复制：目标 - 复制到 所属年月 - 不提供默认位当前年月")
    private String toYearMonth;

    /** 某些情况 指定复制 - 源数据 - 主键Id集合 */
    @ApiModelProperty(value = "指定复制 - 源数据 - 主键Id集合 - 不提供，复制全部")
    private List<String> ids;
}
