package vip.xiaonuo.biz.modular.prj.mou.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MouParam {

    /** 立项-主键ID */
    @ApiModelProperty(value = "立项-主键ID", position = 1)
    private String bizProjectId;

    /** 主合同信息-合同ID主键 */
    @ApiModelProperty(value = "主合同信息-合同ID主键", position = 2)
    private String hetongId;

}
