package vip.xiaonuo.sys.modular.hr.certificationSystem.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 认证体系Id参数
 *
 * @author pzh
 * @date  2024/10/18 10:01
 **/
@Getter
@Setter
public class CertificationSystemIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
