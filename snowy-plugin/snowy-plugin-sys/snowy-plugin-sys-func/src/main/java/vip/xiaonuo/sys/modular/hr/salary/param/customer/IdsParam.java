package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 主键Id集合
 */

@Getter
@Setter
public class IdsParam {
    /** 主键Id集合 */
    @ApiModelProperty(value = "主键Id集合", position = 1)
    private List<String> ids;
}
