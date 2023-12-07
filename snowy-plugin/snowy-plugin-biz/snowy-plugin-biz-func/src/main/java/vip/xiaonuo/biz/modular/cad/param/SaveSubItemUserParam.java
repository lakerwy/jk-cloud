package vip.xiaonuo.biz.modular.cad.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class SaveSubItemUserParam {

    /** 子项ID */
    @ApiModelProperty(value = "子项ID", position = 1)
    private String subItemId;

    /** 人员配置集合 */
    @ApiModelProperty(value = "人员配置集合", position = 2)
    private List<CadSubItemUserParam> subItemUserList;
}
