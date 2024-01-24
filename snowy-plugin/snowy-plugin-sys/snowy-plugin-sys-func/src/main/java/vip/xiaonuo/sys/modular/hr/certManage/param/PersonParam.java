package vip.xiaonuo.sys.modular.hr.certManage.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 总部花名册 & 分公司花名册 人员信息搜索
 */

@Getter
@Setter
public class PersonParam {

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 2)
    private String name;
}
