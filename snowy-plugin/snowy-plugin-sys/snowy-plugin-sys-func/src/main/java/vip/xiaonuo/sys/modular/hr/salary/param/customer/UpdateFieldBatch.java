package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 批量更新 字段
 */

@Getter
@Setter
public class UpdateFieldBatch {

    /** 待更新的字段名集合 */
    @ApiModelProperty(value = "待更新的字段名集合", position = 1)
    private List<String> fieldList;

    /** 待更新数据集合 */
    @ApiModelProperty(value = "待更新数据集合", position = 1)
    private List<UpdateInfo> infoList;

}
