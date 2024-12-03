package vip.xiaonuo.sys.modular.hr.zizhiCertificate.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 资质证照编辑参数
 *
 * @author pzh
 * @date  2024/10/16 16:39
 **/
@Getter
@Setter
public class ZizhiCertificateEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 资质名称 */
    @ApiModelProperty(value = "资质名称", position = 2)
    private String ziZhiName;

    /** 资质类别 */
    @ApiModelProperty(value = "资质类别", position = 3)
    private String type;

    /** 资质证书号 */
    @ApiModelProperty(value = "资质证书号", position = 4)
    private String certNo;

    /** 颁发机构 */
    @ApiModelProperty(value = "颁发机构", position = 5)
    private String issueOrg;

    /** 资质/业务范围 */
    @ApiModelProperty(value = "资质/业务范围", position = 6)
    private String businessScope;

    /** 有效期至 */
    @ApiModelProperty(value = "有效期至", position = 7)
    private Date validity;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 8)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

}
