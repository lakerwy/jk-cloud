package vip.xiaonuo.sys.modular.hr.personnel.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 花名册列表搜索参数
 */

@Getter
@Setter
public class PersonnelParam {

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "状态")
    private String status;
}
