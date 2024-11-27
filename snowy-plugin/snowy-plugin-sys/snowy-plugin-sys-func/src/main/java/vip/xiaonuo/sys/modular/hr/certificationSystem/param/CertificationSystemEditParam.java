package vip.xiaonuo.sys.modular.hr.certificationSystem.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 认证体系编辑参数
 *
 * @author pzh
 * @date  2024/10/18 10:01
 **/
@Getter
@Setter
public class CertificationSystemEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    /** 证书编号 */
    @ApiModelProperty(value = "证书编号", position = 3)
    private String certNo;

    /** 符合标准 */
    @ApiModelProperty(value = "符合标准", position = 4)
    private String standard;

    /** 通过的认证范围 */
    @ApiModelProperty(value = "通过的认证范围", position = 5)
    private String scope;

    /** 有效期至 */
    @ApiModelProperty(value = "有效期至", position = 6)
    private Date invalidDate;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 7)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

}
