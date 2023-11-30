package vip.xiaonuo.biz.modular.cad.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CadUserBatchAddParam {

    /** 批量新增用户集合 */
    @ApiModelProperty(value = "批量新增用户集合", position = 1)
    private List<CadUserAddParam> cadUserAddParamList;
}
