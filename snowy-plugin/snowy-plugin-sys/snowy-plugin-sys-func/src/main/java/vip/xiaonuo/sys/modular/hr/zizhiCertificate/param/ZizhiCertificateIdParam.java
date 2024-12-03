package vip.xiaonuo.sys.modular.hr.zizhiCertificate.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 资质证照Id参数
 *
 * @author pzh
 * @date  2024/10/16 16:39
 **/
@Getter
@Setter
public class ZizhiCertificateIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
