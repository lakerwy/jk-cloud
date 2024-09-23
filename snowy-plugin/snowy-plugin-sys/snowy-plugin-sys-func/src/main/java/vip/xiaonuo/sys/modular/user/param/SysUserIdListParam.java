package vip.xiaonuo.sys.modular.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class SysUserIdListParam {
    /** id集合 */
    @ApiModelProperty(value = "id集合", required = true)
    @NotNull(message = "idList不能为空")
    private List<String> idList;

    /** 按照idList默认顺序返回结果 true false */
    @ApiModelProperty(value = "按照idList默认顺序返回结果 true false")
    private Boolean isIdList = Boolean.FALSE;

}
