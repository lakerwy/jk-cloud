package vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 企业宣传资料添加参数
 *
 * @author pzh
 * @date  2024/09/18 14:57
 **/
@Getter
@Setter
public class EnterpriseMaterialAddParam {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 3)
    private String name;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 4)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

}
