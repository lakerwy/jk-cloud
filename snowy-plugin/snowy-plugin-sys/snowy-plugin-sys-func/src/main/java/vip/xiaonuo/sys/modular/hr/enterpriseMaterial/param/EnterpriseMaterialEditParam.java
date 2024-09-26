package vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 企业宣传资料编辑参数
 *
 * @author pzh
 * @date  2024/09/18 14:57
 **/
@Getter
@Setter
public class EnterpriseMaterialEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 3)
    private String name;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 4)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

    /** 是否重新上传了附件（1-是，0-否） */
    @ApiModelProperty(value = "是否重新上传了附件（1-是，0-否）", position = 6)
    private String isReUpload;

    /** 排序序号 */
    @ApiModelProperty(value = "排序序号", position = 7)
    private Integer indexSort;

}
