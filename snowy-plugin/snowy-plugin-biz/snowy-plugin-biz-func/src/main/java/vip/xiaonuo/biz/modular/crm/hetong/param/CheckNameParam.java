package vip.xiaonuo.biz.modular.crm.hetong.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/11/3 8:57
 */


@Getter
@Setter
public class CheckNameParam {

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 7)
    private String projectName;

}
