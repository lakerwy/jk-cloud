package vip.xiaonuo.biz.modular.cad.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CadFileTreeVo extends CadFileVo{

    /** 子集 */
    @ApiModelProperty(value = "子集", position = 30)
    private List<CadFileVo> children;

}
