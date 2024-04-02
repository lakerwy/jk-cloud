package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * 批量更新-是否缓发
 */

@Getter
@Setter
public class UpdateBatch {

    /** 主键ID集合 */
    @ApiModelProperty(value = "主键ID集合", position = 1)
    private List<String> ids;

    /**  isSlow 是 | 否 */
    @ApiModelProperty(value = " isSlow 是 | 否", position = 2)
    private String isSlow;
}
