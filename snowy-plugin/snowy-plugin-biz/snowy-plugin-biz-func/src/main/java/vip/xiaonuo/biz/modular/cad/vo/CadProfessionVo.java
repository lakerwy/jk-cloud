package vip.xiaonuo.biz.modular.cad.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadProfessionVo {
    /** 专业Id */
    @ApiModelProperty(value = "专业Id", position = 20)
    private String professionId;

    /** 专业名称 */
    @ApiModelProperty(value = "专业名称", position = 20)
    private String professionName;
}
