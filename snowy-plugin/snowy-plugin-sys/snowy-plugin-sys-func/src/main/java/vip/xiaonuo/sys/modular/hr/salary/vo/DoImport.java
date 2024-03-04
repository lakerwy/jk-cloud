package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 确认导入-正式入库
 */

@Getter
@Setter
public class DoImport {

    /** 导入-批次号 */
    @ApiModelProperty(value = "导入-批次号", position = 1)
    private String batchId;

    /** 导入-类型 */
    @ApiModelProperty(value = "导入-类型", position = 2)
    private String type;
}
