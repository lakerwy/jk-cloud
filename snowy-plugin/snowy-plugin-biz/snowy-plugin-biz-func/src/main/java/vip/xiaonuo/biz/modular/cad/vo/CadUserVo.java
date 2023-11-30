package vip.xiaonuo.biz.modular.cad.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.cad.entity.CadUser;

import java.util.List;

@Getter
@Setter
public class CadUserVo extends CadUser {

    /** 员工所属专业信息 */
    @ApiModelProperty(value = "员工所属专业信息", position = 20)
    private List<CadProfessionVo> professionList;

}
